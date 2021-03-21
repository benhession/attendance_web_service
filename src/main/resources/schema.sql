drop table if exists student_class, student, class, "module", tutor;

create table student (
    student_id varchar(12) not null,
    forename varchar(20) not null,
    surname varchar(20) not null,

    primary key (student_id)
);

create table "module" (
    module_code varchar(5) not null,
    module_year varchar(4) not null,
    module_name varchar(50) not null,

    primary key(module_code, module_year)
);

create table tutor (
    tutor_id varchar(13) not null,
    forename varchar(20) not null,
    surname varchar(20) not null,

    primary key (tutor_id)
);

create table class (
    class_id varchar(12) not null,
    class_name varchar(50) not null,
    location varchar(50) not null,
    date_time timestamp not null,
    duration interval not null,
    class_type varchar(15) not null,
    qr_string varchar(32) not null,
    module_code varchar(5) not null,
    module_year varchar(4) not null,
    tutor_id varchar(13),

    primary key (class_id)
);

alter table class add foreign key (module_code, module_year) references "module"(module_code, module_year);
alter table class add foreign key (tutor_id) references tutor(tutor_id);

create table student_class (
    student_id varchar(12) not null,
    class_id varchar(12) not null,
    attended boolean not null,

    primary key (student_id, class_id)
);

alter table student_class add foreign key (student_id) references student(student_id);
alter table student_class add foreign key (class_id) references class(class_id);

