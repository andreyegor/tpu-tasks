-- Вывести полное имя всех сотрудников (фамилия и имя в одном столбце), указав наличие или отсутствие у него детей (используйте конструкцию CASE подзапрос с EXISTS). В столбце с названием «Дети» вывести значение 'Есть дети', 'Нет детей'. Отсортировать сначала по столбцу «Дети», а затем по полному имени сотрудника.
SELECT
  format('%s %s', es.first_name, es.last_name) AS full_name,
  CASE
    WHEN EXISTS (
      SELECT
        true
      FROM
        hr.dependents ds
      WHERE
        es.employee_id = ds.employee_id
        AND ds.relationship = 'Child'
    ) THEN 'Есть дети'
    ELSE 'Нет детей'
  END as Childs
FROM
  hr.employees es
ORDER BY
  Childs,
  full_name;
-- Вернуть имена, наименование отдела для сотрудников, получающих ту же зарплату, что и «Alexander».
SELECT
  es.first_name,
  ds.department_name
FROM
  hr.employees es
  LEFT JOIN hr.departments ds ON ds.department_id = es.department_id
WHERE
  salary IN (
    SELECT
      salary
    FROM
      hr.employees
    WHERE
      first_name = 'Alexander'
  );
-- Возвратите отделы и среднюю зарплату для каждого отдела, где средняя зарплата для отдела меньше, чем средняя зарплата для всех сотрудников.
WITH salary AS (
  SELECT
    AVG(salary) AS average
  FROM
    hr.employees
)
SELECT
  ds.department_name,
  AVG(salary)
END
FROM
  hr.employees es
  JOIN hr.departments ds ON es.department_id = ds.department_id
  JOIN salary ON true
GROUP BY
  ds.department_name,
  salary.average
HAVING
  AVG(es.salary) < salary.average;
-- Вывести полное имя и должности всех сотрудников, у которых минимальная зарплата по должности больше 9 000.
WITH salary AS (
  SELECT
    job_id,
    MIN(salary) as mn
  FROM
    hr.employees
  GROUP BY
    job_id
)
SELECT
  format('%s %s', es.first_name, es.last_name) AS full_name,
  js.job_title
FROM
  hr.employees es
  JOIN hr.jobs js ON js.job_id = es.job_id
  JOIN salary sl ON sl.job_id = es.job_id
WHERE
  sl.mn > 9000;
-- Вывести сотрудников, у которых ЗП (заработная плата) больше максимальной ЗП любого отдела, в котором работает 8 человек.
WITH department_stats AS (
  SELECT
    department_id,
    COUNT(*) AS department_size,
    MAX(salary) AS max_salary
  FROM
    hr.employees
  GROUP BY
    department_id
),
max_salary_for_8 AS (
  SELECT
    MAX(max_salary) AS max_salary
  FROM
    department_stats
  WHERE
    department_size = 8
)
SELECT
  FORMAT('%s %s', es.first_name, es.last_name) AS full_name
FROM
  hr.employees es
  join max_salary_for_8 ON true
WHERE
  es.salary > max_salary_for_8.max_salary;
-- Вывести полное имя и id сотрудников, у которых кол-во детей больше или равно количеству детей у сотрудника с id =203 (или 109).
WITH employee_kids AS (
  SELECT
    es.employee_id,
    COUNT(ds.employee_id) AS kids_count
  FROM
    hr.employees es
    LEFT JOIN hr.dependents ds ON es.employee_id = ds.employee_id
    AND ds.relationship = 'Child' -- У сотрудника может быть и 0 детей (у 109 и есть). Это тоже нужно учитывать
  GROUP BY
    es.employee_id
),
target_kids_cnt AS (
  SELECT
    MIN(kids_count) AS min_kids -- Берём минимальное количество детей, оно учтёт и все большие значения
  FROM
    employee_kids
  WHERE
    employee_id IN (203, 109)
)
SELECT
  es.employee_id,
  FORMAT('%s %s', es.first_name, es.last_name) AS full_name
FROM
  hr.employees es
  JOIN employee_kids ek ON es.employee_id = ek.employee_id
  JOIN target_kids_cnt tkc ON ek.kids_count >= tkc.min_kids;
-- Вывести полные имена сотрудников (фамилия и имя в одном столбце) с максимальной зарплатой в каждом отделе. Используйте многостолбцовый подзапрос, подумайте каким еще способом можно получить эти данные и какой способ лучше.
SELECT
  FORMAT('%s %s', es.first_name, es.last_name) AS full_name,
  es.department_id,
  es.salary
FROM
  hr.employees es
WHERE
  (es.department_id, es.salary) IN (
    SELECT
      department_id,
      MAX(salary)
    FROM
      hr.employees
    GROUP BY
      department_id
  );
-- А так лучше
SELECT
  FORMAT('%s %s', es.first_name, es.last_name) AS full_name,
  es.department_id,
  es.salary
FROM
  hr.employees es
  JOIN (
    SELECT
      department_id,
      MAX(salary) AS max_salary
    FROM
      hr.employees
    GROUP BY
      department_id
  ) department_salary ON es.department_id = department_salary.department_id
  AND es.salary = department_salary.max_salary;
-- Приведенный ниже запрос выводит полное имя сотрудников (фамилия и имя в одном столбце), у которых стаж работы больше среднего стажа в их отделе. Реализовать этот запрос без использования коррелированного подзапроса.
SELECT
  e.first_name || ' ' || e.last_name,
  current_date - e.hire_date
FROM
  hr.employees e
WHERE
  current_date - e.hire_date > (
    SELECT
      avg(CURRENT_DATE - hire_date)
    FROM
      hr.employees
    WHERE
      department_id = e.department_id
  );
SELECT
  FORMAT('%s %s', es.first_name, es.last_name) AS full_name,
  FORMAT(
    '%s years',
    ROUND(
      (current_date - es.hire_date) / 365.0,
      1
    )
  ) AS experience
FROM
  hr.employees es
  JOIN (
    SELECT
      department_id,
      AVG(CURRENT_DATE - hire_date) AS average
    FROM
      hr.employees
    GROUP BY
      department_id
  ) department_experience ON es.department_id = department_experience.department_id
  AND CURRENT_DATE - es.hire_date > department_experience.average;
-- оптимизирует ли?
-- Вывести сотрудников (id, имя и фамилию), чья зарплата выше средней по их отделу. Реализовать 2 способами, один из которых с помощью коррелированного подзапроса, второй без.
SELECT
  es.employee_id,
  FORMAT('%s %s', es.first_name, es.last_name) AS full_name,
  es.salary
FROM
  hr.employees es
WHERE
  es.salary > (
    SELECT
      AVG(salary)
    FROM
      hr.employees
    WHERE
      department_id = es.department_id
  );
WITH dept_avg AS (
  SELECT
    department_id,
    AVG(salary) AS avg_salary
  FROM
    hr.employees
  GROUP BY
    department_id
)
SELECT
  es.employee_id,
  FORMAT('%s %s', es.first_name, es.last_name) AS full_name,
  es.salary
FROM
  hr.employees es
  JOIN dept_avg da ON es.department_id = da.department_id
WHERE
  es.salary > da.avg_salary;
-- Придумайте запросы с подзапросами, включающие таблицы region, location.
WITH european_departments as (
  SELECT
    ds.department_id,
    ds.department_name
  FROM
    hr.departments ds
    JOIN hr.locations ls ON ls.location_id = ds.location_id
    JOIN hr.countries cs ON cs.country_id = ls.country_id
    JOIN hr.regions rs ON rs.region_id = cs.region_id
    AND rs.region_name = 'Europe'
)
SELECT
  FORMAT('%s %s', es.first_name, es.last_name) AS full_name,
  eds.department_name
FROM
  hr.employees es
  JOIN european_departments eds ON eds.department_id = es.department_id;
-- Вывести фамилию людей у которых зарплата выше средней
SELECT
  last_name
FROM
  hr.employees
WHERE
  salary > (
    SELECT
      AVG(salary)
    from
      hr.employees
  );