insert into student (student_id, forename, surname) values
('yarrowp3138', 'Peter', 'Yarrow'),
('traversm0936', 'Mary', 'Travers'),
('stookeyp3037', 'Paul', 'Stookey'),
('lennonj0940', 'John', 'Lennon'),
('mccartnp1842', 'Paul', 'McCartney'),
('harrisog2543', 'George', 'Harrison'),
('starrr0740', 'Ringo', 'Starr')

on conflict do nothing;

insert into tutor (tutor_id, forename, surname) values
    ('dylanb2441', 'Bob', 'Dylan'),
    ('bloomfieldm2843', 'Mike', 'Bloomfield')
on conflict do nothing;

insert into module (module_code, module_year, module_name) values
    ('TM470', '2021', 'The computing and IT project'),
    ('TM351', '2020','Data management and analysis'),
    ('TM354', '2021', 'Software engineering')
on conflict do nothing;

insert into class (class_id, class_name, location, date_time, duration,
                   class_type, qr_string, module_code, module_year, tutor_id) values
    ('TM4702101', 'Introduction to TM470', 'Berrill Lecture Theatre', timestamp'2021-03-29 10:00:00 UTC',
     interval '1 hour', 'Lecture', '78C4100F08DDFEBDBFF91F2EF7C1ECDC', 'TM470', '2021', 'dylanb2441'),
    ('TM4702102', 'TMA01', 'Berrill Lecture Theatre', timestamp'2021-05-15 14:00:00 UTC', interval '1 hour',
     'Lecture', '21312ED8F6820DED94188D177A73170E', 'TM470', '2021', 'dylanb2441'),
    ('TM4702103', 'TMA02', 'Berrill Lecture Theatre', timestamp'2021-07-15 10:30:00 UTC', interval '1 hour',
     'Lecture', 'BB52F36E1C9E10909733B2FCA23290FA', 'TM470', '2021', 'dylanb2441'),
    ('TM3512001', 'Introduction to TM351', 'Hub Theatre', timestamp'2021-07-20 19:59:00 UTC', interval '1 hour',
     'lecture', '187BB4BEDFD30EF473227C8AFC5F8283', 'TM351', '2020', 'dylanb2441'),
    ('TM3542101', 'Introduction to TM354', 'Hub Theatre', timestamp'2021-08-14 15:57:00 UTC', interval '1 hour',
     'lecture', 'E0A96F9650F94263C35B7CE59CA3DBB9', 'TM354', '2021', 'dylanb2441'),
    ('TM3542102', 'What is Software Engineering?', 'Hub Theatre', timestamp'2021-08-18 10:00:00 UTC', interval '1 hour',
     'lecture', '70704F5DFB5CB49AF03499E4293EFE75', 'TM354', '2021', 'dylanb2441')

 on conflict do nothing;

insert into student_class (student_id, class_id, attended) values
    ('yarrowp3138', 'TM4702101', false),
    ('yarrowp3138', 'TM4702102', true),
    ('yarrowp3138', 'TM4702103', true),
    ('yarrowp3138', 'TM3512001', false),
    ('yarrowp3138', 'TM3542101', false),
    ('traversm0936', 'TM4702101', true),
    ('traversm0936', 'TM4702102', false),
    ('traversm0936', 'TM4702103', false),
    ('traversm0936', 'TM3542101', false),
    ('traversm0936', 'TM3542102', false),
    ('lennonj0940', 'TM3542101', false),
    ('mccartnp1842', 'TM3542101', false),
    ('harrisog2543', 'TM3542101', false),
    ('starrr0740', 'TM3542101', false),
    ('lennonj0940', 'TM3542102', false),
    ('mccartnp1842', 'TM3542102', false),
    ('harrisog2543', 'TM3542102', false),
    ('starrr0740', 'TM3542102', false)
on conflict do nothing;
