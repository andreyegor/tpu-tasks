-- Для всех заданий предполагшаю что указана месячная зарплата
-- Вывести фамилию и заглавную букву имени (в одном столбце "Иванов И."), заработную плату тех сотрудников, у которых зарплата за полугодие +10% выше 30000. Отсортируйте в прямом порядке по фамилии.
SELECT
  format('%s %s.', first_name, substr(last_name, 1, 1)) as shorten_full_name
FROM
  hr.employees
WHERE
  salary * 6 * 1.1 > 30000;
-- Вывести фамилию сотрудника, manager_id и зарплату за 9 дней, округлив ее до двух знаков после запятой. Считаем, что в месяце 23 рабочих дня.
SELECT
  last_name,
  manager_id,
  ROUND(salary * 9 / 23, 2) as nine_days_salary
FROM
  hr.employees;
-- Вывести инициалы работников в алфавитном порядке и их номер телефона.
SELECT
  format(
    '%s. %s.',
    substr(first_name, 1, 1),
    substr(last_name, 1, 1)
  ) as initials,
  phone_number
FROM
  hr.employees
ORDER BY
  initials;
-- Вывести фамилию и имя сотрудника (в одном столбце), его дневную зарплату, округлив ее до двух знаков после запятой и представив в виде ХХХ руб. ХХ коп. (сделать конкатенацию двумя способами). Считаем, что в месяце 23 рабочих дня.
SELECT
  first_name || ' ' || last_name as full_name,
  format(
    '%s руб. %s коп.',
    (salary / 23) :: int,
    ((salary / 23) % 1 * 100) :: int
  ) as daily_salary
FROM
  hr.employees;
-- В первом столбце вывести фамилию и первые три буквы имени сотрудника, во втором столбце вывести фамилии, в которых нужно заменить удвоенную "pp" на др. символы, при условии, что имя сотрудника состоит из 4-х символов и зарплата меньше 9000.
SELECT
  first_name || ' ' || substr(last_name, 1, 3) as some_sorta_name,
  CASE
    WHEN length(first_name) = 4
    AND salary < 9000 THEN replace(last_name, 'pp', '_not_pp_')
    ELSE last_name
  END as oddly_replaced_if
FROM
  hr.employees;
-- Придумать 2 запроса самостоятельно с числовыми функциями.
SELECT
  first_name,
  last_name,
  '10^' || LOG(10, salary) :: int
FROM
  hr.employees
WHERE
  LOG(10, salary) = FLOOR(LOG(10, salary));
SELECT
  first_name,
  random() as rnd
FROM
  hr.employees
ORDER BY
  rnd;