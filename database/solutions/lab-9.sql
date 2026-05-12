INSERT INTO
  venues (venue_id, venue_name, venue_location)
VALUES
  (1, 'Объект 1', 'Адрес 1'),
  (2, 'Объект 2', 'Адрес 2'),
  (3, 'Объект 3', 'Адрес 3');
INSERT INTO
  concerts (
    concert_id,
    concert_name,
    concert_date,
    concert_duration_minutes,
    concert_level,
    venue_id
  )
VALUES
  (
    1,
    'Событие 1',
    DATE '2026-03-15',
    120,
    'Тип 1',
    1
  ),
  (
    2,
    'Событие 2',
    DATE '2026-04-20',
    90,
    'Тип 2',
    2
  ),
  (
    3,
    'Событие 3',
    DATE '2026-05-10',
    75,
    'Тип 3',
    1
  );
INSERT INTO
  concert_places (
    concert_id,
    place_row,
    place_num,
    place_price
  )
VALUES
  (1, 1, 1, 1500.00),
  (1, 1, 2, 1500.00),
  (1, 2, 1, 1200.00),
  (2, 1, 1, 900.00),
  (2, 1, 2, 900.00),
  (3, 1, 1, 1100.00),
  (3, 1, 2, 1100.00),
  (3, 2, 1, 800.00);
INSERT INTO
  concert_report (
    concert_id,
    venue_name,
    concert_name,
    concert_duration_minutes,
    report_year,
    sold_tickets_count,
    sales_amount
  )
VALUES
  (
    1,
    'Объект 1',
    'Событие 1',
    120,
    2026,
    180,
    270000.00
  ),
  (
    2,
    'Объект 2',
    'Событие 2',
    90,
    2026,
    95,
    85500.00
  ),
  (
    3,
    'Объект 3',
    'Событие 3',
    75,
    2026,
    140,
    154000.00
  );
INSERT INTO
  concert_passport (
    concert_id,
    event_date,
    event_form,
    event_topic,
    event_venue,
    attendees_age_0_14,
    attendees_age_15_30,
    attendees_age_30_plus,
    disability_accessibility,
    responsible_person_fio,
    leader_fio,
    notes
  )
VALUES
  (
    1,
    DATE '2026-03-15',
    'Форма 1',
    'Тема 1',
    'Объект 1',
    18,
    72,
    90,
    'Да',
    'ФИО 1',
    'ФИО 2',
    'Заметка 1'
  ),
  (
    2,
    DATE '2026-04-20',
    'Форма 1',
    'Тема 2',
    'Объект 2',
    9,
    56,
    30,
    'Да',
    'ФИО 3',
    'ФИО 4',
    'Заметка 2'
  ),
  (
    3,
    DATE '2026-05-10',
    'Форма 2',
    'Тема 3',
    'Объект A',
    14,
    48,
    78,
    'Частично',
    'ФИО 5',
    'ФИО 6',
    'Заметка 3'
  );
CREATE VIEW concerts_view AS
SELECT
  c.concert_id,
  c.concert_name,
  c.concert_date,
  c.concert_duration_minutes,
  c.concert_level,
  v.venue_id,
  v.venue_name,
  v.venue_location,
  COUNT(*) palces_cnt,
  SUM(cp.place_price) as price_sum
FROM
  concerts c
  JOIN venues v ON v.venue_id = c.venue_id
  JOIN concert_places cp ON cp.concert_id = c.concert_id
GROUP BY
  c.concert_id,
  c.concert_name,
  c.concert_date,
  c.concert_duration_minutes,
  c.concert_level,
  v.venue_id,
  v.venue_name,
  v.venue_location;
CREATE VIEW concert_passport_summary AS
SELECT
  c.concert_name,
  p.event_form,
  p.attendees_age_0_14,
  p.attendees_age_15_30,
  p.attendees_age_30_plus,
  p.attendees_age_0_14 + p.attendees_age_15_30 + p.attendees_age_30_plus AS attendees_total
FROM
  concert_passport p
  JOIN concerts c ON c.concert_id = p.concert_id;
SELECT
  *
FROM
  concerts_view;
SELECT
  *
FROM
  concert_passport_summary;