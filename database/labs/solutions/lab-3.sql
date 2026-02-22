-- Показать завтрашнюю дату в формате: «Завтрашняя дата - 15 февраля 2025 г.» Месяц можно на английском.
SELECT
  TO_CHAR(
    CURRENT_DATE + 1,
    'Завтрашняя дата - DD TMMonth YYYY г.'
  );
-- Вывести фамилию и имя сотрудников в одном столбце, которые трудоустроены в мае 1990-1995 годов.
SELECT
  first_name || ' ' || last_name as full_name
FROM
  hr.employees
WHERE
  EXTRACT(
    'month'
    FROM
      hire_date
  ) = 5
  AND (
    EXTRACT(
      'year'
      FROM
        hire_date
    ) BETWEEN 1990
    AND 1995
  );
-- Узнать, сколько дней осталось до конца года, если считать от даты трудоустройства сотрудника и до 1 января следующего года.
SELECT
  EXTRACT(
    'days'
    FROM
      (
        DATE_TRUNC('year', CURRENT_DATE) + INTERVAL '1 year' - hire_date
      )
  )
FROM
  hr.employees;
-- Вывести фамилию и имя сотрудника, дату найма в формате дня недели при условии, что сотрудник был нанят на работу в понедельник (или вторник, выберите любой день недели). Используйте функцию EXTRACT.
SELECT
  first_name,
  last_name,
  TO_CHAR(hire_date, 'FMDay, DD.MM.YYYY') as hire_date
FROM
  hr.employees
WHERE
  EXTRACT(
    'dow'
    FROM
      hire_date
  ) = 1;
-- Найти сотрудников (Last_name, First_name в одном столбце через пробел), которые трудоустроились в январе и проработали в компании менее 35 лет. Вывести дату найма в формате «11-февраля-2004». Также найти для них количество лет стажа в формате «3 года 7 месяцев 16 дней» или «3 years 7 mons 16 days»
SELECT
  first_name || ' ' || last_name as full_name,
  TO_CHAR(hire_date, 'DD-TMMonth-YYYY') as hire_date,
  AGE(CURRENT_DATE, hire_date) :: TEXT as experience
FROM
  hr.employees
WHERE
  EXTRACT(
    'month'
    FROM
      hire_date
  ) = 1
  AND EXTRACT(
    'years'
    FROM
      AGE(CURRENT_DATE, hire_date)
  ) < 35;
-- Придумать  2 запроса с функциями дат самостоятельно.
SELECT
  first_name || ' ' || last_name as full_name,
  hire_date :: TEXT
FROM
  hr.employees
WHERE
  LOG(
    2,
    EXTRACT(
      'day'
      from
        hire_date
    )
  ) = FLOOR(
    LOG(
      2,
      EXTRACT(
        'day'
        from
          hire_date
      )
    )
  );
SELECT
  first_name || ' ' || last_name as full_name,
  EXTRACT(
    'year'
    FROM
      hire_date
  )
FROM
  hr.employees
WHERE
  EXTRACT(
    'years'
    FROM
      AGE(CURRENT_DATE, hire_date)
  ) < LENGTH(first_name) * 3;