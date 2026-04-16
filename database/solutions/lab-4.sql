-- Вывести количество сотрудников, которые были трудоустроены после 1987 года, сгруппировав их по менеджерам.
SELECT
  manager_id,
  COUNT(*) AS employees_count
FROM
  hr.employees
WHERE
  hire_date >= '1988-01-01'
GROUP BY
  manager_id;
-- Вывести id отдела, количество сотрудников в отделах и сумму заработной платы в отделе, исключить сотрудников, у которых отдел не указан и количество сотрудников в отделе меньше 5. Отсортировать по сумме заработной платы от большего к меньшему.
SELECT
  department_id,
  COUNT(*) AS employees_count,
  SUM(salary) AS total_salary
FROM
  hr.employees
WHERE
  department_id IS NOT NULL
GROUP BY
  department_id
HAVING
  NOT COUNT(*) < 5
ORDER BY
  total_salary DESC;
-- Вывести все страны и города из таблицы locations, при этом города одной страны перечислить через запятую в одну ячейку.
SELECT
  country_id,
  STRING_AGG(city, ', ') AS cities
FROM
  hr.locations
GROUP BY
  country_id
ORDER BY
  country_id;
-- Верните номера отделов, в которых самая низкая зарплата сотрудников меньше 9000, и выведите для этих отделов Фамилию и номера сотрудников (employee_id) в формате: «Last_name - employee_id» одном поле через запятую.
SELECT
  department_id,
  STRING_AGG(FORMAT('%s - %s', last_name, employee_id), ', ')
FROM
  hr.employees
WHERE
  department_id IS NOT NULL
GROUP BY
  department_id
HAVING
  MIN(salary) < 9000;
-- Вывести количество сотрудников, сгруппированных по дням недели, когда они были наняты.
SELECT
  TO_CHAR(hire_date, 'FMDay') as day_of_the_week,
  COUNT(*)
FROM
  hr.employees
GROUP BY
  day_of_the_week;
-- Вывести для каждого отдела: в одном столбце информацию о сотрудниках через запятую в виде "фамилия + job_id + salary", в других столбцах минимальную, максимальную зарплату и среднюю зарплату.
SELECT
  STRING_AGG(
    FORMAT('%s + %s + %s', last_name, job_id, salary),
    ', '
  ),
  MIN(salary),
  MAX(salary),
  ROUND(AVG(salary), 2)
FROM
  hr.employees
GROUP BY
  department_id;
-- Придумать 2 запроса самостоятельно с агрегацией данных.
SELECT
  job_id,
  MIN(salary),
  MAX(salary),
  AVG(salary)
FROM
  hr.employees
GROUP BY
  job_id;
SELECT
  EXTRACT(
    'year'
    FROM
      AGE(CURRENT_DATE, hire_date)
  ) as experience,
  MIN(salary),
  MAX(salary),
  AVG(salary)
FROM
  hr.employees
GROUP BY
  experience;
ORDER BY
  experience;
-- Сколько человек в каждом отделе
SELECT
  department_id,
  count(*)
FROM
  hr.employees
GROUP BY
  department_id;