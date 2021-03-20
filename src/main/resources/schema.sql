drop table if exists student_class, student, class, "module", tutor;

create table student (
    student_id varchar(12) not null,
    forename varchar(20) not null,
    surname varchar(20) not null,

    primary key (student_id)
);

create table "module" (
    module_code varchar(5),
    module_year varchar(4),
    module_name varchar(50),

    primary key(module_code, module_year)
);

create table tutor (
    tutor_id varchar(13),
    forename varchar(20),
    surname varchar(20),

    primary key (tutor_id)
);

create table class (
    class_id varchar(12),
    class_name varchar(50),
    location varchar(50),
    date_time timestamp,
    duration interval,
    class_type varchar(15),
    qr_string varchar(50),
    module_code varchar(5),
    module_year varchar(4),
    tutor_id varchar(13),

    primary key (class_id)
);

alter table class add foreign key (module_code, module_year) references "module"(module_code, module_year);
alter table class add foreign key (tutor_id) references tutor(tutor_id);

create table student_class (
    student_id varchar(12),
    class_id varchar(12),
    attended boolean,

    primary key (student_id, class_id)
);

alter table student_class add foreign key (student_id) references student(student_id);
alter table student_class add foreign key (class_id) references class(class_id);

