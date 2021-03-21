insert into student (student_id, forename, surname) values
    ('yarrowP3138', 'Peter', 'Yarrow'),
    ('traversM0936', 'Mary', 'Travers'),
    ('stookeyP3037', 'Paul', 'Stookey')
on conflict do nothing;

insert into tutor (tutor_id, forename, surname) values
    ('dylanb2441', 'Bob', 'Dylan')
on conflict do nothing;

insert into module (module_code, module_year, module_name) values
    ('TM470', '2021', 'The computing and IT project'),
    ('TM351', '2020','Data management and analysis')
on conflict do nothing;

insert into class (class_id, class_name, location, date_time, duration,
                   class_type, qr_string, module_code, module_year, tutor_id) values
    ('TM4702101', 'Introduction to TM470', 'Berrill Lecture Theatre', timestamp'2021-02-08 10:00:00 GMT',
     interval '1 hour', 'Lecture', '78C4100F08DDFEBDBFF91F2EF7C1ECDC', 'TM470', '2021', 'dylanb2441'),
    ('TM4702102', 'TMA01', 'Berrill Lecture Theatre', timestamp'2021-02-15 10:00:00 GMT', interval '1 hour',
     'Lecture', '21312ED8F6820DED94188D177A73170E', 'TM470', '2021', 'dylanb2441'),
    ('TM4702103', 'TMA02', 'Berrill Lecture Theatre', timestamp'2021-03-23 11:00:00 GMT', interval '1 hour',
     'Lecture', 'BB52F36E1C9E10909733B2FCA23290FA', 'TM470', '2021', 'dylanb2441')
 on conflict do nothing;

insert into student_class (student_id, class_id, attended) values
    ('yarrowP3138', 'TM4702101', true),
    ('yarrowP3138', 'TM4702102', true),
    ('yarrowP3138', 'TM4702103', false),
    ('traversM0936', 'TM4702101', false),
    ('traversM0936', 'TM4702102', true),
    ('traversM0936', 'TM4702103', false)
on conflict do nothing;
