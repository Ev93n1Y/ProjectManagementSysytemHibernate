INSERT INTO developers (name, age, gender, salary)
VALUES
('James', 25, 'male', 700),
('Mary', 22, 'female', 750),
('Robert', 30, 'male', 800),
('Patricia', 25, 'female', 850),
('John', 24, 'male', 900),
('Jennifer', 27, 'female', 9500),
('Michael', 24, 'male', 1000),
('David', 40, 'male', 1050),
('Elizabeth', 26, 'female', 1100),
('William', 31, 'male', 1150);

INSERT INTO skills (department, level)
VALUES
('Java', 'Junior'),
('Java', 'Middle'),
('Java', 'Senior'),
('JS', 'Junior'),
('JS', 'Middle'),
('JS', 'Senior'),
('C++', 'Junior'),
('C++', 'Middle'),
('C++', 'Senior'),
('C#', 'Junior'),
('C#', 'Middle'),
('C#', 'Senior');


INSERT INTO companies (name, location)
VALUES
('Goit', 'Kyiv'),
('Electric', 'NY'),
('Teravision', 'Bogota'),
('Microsoft ', 'Washington'),
('IBM', 'Atlanta'),
('Oracle', 'California');


INSERT INTO customers (name, email)
VALUES
('Steven', 'steven@gmail.com'),
('George', 'george@gmail.com'),
('Edward', 'edward@gmail.com'),
('Samuel', 'samuel@gmail.com'),
('Tyler', 'tyler@gmail.com'),
('Raymond', 'raymond@gmail.com'),
('Peter', 'peter@gmail.com'),
('Henry', 'henry@gmail.com');

INSERT INTO projects (name, company_id, customer_id, cost, creation_date)
VALUES
('Project 1', 1, 3, 3000, '2010-01-01'),
('Project 2', 3, 2, 4000, '2011-02-02'),
('Project 3', 2, 4, 5000, '2012-03-03'),
('Project 4', 4, 6, 6000, '2013-04-04'),
('Project 5', 6, 7, 7000, '2014-05-05'),
('Project 6', 3, 5, 8000, '2015-06-06'),
('Project 7', 2, 8, 9000, '2016-07-07'),
('Project 8', 5, 1, 10000, '2017-08-08');

INSERT INTO companies_developers (company_id, developer_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(1, 7),
(2, 8),
(3, 9),
(4, 10);

INSERT INTO developers_projects (developer_id, project_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 1),
(10, 2);

INSERT INTO developers_skills (developer_id, skill_id)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 12);
