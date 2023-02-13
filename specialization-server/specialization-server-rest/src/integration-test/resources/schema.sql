drop table if exists specializations;
drop table if exists disciplines;

create table specializations (
    id bigserial,
    name varchar(100) not null unique,
    short_name varchar(50) not null unique,
    cipher varchar(100) not null,
    parent_id bigint null,

    primary key (id),
    foreign key (parent_id) references specializations (id) on delete cascade on update cascade
);

create table disciplines (
    id bigserial,
    name varchar(200) not null unique,
    short_name varchar(50) not null unique,

    primary key(id)
);
