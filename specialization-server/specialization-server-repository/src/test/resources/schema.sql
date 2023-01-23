drop table if exists specializations;

create table specializations (
    id bigserial,
    name varchar(100) not null unique,
    short_name varchar(50) not null unique,
    cipher varchar(100) not null,
    parent_id bigint null,

    primary key (id),
    foreign key (parent_id) references specializations (id) on delete cascade on update cascade
);

-- sequence

--drop sequence if exists specializations_seq;

--create sequence specializations_seq start 1 increment 50;
