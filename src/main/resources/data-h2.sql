INSERT INTO companies
(id, name)
VALUES
(1, 'EBF');

INSERT INTO employees
(id, company_id, name, surname, email, address, salary)
VALUES
(1, 1, 'TEST1', 'TEST1_SURNAME', 'test1@employeeapi.com', 'ADDRESS1', 4567),
(2, 1, 'TEST2', 'TEST2_SURNAME', 'test2@employeeapi.com', 'ADDRESS2', 4598);