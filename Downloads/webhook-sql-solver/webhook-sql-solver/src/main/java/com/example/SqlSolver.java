package com.example;

import org.springframework.stereotype.Component;

@Component
public class SqlSolver {

  // PostgreSQL final query for Question 1 (odd regNo)
  private static final String POSTGRES_Q1 = """WITH eligible AS (
  SELECT
    p.amount            AS salary,
    e.first_name,
    e.last_name,
    e.dob,
    d.department_name,
    p.payment_time,
    e.emp_id
  FROM payments p
  JOIN employee  e ON e.emp_id = p.emp_id
  JOIN department d ON d.department_id = e.department
  WHERE EXTRACT(DAY FROM p.payment_time) <> 1
),
max_sal AS (
  SELECT MAX(salary) AS max_salary FROM eligible
)
SELECT
  e.salary                             AS \"SALARY\",
  (e.first_name || ' ' || e.last_name) AS \"NAME\",
  EXTRACT(YEAR FROM AGE(e.payment_time::date, e.dob))::int AS \"AGE\",
  e.department_name                    AS \"DEPARTMENT_NAME\"
FROM eligible e
JOIN max_sal m ON e.salary = m.max_salary
ORDER BY e.emp_id
LIMIT 1;
""";

  public String solve(int lastTwoDigits) {
    boolean isOdd = (lastTwoDigits % 2) == 1;
    if (isOdd) {
      return POSTGRES_Q1;
    } else {
      throw new IllegalStateException("Even regNo → Question 2 not implemented.");
    }
  }
}
