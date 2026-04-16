-- Для каждого отдела отобразите номер отдела, название отдела, регион и максимальную ЗП в отделе, которая больше 12000.
SELECT
  ds.department_id,
  ds.department_name,
  rs.region_name,
  max(es.salary) as max_salary
FROM
  hr.departments ds
  JOIN hr.employees es ON es.department_id = ds.department_id
  LEFT JOIN hr.locations ls ON ls.location_id = ds.location_id
  LEFT JOIN hr.countries cs ON cs.country_id = ls.country_id
  LEFT JOIN hr.regions rs ON rs.region_id = cs.region_id
GROUP BY
  ds.department_id,
  ds.department_name,
  rs.region_name
HAVING
  max(es.salary) > 12000;
-- Получите список всех отделов и сотрудников, включая отделы без сотрудников (используйте команду USING при соединении таблиц).
SELECT
  ds.department_name,
  CASE
    WHEN es.employee_id IS NOT NULL THEN format('%s %s', es.first_name, es.last_name)
    ELSE '-'
  END as full_name
FROM
  hr.departments ds
  LEFT JOIN hr.employees es USING(department_id);
-- Для каждого отдела отобразите номер отдела, название отдела, регион и минимальную ЗП в отделе и список сотрудников в виде "Фамилия И." разделенных через запятую.
SELECT
  ds.department_id,
  ds.department_name,
  rs.region_name,
  min(es.salary) as min_salary,
  string_agg(
    format(
      '%s %s.',
      es.first_name,
      substr(es.last_name, 1, 1)
    ),
    ', '
  ) as employees
FROM
  hr.departments ds
  LEFT JOIN hr.employees es ON es.department_id = ds.department_id
  LEFT JOIN hr.locations ls ON ls.location_id = ds.location_id
  LEFT JOIN hr.countries cs ON cs.country_id = ls.country_id
  LEFT JOIN hr.regions rs ON rs.region_id = cs.region_id
GROUP BY
  ds.department_id,
  ds.department_name,
  rs.region_name;
-- Выведите регионы, в которых нет ни одного отдела.
SELECT
  rs.region_name
FROM
  hr.regions rs
  LEFT JOIN hr.countries cs ON cs.region_id = rs.region_id
  LEFT JOIN hr.locations ls ON ls.country_id = cs.country_id
  LEFT JOIN hr.departments ds ON ds.location_id = ls.location_id
GROUP BY
  rs.region_name
HAVING
  count(ds.department_name) = 0;
-- Придумайте самостоятельно запрос, с внешним соединением. Запрос должн быть основаны минимум на 3-х таблицах и включать различные условия.
SELECT
  format('%s %s', es.first_name, es.last_name),
  ds.department_name,
  ls.city
FROM
  hr.employees es
  JOIN hr.departments ds ON ds.department_id = es.department_id
  JOIN hr.locations ls ON ls.location_id = ds.location_id
WHERE
  lower(substr(ls.city, 1, 1)) != 's';
-- По каждой должности посчитать количество детей
SELECT
  js.job_title,
  COUNT(ds.relationship) as children_cnt
FROM
  hr.jobs js 
  LEFT JOIN hr.employees es on js.job_id=es.job_id
  LEFT JOIN hr.dependents ds on es.employee_id=ds.employee_id AND ds.relationship='Child'
GROUP BY
  js.job_title