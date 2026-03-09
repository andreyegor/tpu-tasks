-- Вам необходимо создать отчет по отделам компании, который будет содержать две части:
-- — Наименование столбцов с общей информацией по всей компании;
-- — Детальная информация по каждому существующему отделу.
-- Используйте неявное соединение таблиц, учитывая только те отделы, в которых есть сотрудники 
WITH output as (
  SELECT
    es.department_id,
    ds.department_name,
    COUNT(*) as department_size,
    SUM(es.salary) as department_salary
  FROM
    hr.employees es
    LEFT JOIN hr.departments ds ON ds.department_id = es.department_id
  GROUP BY
    es.department_id,
    ds.department_name
)
SELECT
  'Total' as department_id,
  format('Departments count - %s', COUNT(*)) as department_name,
  format(
    'Total size - %s',
    SUM(department_size)
  ) as department_size,
  format(
    'Total salary - %s',
    SUM(department_salary)
  ) as department_salary
FROM
  output
UNION ALL
SELECT
  department_id :: TEXT,
  department_name,
  department_size :: TEXT,
  department_salary :: TEXT
FROM
  (
    SELECT
      *
    FROM
      output
    ORDER BY
      department_id
  );
-- Напишите запрос для вывода полного имени сотрудника (имя и фамилия), зарплаты и категории зарплаты на основе следующих условий:
-- Если зарплата < 5000 - категория 'Низкая'
-- Если зарплата от 5000 до 10000 - категория 'Средняя'
-- Если зарплата от 10001 до 15000 - категория 'Выше среднего'
-- Если зарплата > 15000 - категория 'Высокая'
-- Если зарплата NULL - категория 'Не указана'.
-- Отсортируйте по зарплате в порядке убывания.
SELECT
  format('%s %s', first_name, last_name) as full_name,
  salary,
  CASE
    WHEN salary < 5000 THEN 'Низкая'
    WHEN salary <= 10000 THEN 'Средняя'
    WHEN salary <= 15000 THEN 'Выше среднего'
    WHEN salary IS NOT NULL THEN 'Высокая'
    ELSE 'Не указана'
  END as salary_category
FROM
  hr.employees
ORDER BY
  salary DESC;
-- Используя неявное соединение, выведите список сотрудников, работающих в отделах, расположенных в городе 'Seattle'. Отобразите полное имя сотрудника, название отдела и город, отсортируйте список по фамилии сотрудника в прямом порядке.
SELECT
  format('%s %s', es.first_name, es.last_name) as full_name,
  ds.department_name,
  ls.city
FROM
  hr.employees es
  JOIN hr.departments ds on ds.department_id = es.department_id
  JOIN hr.locations ls on ls.location_id = ds.location_id
WHERE
  ls.city = 'Seattle'
ORDER BY
  es.last_name;
-- Используя неявное соединение, выведите информацию о сотрудниках, их отделах, странах и регионах. Отобразите полное имя сотрудников, перечислив их через запятую, название отдела, название страны и название региона. Включите только тех сотрудников, чья зарплата превышает 11000.
SELECT
  STRING_AGG(
    format('%s %s', es.first_name, es.last_name),
    ', '
  ) as emplyees,
  ds.department_name as department,
  cs.country_name as country,
  rs.region_name as region
FROM
  hr.employees es
  JOIN hr.departments ds ON ds.department_id = es.department_id
  JOIN hr.locations ls ON ls.location_id = ds.location_id
  JOIN hr.countries cs ON cs.country_id = ls.country_id
  JOIN hr.regions rs ON rs.region_id = cs.region_id
WHERE
  es.salary > 11000
GROUP BY
  ds.department_name,
  cs.country_name,
  rs.region_name;
-- Напишите запрос, чтобы отобразить название отдела и города, количество сотрудников этого отдела и количество детей всех сотрудников этого отдела, отсортировать по количеству сотрудников в обратном порядке. Внимательнее: у некоторых сотрудников нет детей.
SELECT
  ds.department_name as department,
  ls.city,
  COUNT(DISTINCT es.employee_id) as employees_cnt,
  COUNT(dds.dependent_id)
FROM
  hr.employees es
  JOIN hr.departments ds ON ds.department_id = es.department_id
  JOIN hr.locations ls ON ls.location_id = ds.location_id
  LEFT JOIN hr.dependents dds ON dds.employee_id = es.employee_id
  AND dds.relationship = 'Child'
GROUP BY
  ds.department_name,
  ls.city
ORDER BY
  employees_cnt DESC;
-- Для каждого существующего отдела выведите: название отдела, количество сотрудников в отделе, суммарную зарплату всех сотрудников отдела, минимальную зарплату в отделе, максимальную зарплату в отделе, среднюю зарплату в отделе (округленную до 2 знаков). Включите только те отделы, в которых работает более 2 сотрудников, и отсортируйте по убыванию средней зарплаты.
SELECT
  ds.department_name as department,
  COUNT(*) as employees_cnt,
  SUM(es.salary) as total_salary,
  MIN(es.salary) as min_salary,
  MAX(es.salary) as max_salary,
  ROUND(AVG(es.salary), 2) as avg_salary
FROM
  hr.departments ds
  JOIN hr.employees es ON es.department_id = ds.department_id
GROUP BY
  ds.department_name
ORDER BY
  avg_salary;
-- Придумайте самостоятельно запрос с соединением в разделе WHERE. Запрос должен быть основан минимум на 3-х таблицах и включать различные условия.
SELECT
  format('%s %s', es.first_name, es.last_name) AS full_name,
  es.salary,
  ds.department_name as department,
  ls.city
FROM
  hr.employees es,
  hr.departments ds,
  hr.locations ls
WHERE
  es.department_id = ds.department_id
  AND ds.location_id = ls.location_id
  AND es.salary > 10000
  AND ls.city = 'Seattle';