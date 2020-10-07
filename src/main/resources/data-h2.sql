TRUNCATE TABLE companies;
INSERT INTO companies
(id, name)
VALUES
(10, 'EBF');

TRUNCATE TABLE employees;
INSERT INTO employees
(id, company_id, name, surname, email, address, salary)
VALUES
(10, 10, 'TEST1', 'TEST1_SURNAME', 'test1@employeeapi.com', 'ADDRESS1', 4567),
(11, 10, 'TEST2', 'TEST2_SURNAME', 'test2@employeeapi.com', 'ADDRESS2', 4598);
