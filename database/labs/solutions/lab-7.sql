-- Получение списка всех родителей (непосредственных и вышестоящих) для заданного сотрудника (например, с id=112), у которого “родителей” не меньше 2.
RECURSIVE managers AS (
  SELECT
    format('%s %s', es.first_name, es.last_name) AS full_name,
    employee_id,
    manager_id,
    1 AS level
  FROM
    hr.employees
  WHERE
    employee_id = 112
  UNION ALL
  SELECT
    e.employee_id,
    e.manager_id,
    m.level + 1
  FROM
    hr.employees e
    JOIN managers m ON e.employee_id = m.manager_id
)
-- У топменеджеров (сотрудников 2 уровня) подсчитайте количество непосредственных подчиненных, и их Фамилию И. через запятую укажите во отдельном столбце. 
-- Придумайте свой запрос с указанием пути иерархии.