-- Регион
INSERT INTO hr.regions
VALUES (1, 'Europe');
INSERT INTO hr.regions
VALUES (2, 'Americas');
INSERT INTO hr.regions
VALUES (3, 'Asia');
INSERT INTO hr.regions
VALUES (4, 'Middle East and Africa');
--Страна
INSERT INTO hr.countries
VALUES ('AR', 'Argentina', 2);
INSERT INTO hr.countries
VALUES ('AU', 'Australia', 3);
INSERT INTO hr.countries
VALUES ('BE', 'Belgium', 1);
INSERT INTO hr.countries
VALUES ('BR', 'Brazil', 2);
INSERT INTO hr.countries
VALUES ('CA', 'Canada', 2);
INSERT INTO hr.countries
VALUES ('CH', 'Switzerland', 1);
INSERT INTO hr.countries
VALUES ('CN', 'China', 3);
INSERT INTO hr.countries
VALUES ('DE', 'Germany', 1);
INSERT INTO hr.countries
VALUES ('DK', 'Denmark', 1);
INSERT INTO hr.countries
VALUES ('EG', 'Egypt', 4);
INSERT INTO hr.countries
VALUES ('FR', 'France', 1);
INSERT INTO hr.countries
VALUES ('HK', 'HongKong', 3);
INSERT INTO hr.countries
VALUES ('IL', 'Israel', 4);
INSERT INTO hr.countries
VALUES ('IN', 'India', 3);
INSERT INTO hr.countries
VALUES ('IT', 'Italy', 1);
INSERT INTO hr.countries
VALUES ('JP', 'Japan', 3);
INSERT INTO hr.countries
VALUES ('KW', 'Kuwait', 4);
INSERT INTO hr.countries
VALUES ('MX', 'Mexico', 2);
INSERT INTO hr.countries
VALUES ('NG', 'Nigeria', 4);
INSERT INTO hr.countries
VALUES ('NL', 'Netherlands', 1);
INSERT INTO hr.countries
VALUES ('SG', 'Singapore', 3);
INSERT INTO hr.countries
VALUES ('UK', 'United Kingdom', 1);
INSERT INTO hr.countries
VALUES ('US', 'United States of America', 2);
INSERT INTO hr.countries
VALUES ('ZM', 'Zambia', 4);
INSERT INTO hr.countries
VALUES ('ZW', 'Zimbabwe', 4);
-- Местположение
INSERT INTO hr.locations
VALUES (
        1400,
        '2014 Jabberwocky Rd',
        '26192',
        'Southlake',
        'Texas',
        'US'
    );
INSERT INTO hr.locations
VALUES (
        1500,
        '2011 Interiors Blvd',
        '99236',
        'South San Francisco',
        'California',
        'US'
    );
INSERT INTO hr.locations
VALUES (
        1700,
        '2004 Charade Rd',
        '98199',
        'Seattle',
        'Washington',
        'US'
    );
INSERT INTO hr.locations
VALUES (
        1800,
        '147 Spadina Ave',
        'M5V 2L7',
        'Toronto',
        'Ontario',
        'CA'
    );
INSERT INTO hr.locations
VALUES (
        2400,
        '8204 Arthur St',
        NULL,
        'London',
        NULL,
        'UK'
    );
INSERT INTO hr.locations
VALUES (
        2500,
        'Magdalen Centre, The Oxford Science Park',
        'OX9 9ZB',
        'Oxford',
        'Oxford',
        'UK'
    );
INSERT INTO hr.locations
VALUES (
        2700,
        'Schwanthalerstr. 7031',
        '80925',
        'Munich',
        'Bavaria',
        'DE'
    );
-- Отдел
INSERT INTO hr.departments
VALUES (1, 'Administration', 1700);
INSERT INTO hr.departments
VALUES (2, 'Marketing', 1800);
INSERT INTO hr.departments
VALUES (3, 'Purchasing', 1700);
INSERT INTO hr.departments
VALUES (4, 'Human Resources', 2400);
INSERT INTO hr.departments
VALUES (5, 'Shipping', 1500);
INSERT INTO hr.departments
VALUES (6, 'IT', 1400);
INSERT INTO hr.departments
VALUES (7, 'Public Relations', 2700);
INSERT INTO hr.departments
VALUES (8, 'Sales', 2500);
INSERT INTO hr.departments
VALUES (9, 'Executive', 1700);
INSERT INTO hr.departments
VALUES (10, 'Finance', 1700);
INSERT INTO hr.departments
VALUES (11, 'Accounting', 1700);
INSERT INTO hr.departments
VALUES (12, 'PR', 1700);
INSERT INTO hr.departments
VALUES (13, 'Virtual_department', NULL);
INSERT INTO hr.departments
VALUES (14, 'AI', 2400);
-- Должность
INSERT INTO hr.jobs
VALUES (1, 'Public Accountant', 4200.00, 9000.00);
INSERT INTO hr.jobs
VALUES (2, 'Accounting Manager', 8200.00, 16000.00);
INSERT INTO hr.jobs
VALUES (3, 'Administration Assistant', 3000.00, 6000.00);
INSERT INTO hr.jobs
VALUES (4, 'President', 20000.00, 40000.00);
INSERT INTO hr.jobs
VALUES (
        5,
        'Administration Vice President',
        15000.00,
        30000.00
    );
INSERT INTO hr.jobs
VALUES (6, 'Accountant', 4200.00, 9000.00);
INSERT INTO hr.jobs
VALUES (7, 'Finance Manager', 8200.00, 16000.00);
INSERT INTO hr.jobs
VALUES (
        8,
        'Human Resources Representative',
        4000.00,
        9000.00
    );
INSERT INTO hr.jobs
VALUES (9, 'Programmer', 4000.00, 10000.00);
INSERT INTO hr.jobs
VALUES (10, 'Marketing Manager', 9000.00, 15000.00);
INSERT INTO hr.jobs
VALUES (11, 'Marketing Representative', 4000.00, 9000.00);
INSERT INTO hr.jobs
VALUES (
        12,
        'Public Relations Representative',
        4500.00,
        10500.00
    );
INSERT INTO hr.jobs
VALUES (13, 'Purchasing Clerk', 2500.00, 5500.00);
INSERT INTO hr.jobs
VALUES (14, 'Purchasing Manager', 8000.00, 15000.00);
INSERT INTO hr.jobs
VALUES (15, 'Sales Manager', 10000.00, 20000.00);
INSERT INTO hr.jobs
VALUES (16, 'Sales Representative', 6000.00, 12000.00);
INSERT INTO hr.jobs
VALUES (17, 'Shipping Clerk', 2500.00, 5500.00);
INSERT INTO hr.jobs
VALUES (18, 'Stock Clerk', 2000.00, 5000.00);
INSERT INTO hr.jobs
VALUES (19, 'Stock Manager', 5500.00, 8500.00);
INSERT INTO hr.jobs
VALUES (20, 'Programmer', 10000.00, 19000.00);
-- Сотрудники
INSERT INTO hr.employees
VALUES (
        100,
        'Steven',
        'King',
        'sking.kochhar@sqltutorial.org',
        '515.123.4768',
        '1989-09-21',
        4,
        40000.00,
        null,
        1
    );
INSERT INTO hr.employees
VALUES (
        101,
        'Neena',
        'Kochhar',
        'neena.kochhar@sqltutorial.org',
        '515.123.4568',
        '1989-09-21',
        5,
        17000.00,
        100,
        9
    );
INSERT INTO hr.employees
VALUES (
        102,
        'Lex',
        'De Haan',
        'lex.de haan@sqltutorial.org',
        '515.123.4569',
        '1993-01-13',
        5,
        17000.00,
        100,
        9
    );
INSERT INTO hr.employees
VALUES (
        103,
        'Alexander',
        'Hunold',
        'alexander.hunold@sqltutorial.org',
        '590.423.4567',
        '1990-01-03',
        9,
        9000.00,
        102,
        6
    );
INSERT INTO hr.employees
VALUES (
        104,
        'Bruce',
        'Ernst',
        'bruce.ernst@sqltutorial.org',
        '590.423.4568',
        '1991-05-21',
        9,
        6000.00,
        103,
        6
    );
INSERT INTO hr.employees
VALUES (
        105,
        'David',
        'Austin',
        'david_austin@sqltutorial.org',
        '590.423.4569',
        '1997-06-25',
        9,
        4800.00,
        103,
        6
    );
INSERT INTO hr.employees
VALUES (
        106,
        'Valli',
        'Pataballa',
        'valli.pataballa@sqltutorial.org',
        '590.423.4560',
        '1998-02-05',
        9,
        4800.00,
        103,
        6
    );
INSERT INTO hr.employees
VALUES (
        107,
        'Diana',
        'Lorentz',
        'diana.lorentz@sqltutorial.org',
        '590.423.5567',
        '1999-02-07',
        9,
        4200.00,
        103,
        6
    );
INSERT INTO hr.employees
VALUES (
        108,
        'Nancy',
        'Greenberg',
        'nancy.greenberg@sqltutorial.org',
        '515.124.4569',
        '1994-08-17',
        7,
        12000.00,
        101,
        10
    );
INSERT INTO hr.employees
VALUES (
        109,
        'Daniel',
        'Faviet',
        'daniel.faviet@sqltutorial.org',
        '515.124.4169',
        '1994-08-16',
        9,
        9000.00,
        108,
        10
    );
INSERT INTO hr.employees
VALUES (
        110,
        'John',
        'Chen',
        'john.chen@sqltutorial.org',
        '515.124.4269',
        '1997-09-28',
        6,
        8200.00,
        108,
        10
    );
INSERT INTO hr.employees
VALUES (
        111,
        'Ismael',
        'Sciarra',
        'ismael.sciarra@sqltutorial.org',
        '515.124.4369',
        '1997-09-30',
        6,
        7700.00,
        108,
        10
    );
INSERT INTO hr.employees
VALUES (
        112,
        'Jose Manuel',
        'Urman',
        'jose manuel.urman@sqltutorial.org',
        '515.124.4469',
        '1998-03-07',
        6,
        7800.00,
        108,
        10
    );
INSERT INTO hr.employees
VALUES (
        113,
        'Luis',
        'Popp',
        'luis.popp@sqltutorial.org',
        '515.124.4567',
        '1999-12-07',
        6,
        6900.00,
        108,
        10
    );
INSERT INTO hr.employees
VALUES (
        114,
        'Den',
        'Raphaely',
        'den.raphaely@sqltutorial.org',
        '515.127.4561',
        '1994-12-07',
        14,
        11000.00,
        100,
        3
    );
INSERT INTO hr.employees
VALUES (
        115,
        'Alexander',
        'Khoo',
        'alexander.khoo@sqltutorial.org',
        '515.127.4562',
        '1995-05-18',
        13,
        3100.00,
        114,
        3
    );
INSERT INTO hr.employees
VALUES (
        116,
        'Shelli',
        'Baida',
        'shelli.baida@sqltutorial.org',
        '515.127.4563',
        '1997-12-24',
        13,
        2900.00,
        114,
        3
    );
INSERT INTO hr.employees
VALUES (
        117,
        'Sigal',
        'Tobias',
        'sigal.tobias@sqltutorial.org',
        '515.127.4564',
        '1997-07-24',
        13,
        2800.00,
        114,
        3
    );
INSERT INTO hr.employees
VALUES (
        118,
        'Guy',
        'Himuro',
        'guy.himuro@sqltutorial.org',
        '515.127.4565',
        '1998-11-15',
        13,
        2600.00,
        114,
        3
    );
INSERT INTO hr.employees
VALUES (
        119,
        'Karen',
        'Colmenares',
        'karen.colmenares@sqltutorial.org',
        '515.127.4566',
        '1999-08-10',
        13,
        2500.00,
        114,
        3
    );
INSERT INTO hr.employees
VALUES (
        120,
        'Matthew',
        'Weiss',
        'matthew.weiss@sqltutorial.org',
        '650.123.1234',
        '1996-07-18',
        19,
        8000.00,
        100,
        5
    );
INSERT INTO hr.employees
VALUES (
        121,
        'Adam',
        'Fripp',
        'adam.fripp@sqltutorial.org',
        '650.123.2234',
        '1997-04-10',
        19,
        8200.00,
        100,
        5
    );
INSERT INTO hr.employees
VALUES (
        122,
        'Payam',
        'Kaufling',
        'payam.kaufling@sqltutorial.org',
        '650.123.3234',
        '1995-05-01',
        19,
        7900.00,
        100,
        5
    );
INSERT INTO hr.employees
VALUES (
        123,
        'Shanta',
        'Vollman',
        'shanta.vollman@sqltutorial.org',
        '650.123.4234',
        '1997-10-10',
        19,
        6500.00,
        100,
        5
    );
INSERT INTO hr.employees
VALUES (
        126,
        'Irene',
        'Mikkilineni',
        'irene.mikkilineni@sqltutorial.org',
        '650.124.1224',
        '1998-09-28',
        18,
        2700.00,
        120,
        5
    );
INSERT INTO hr.employees
VALUES (
        145,
        'John',
        'Russell',
        'john.russell@sqltutorial.org',
        NULL,
        '1996-10-01',
        15,
        14000.00,
        100,
        8
    );
INSERT INTO hr.employees
VALUES (
        146,
        'Karen',
        'Partners',
        'karen.partners@sqltutorial.org',
        NULL,
        '1997-01-05',
        15,
        13500.00,
        100,
        8
    );
INSERT INTO hr.employees
VALUES (
        176,
        'Jonathon',
        'Taylor',
        'jonathon.taylor@sqltutorial.org',
        NULL,
        '1998-03-24',
        16,
        8600.00,
        100,
        8
    );
INSERT INTO hr.employees
VALUES (
        177,
        'Jack',
        'Livingston',
        'jack.livingston@sqltutorial.org',
        NULL,
        '1998-04-23',
        16,
        8400.00,
        100,
        8
    );
INSERT INTO hr.employees
VALUES (
        178,
        'Kimberely',
        'Grant',
        'kimberely_grant@sqltutorial.org',
        NULL,
        '1999-05-24',
        16,
        7000.00,
        100,
        8
    );
INSERT INTO hr.employees
VALUES (
        179,
        'Charles',
        'Johnson',
        'charles.johnson@sqltutorial.org',
        NULL,
        '2000-01-04',
        16,
        6200.00,
        100,
        8
    );
INSERT INTO hr.employees
VALUES (
        192,
        'Sarah',
        'Bell',
        'sarah.bell@sqltutorial.org',
        '650.501.1876',
        '1996-02-04',
        17,
        4000.00,
        123,
        5
    );
INSERT INTO hr.employees
VALUES (
        193,
        'Britney',
        'Everett',
        'britney.everett@sqltutorial.org',
        '650.501.2876',
        '1997-03-03',
        17,
        3900.00,
        123,
        5
    );
INSERT INTO hr.employees
VALUES (
        200,
        'Jennifer',
        'Whalen',
        'jennifer.whalen@sqltutorial.org',
        '515.123.4444',
        '1987-09-17',
        3,
        4400.00,
        101,
        1
    );
INSERT INTO hr.employees
VALUES (
        201,
        'Michael',
        'Hartstein',
        'michael.hartstein@sqltutorial.org',
        '515.123.5555',
        '1996-02-17',
        10,
        13000.00,
        100,
        2
    );
INSERT INTO hr.employees
VALUES (
        202,
        'Pat',
        'Fay',
        'pat.fay@sqltutorial.org',
        '603.123.6666',
        '1997-08-17',
        11,
        6000.00,
        201,
        2
    );
INSERT INTO hr.employees
VALUES (
        203,
        'Susan',
        'Mavris',
        'susan.mavris@sqltutorial.org',
        '515.123.7777',
        '1994-06-07',
        8,
        6500.00,
        101,
        4
    );
INSERT INTO hr.employees
VALUES (
        204,
        'Hermann',
        'Baer',
        'hermann_baer@sqltutorial.org',
        '515.123.8888',
        '1994-06-07',
        12,
        10000.00,
        101,
        7
    );
INSERT INTO hr.employees
VALUES (
        205,
        'Shelley',
        'Higgins',
        'shelley.higgins@sqltutorial.org',
        '515.123.8080',
        '1994-06-07',
        2,
        12000.00,
        101,
        11
    );
INSERT INTO hr.employees
VALUES (
        206,
        'William',
        'Gietz',
        'william.gietz@sqltutorial.org',
        '515.123.8181',
        '1994-06-07',
        2,
        8300.00,
        205,
        11
    );
INSERT INTO hr.employees
VALUES (
        207,
        'Stevena',
        'King',
        'steven.king@sqltutorial.org',
        '515.123.4567',
        '1987-06-17',
        4,
        24000.00,
        NULL,
        9
    );
INSERT INTO hr.employees
VALUES (
        221,
        'Neen',
        'King',
        'neen.king@sqltutorial.org',
        '515.143.4567',
        '1987-06-17',
        6,
        7500.00,
        114,
        3
    );
INSERT INTO hr.employees
VALUES (
        220,
        'Stevena',
        'Hunold',
        'sv.hunold@sqltutorial.org',
        '515.123.4569',
        '1987-06-17',
        6,
        6000.00,
        114,
        3
    );
INSERT INTO hr.employees
VALUES (
        300,
        'Lexum',
        'Chenna',
        'l.c.@sqltutorial.org',
        '515.124.4569',
        '1999-12-07',
        17,
        1000.00,
        123,
        NULL
    );
INSERT INTO hr.employees
VALUES (
        301,
        'Ruslan',
        'Roment',
        'rus.rom@sqltutorial.org',
        NULL,
        '1999-02-07',
        20,
        15000.00,
        300,
        13
    );
INSERT INTO hr.employees
VALUES (
        302,
        'Fis',
        'Gert',
        'fis.gert@sqltutorial.org',
        '515.123.5667',
        '1999-02-07',
        20,
        19000.00,
        100,
        13
    );
INSERT INTO hr.employees
VALUES (
        303,
        'Mark',
        'Johnson',
        'mark.jns@sqltutorial.org',
        NULL,
        '1987-06-19',
        14,
        9000.00,
        100,
        9
    );
-- Иждивенец
INSERT INTO hr.dependents
VALUES (1, 'Penelope', 'Popp', 'Child', 113);
INSERT INTO hr.dependents
VALUES (2, 'Nick', 'Higgins', 'Child', 205);
INSERT INTO hr.dependents
VALUES (3, 'Ed', 'Whalen', 'Child', 200);
INSERT INTO hr.dependents
VALUES (4, 'Jennifer', 'King', 'Child', 100);
INSERT INTO hr.dependents
VALUES (5, 'Johnny', 'Kochhar', 'Child', 101);
INSERT INTO hr.dependents
VALUES (6, 'Bette', 'De Haan', 'Child', 102);
INSERT INTO hr.dependents
VALUES (7, 'Grace', 'Popp', 'Child', 113);
INSERT INTO hr.dependents
VALUES (8, 'Matthew', 'Chen', 'Child', 110);
INSERT INTO hr.dependents
VALUES (9, 'Joe', 'Sciarra', 'Child', 111);
INSERT INTO hr.dependents
VALUES (10, 'Christian', 'Urman', 'Child', 112);
INSERT INTO hr.dependents
VALUES (11, 'Zero', 'Popp', 'Child', 113);
INSERT INTO hr.dependents
VALUES (12, 'Karl', 'Greenberg', 'Child', 108);
INSERT INTO hr.dependents
VALUES (13, 'Uma', 'Mavris', 'Child', 203);
INSERT INTO hr.dependents
VALUES (14, 'Vivien', 'Hunold', 'Child', 103);
INSERT INTO hr.dependents
VALUES (15, 'Cuba', 'Ernst', 'Child', 104);
INSERT INTO hr.dependents
VALUES (16, 'Fred', 'Popp', 'Child', 113);
INSERT INTO hr.dependents
VALUES (17, 'Helen', 'Pataballa', 'Child', 106);
INSERT INTO hr.dependents
VALUES (18, 'Dan', 'Lorentz', 'Child', 107);
INSERT INTO hr.dependents
VALUES (19, 'Bob', 'Hartstein', 'Child', 201);
INSERT INTO hr.dependents
VALUES (20, 'Lucille', 'Fay', 'Child', 202);
INSERT INTO hr.dependents
VALUES (21, 'Kirsten', 'Fay', 'Child', 202);
INSERT INTO hr.dependents
VALUES (22, 'Elvis', 'Khoo', 'Child', 115);
INSERT INTO hr.dependents
VALUES (23, 'Sandra', 'Baida', 'Child', 116);
INSERT INTO hr.dependents
VALUES (24, 'Cameron', 'Tobias', 'Child', 117);
INSERT INTO hr.dependents
VALUES (25, 'Kevin', 'Himuro', 'Child', 118);
INSERT INTO hr.dependents
VALUES (26, 'Rip', 'Colmenares', 'Child', 119);
INSERT INTO hr.dependents
VALUES (27, 'Julia', 'Raphaely', 'Child', 114);
INSERT INTO hr.dependents
VALUES (28, 'Woody', 'Russell', 'Child', 145);
INSERT INTO hr.dependents
VALUES (29, 'Alec', 'Partners', 'Child', 146);
INSERT INTO hr.dependents
VALUES (30, 'Sandra', 'Taylor', 'Child', 176);
INSERT INTO hr.dependents
VALUES (31, 'Karl', 'Mavris', 'Child', 203);
INSERT INTO hr.dependents
VALUES (32, 'Vivien', 'Mavris', 'Child', 203);