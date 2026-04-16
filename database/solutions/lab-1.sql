-- Найти все записи о сотрудниках, у которых LAST_NAME имеет в середине букву "r" и заканчивается на "t".
SELECT
  *
FROM
  hr.employees
WHERE
  last_name ~ format(
    '^(.{%s})r(.{%s})t$',
    (LENGTH(last_name) -1) / 2,
    (LENGTH(last_name) -1) / 2 - 1
  );
-- наверное не строго в середине
-- Вывести фамилию, почту и идентификатор должности сотрудников, которые работают на должности 9, 13, 16 или у которых в почтовом адресе присутствует символ "_". Отсортировать по должность в обратном порядке.
SELECT
  last_name,
  email,
  job_id
FROM
  hr.employees
WHERE
  job_id IN (9, 13, 16)
  OR email ~ '.*_.*'
ORDER BY
  job_id DESC;
-- Вывести информацию о сотрудниках: имя, фамилию, manager_id, номер телефона, почту и зарплату, которая больше 30000. Отсортируйте по зарплате в прямом порядке.
SELECT
  first_name,
  last_name,
  manager_id,
  phone_number,
  email,
  salary
FROM
  hr.employees
WHERE
  salary > 30000
ORDER BY
  salary;
-- Вывести фамилию и дату найма сотрудника, которые были трудоустроены с 1987 по 1994 годы. Отсортируйте по дате найма в обратном порядке.
SELECT
  last_name,
  hire_date
FROM
  hr.employees
WHERE
  hire_date BETWEEN '1987-01-01'
  AND '1994-12-31'
ORDER BY
  hire_date DESC;
-- Найти всех сотрудников, у которых есть удвоенная буква «l» в имени или фамилии. Применить разные способы для двух частей условия.
SELECT
  *
FROM
  hr.employees
WHERE
  first_name ~ '.*ll.*'
  OR POSITION('ll' in last_name) != 0;
-- Выбрать уникальные идентификаторы менеджеров, подчиненные которых трудоустроены c 1988 года и их номер телефона начинается на 515. Вывести только идентификаторы менеджеров, отсортированные по возрастанию.
SELECT
  DISTINCT manager_id
FROM
  hr.employees
WHERE
  hire_date >= '1988-01-01'
  AND phone_number ~ '515.*'
ORDER BY
  manager_id;
-- Для операторов OR, DISTINCT, AS, IN, NOT, NULL, ESCAPE придумайте запросы самостоятельно.
SELECT
  DISTINCT FORMAT('%s %s', first_name, last_name) as full_name,
  email
FROM
  hr.employees
WHERE
  (
    department_id IN (1, 10)
    OR manager_id IS NOT NULL
  )
  AND email LIKE '%ы_%' ESCAPE 'ы'
ORDER BY
  full_name;
-- Вывести список сотрудников, у которых id>150
SELECT
  DISTINCT FORMAT('%s %s', first_name, last_name) as full_name
FROM
  hr.employees
where
  employee_id>150;