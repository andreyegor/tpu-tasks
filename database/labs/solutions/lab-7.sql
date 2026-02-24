-- Получение списка всех родителей (непосредственных и вышестоящих) для заданного сотрудника (например, с id=112), у которого “родителей” не меньше 2.
WITH RECURSIVE managers AS (
  SELECT
    format('%s %s', es.first_name, es.last_name) AS full_name,
    es.employee_id,
    es.manager_id,
    0 AS depth
  FROM
    hr.employees es
  WHERE
    employee_id = 112
  UNION ALL
  SELECT
    format('%s %s', es.first_name, es.last_name) AS full_name,
    es.employee_id,
    es.manager_id,
    ms.depth + 1
  FROM
    hr.employees es
    JOIN managers ms ON ms.manager_id = es.employee_id
)
SELECT
  *
FROM
  managers;
-- У топменеджеров (сотрудников 2 уровня) подсчитайте количество непосредственных подчиненных, и их Фамилию И. через запятую укажите во отдельном столбце.
WITH RECURSIVE managers AS (
  SELECT
    format('%s %s', es.first_name, es.last_name) AS full_name,
    es.employee_id,
    es.manager_id,
    0 AS employee_level
  FROM
    hr.employees es
  WHERE
    es.manager_id IS NULL
  UNION
  SELECT
    format('%s %s', es.first_name, es.last_name) AS full_name,
    es.employee_id,
    es.manager_id,
    ms.employee_level + 1
  FROM
    hr.employees es
    JOIN managers ms ON es.manager_id = ms.employee_id
)
SELECT
  ms.full_name AS manager_name,
  COUNT(es.employee_id) AS subordinates_count,
  STRING_AGG(
    FORMAT('%s %s.', es.last_name, LEFT(es.first_name, 1)),
    ', '
  ) AS subordinates
FROM
  managers ms
  JOIN hr.employees es ON es.manager_id = ms.employee_id
WHERE
  ms.employee_level = 2
GROUP BY
  ms.full_name;
-- Придумайте свой запрос с указанием пути иерархии.
WITH RECURSIVE preprocessed_employees AS (
  SELECT
    employee_id,
    manager_id,
    format('%s %s', first_name, last_name) AS full_name,
    ROW_NUMBER() OVER (PARTITION BY manager_id) AS idx,
    COUNT(*) OVER (PARTITION BY manager_id) AS cnt
  FROM
    hr.employees
),
max_length AS (
  SELECT
    MAX(LENGTH(full_name)) AS val
  FROM
    preprocessed_employees
),
tree AS (
  SELECT
    es.employee_id,
    es.full_name,
    0 AS depth,
    ARRAY [es.idx] :: bigint [] AS output_order,
    ARRAY [] :: boolean [] AS is_last,
    lpad(
      es.full_name,
      length(es.full_name) + ((ml.val - length(es.full_name)) / 2) :: int,
      ' '
    ) as output
  FROM
    preprocessed_employees es
    CROSS JOIN max_length ml
  WHERE
    es.manager_id IS NULL
  UNION ALL
  SELECT
    es.employee_id,
    es.full_name,
    tr.depth + 1,
    tr.output_order || es.idx AS output_order,
    tr.is_last || (es.idx = es.cnt) AS is_last,
    (
      COALESCE(
        (
          SELECT
            string_agg(
              CASE
                WHEN flag THEN repeat(' ', ml.val)
                ELSE format(
                  '%s│%s',
                  repeat(' ', (ml.val / 2) :: int),
                  repeat(' ', ceil(ml.val / 2) :: int)
                )
              END,
              ''
            )
          FROM
            unnest(tr.is_last) flag --эта штука первращает массив в таблицу чтобы по нему итерироваться :)
        ),
        ''
      )
    ) || (
      CASE
        WHEN es.idx = es.cnt THEN format(
          '%s└%s',
          repeat(' ', (ml.val / 2) :: int),
          repeat('─', ceil(ml.val / 2) :: int)
        )
        ELSE format(
          '%s├%s',
          repeat(' ', (ml.val / 2) :: int),
          repeat('─', ceil(ml.val / 2) :: int)
        )
      END
    ) || lpad(
      ' ' || es.full_name,
      length(es.full_name) + ((ml.val - length(es.full_name) -1) / 2) :: int,
      '─'
    ) as output
  FROM
    preprocessed_employees es
    JOIN tree tr ON tr.employee_id = es.manager_id
    CROSS JOIN max_length ml
)
SELECT
  string_agg(
    output,
    E '\n' -- Только для escape вроде
    ORDER BY
      output_order
  ) AS output
FROM
  tree;