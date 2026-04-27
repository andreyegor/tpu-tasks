BEGIN;
TRUNCATE TABLE concert_places,
concerts,
venues RESTART IDENTITY CASCADE;
CREATE TEMP TABLE test_results (
  test_name text NOT NULL,
  ok boolean NOT NULL,
  sqlstate text,
  message text
);
CREATE PROCEDURE pg_temp.test_expect_success(title text, query text) LANGUAGE plpgsql AS $$
DECLARE state text;
message text;
BEGIN EXECUTE query;
INSERT INTO test_results
VALUES (title, true, 0, 'ok');
EXCEPTION
WHEN OTHERS THEN GET STACKED DIAGNOSTICS state = RETURNED_SQLSTATE,
message = MESSAGE_TEXT;
INSERT INTO test_results
VALUES (title, false, state, message);
END;
$$;
CREATE PROCEDURE pg_temp.test_expect_error(title text, query text, target_state text) LANGUAGE plpgsql AS $$
DECLARE state text;
message text;
BEGIN BEGIN EXECUTE query;
INSERT INTO test_results(test_name, ok, sqlstate, message)
VALUES (
    title,
    false,
    null,
    'ok, but error expected'
  );
EXCEPTION
WHEN OTHERS THEN GET STACKED DIAGNOSTICS state = RETURNED_SQLSTATE,
message = MESSAGE_TEXT;
INSERT INTO test_results(test_name, OK, sqlstate, message)
VALUES (
    title,
    state = target_state,
    state,
    message
  );
END;
END;
$$;
CREATE PROCEDURE pg_temp.test_expect_int(title text, query text, target int) LANGUAGE plpgsql AS $$
DECLARE actual int;
state text;
message text;
BEGIN EXECUTE query INTO actual;
INSERT INTO test_results
VALUES (
    title,
    actual = target,
    0,
    format('actual=%s, expected=%s', actual, target)
  );
EXCEPTION
WHEN OTHERS THEN GET STACKED DIAGNOSTICS state = RETURNED_SQLSTATE,
message = MESSAGE_TEXT;
INSERT INTO test_results
VALUES (title, false, state, message);
END;
$$;
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт NOT NULL для названия площадки',
  $$INSERT INTO venues(venue_name, venue_location)
  VALUES (NULL, 'Какой-то адрес') $$,
    '23502'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт NOT NULL для адреса площадки',
  $$INSERT INTO venues(venue_name, venue_location)
  VALUES ('Площадка', NULL) $$,
    '23502'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт NOT NULL для уровня концерта',
  $$INSERT INTO concerts(
    concert_name,
    concert_date,
    concert_duration_minutes,
    concert_level,
    venue_id
  )
  VALUES (
      'Концерт без уровня',
      DATE '2026-06-01',
      60,
      NULL,
      1
    ) $$,
    '23502'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт FOREIGN KEY concerts.venue_id -> venues.venue_id',
  $$INSERT INTO concerts(
    concert_name,
    concert_date,
    concert_duration_minutes,
    concert_level,
    venue_id
  )
  VALUES (
      'Несуществующая площадка',
      DATE '2026-06-02',
      60,
      'Фантастический',
      9999
    ) $$,
    '23503'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт FOREIGN KEY concert_places.concert_id -> concerts.concert_id',
  $$INSERT INTO concert_places(
    concert_id,
    place_row,
    place_num,
    place_price,
    places_sold
  )
  VALUES (9999, 1, 1, 100.00, false) $$,
    '23503'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт - ненулевая продолжительность концерта',
  $$INSERT INTO concerts(
    concert_name,
    concert_date,
    concert_duration_minutes,
    concert_level,
    venue_id
  )
  VALUES (
      'Нулевая длина',
      DATE '2026-06-02',
      0,
      'Городской',
      1
    ) $$,
    '23514'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт - неотрицательная цена места',
  $$INSERT INTO concert_places(
    concert_id,
    place_row,
    place_num,
    place_price,
    places_sold
  )
  VALUES (1, 2, 1, -1.00, false) $$,
    '23514'
);
CALL pg_temp.test_expect_error(
  'Нарушен констрейнт - запрещено удаление площадок с существующими концертами',
  $$
  WITH v AS (
    INSERT INTO venues(venue_name, venue_location)
    VALUES ('Площадка', 'Адрес')
    RETURNING venue_id
  )
  INSERT INTO concerts(
    concert_name,
    concert_date,
    concert_duration_minutes,
    concert_level,
    venue_id
  )
  SELECT
    'Концерт',
    DATE '2026-06-01',
    60,
    'Превосходный',
    venue_id
  FROM v;

  DELETE FROM venues
  WHERE venue_id = (SELECT venue_id FROM v)
  $$,
  '23503'
);
CALL pg_temp.test_expect_int(
  'Каскадное удаление мест при удалении концерта',
  $$
  WITH v AS (
    INSERT INTO venues(venue_name, venue_location)
    VALUES ('Test venue', 'Address')
    RETURNING venue_id
  ),
  c AS (
    INSERT INTO concerts(
      concert_name,
      concert_date,
      concert_duration_minutes,
      concert_level,
      venue_id
    )
    SELECT
      'Cascade concert',
      DATE '2026-06-03',
      90,
      'Level',
      venue_id
    FROM v
    RETURNING concert_id
  ),
  p AS (
    INSERT INTO concert_places(
      concert_id,
      place_row,
      place_num,
      place_price,
      places_sold
    )
    SELECT concert_id, 1, 1, 100.00, false FROM c
  ),
  d AS (
    DELETE FROM concerts
    WHERE concert_id = (SELECT concert_id FROM c)
  )
  SELECT COUNT(*)
  FROM concert_places
  WHERE concert_id = (SELECT concert_id FROM c)
  $$,
  0
);
SELECT *
FROM test_results
ORDER BY ok DESC,
  test_name;
ROLLBACK;