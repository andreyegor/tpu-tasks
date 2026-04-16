BEGIN;
TRUNCATE TABLE audience_places,
concerts,
venues,
venue_types,
locations RESTART IDENTITY CASCADE;
CREATE TEMP TABLE test_results (
  test_name text NOT NULL,
  ok boolean NOT NULL,
  sqlstate text,
  message text
);
CREATE PROCEDURE pg_temp.test_expect_success(title text, query text) LANGUAGE plpgsql AS $$ 
DECLARE 
state text;
message text;
BEGIN EXECUTE query;
INSERT INTO
  test_results
VALUES
  (title, true, 0, 'ok');
EXCEPTION
WHEN OTHERS THEN GET STACKED DIAGNOSTICS state = RETURNED_SQLSTATE,
message = MESSAGE_TEXT;
INSERT INTO
  test_results
VALUES
  (title, false, state, message);
END;
$$;
CREATE PROCEDURE pg_temp.test_expect_error(title text, query text, target_state text) LANGUAGE plpgsql AS $$ 
DECLARE 
state text;
message text;
BEGIN BEGIN EXECUTE query;
INSERT INTO
  test_results(test_name, ok, sqlstate, message)
VALUES
  (
    title,
    false,
    null,
    'ok, but error expected'
  );
EXCEPTION
WHEN OTHERS THEN GET STACKED DIAGNOSTICS state = RETURNED_SQLSTATE,
message = MESSAGE_TEXT;
INSERT INTO
  test_results(test_name, OK, sqlstate, message)
VALUES
  (
    title,
    state = target_state,
    state,
    message
  );
END;
END;
$$;
CREATE PROCEDURE pg_temp.test_expect_int(title text, query text, target int) LANGUAGE plpgsql AS $$ 
DECLARE 
actual int;
state text;
message text;
BEGIN EXECUTE query INTO actual;
INSERT INTO
  test_results
VALUES
  (
    title,
    actual = target,
    0,
    format('actual=%s, expected=%s', actual, target)
  );
EXCEPTION
WHEN OTHERS THEN GET STACKED DIAGNOSTICS state = RETURNED_SQLSTATE,
message = MESSAGE_TEXT;
INSERT INTO
  test_results
VALUES
  (title, false, state, message);
END;
$$;
CALL pg_temp.test_expect_success(
  'Корректная локация',
  'INSERT INTO locations(location_address) VALUES (''Улица Пушкина дом Колотушкина'')'
);
CALL pg_temp.test_expect_success(
  'Корректный тип площадки',
  'INSERT INTO venue_types(venue_type_name) VALUES (''Стадион'')'
);
CALL pg_temp.test_expect_success(
  'Корректная площадка',
  'INSERT INTO venues(venue_name, location_id, venue_type_id) VALUES (''Большой Стадион'', 1, 1)'
);
CALL pg_temp.test_expect_success(
  'Корректный концерт',
  'INSERT INTO concerts(concert_name, concert_date, concert_duration_minutes, venue_id) VALUES (''Rock Night'', DATE ''2026-06-01'', 120, 1)'
);
CALL pg_temp.test_expect_success(
  'Корректное место на концерте',
  'INSERT INTO audience_places(concert_id, place_name, place_capacity, place_price, sold_places) VALUES (1, ''VIP'', 100, 2500.00, 25)'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт UNIQUE для имени типа площадки',
  'INSERT INTO venue_types(venue_type_name) VALUES (''Стадион'')',
  '23505'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт NOT NULL для адреса',
  'INSERT INTO locations(location_address) VALUES (NULL)',
  '23502'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт FOREIGN KEY venues.location_id -> locations.location_id',
  'INSERT INTO venues(venue_name, location_id, venue_type_id) VALUES (''Плохая улица'', 9999, 1)',
  '23503'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт FOREIGN KEY venues.venue_type_id -> venue_types.venue_type_id',
  'INSERT INTO venues(venue_name, location_id, venue_type_id) VALUES ('' Плохая улица'', 1, 9999)',
  '23503'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт - ненулевая продолжительность концерта',
  'INSERT INTO concerts(concert_name, concert_date, concert_duration_minutes, venue_id) VALUES (''Плохая длина'', DATE ''2026-06-02'', 0, 1)',
  '23514'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт - неотрицательная цена',
  'INSERT INTO audience_places(concert_id, place_name, place_capacity, place_price, sold_places) VALUES (1, ''Плохая цена'', 10, -1.00, 1)',
  '23514'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт - мест продано больше чем возможно',
  'INSERT INTO audience_places(concert_id, place_name, place_capacity, place_price, sold_places) VALUES (1, ''Слишком много мест'', 10, 100.00, 11)',
  '23514'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт - запрещено удаление площадок с существующими концертами',
  'DELETE FROM venues WHERE venue_id = 1',
  '23001'
);
CALL pg_temp.test_expect_int(
  'Каскадное удаление концерта',
  $$
  WITH c AS (
    INSERT INTO concerts (concert_name, concert_date, concert_duration_minutes, venue_id)
    VALUES ('Cascade Show TEST', DATE '2026-06-03', 90, 1)
    RETURNING concert_id
  )
  INSERT INTO audience_places (concert_id, place_name, place_capacity, place_price, sold_places)
  SELECT concert_id, 'Fan Zone TEST', 200, 1200.00, 50
  FROM c;
  DELETE FROM concerts WHERE concert_id = 2;
  SELECT COUNT(*) FROM audience_places WHERE concert_id = 2
  $$,
  0
);
SELECT
  *
FROM
  test_results
ORDER BY
  ok DESC,
  test_name;
ROLLBACK;