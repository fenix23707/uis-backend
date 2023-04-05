drop table if exists roles_permissions;
drop table if exists users_roles;
drop table if exists permissions;
drop table if exists users;
drop table if exists roles;

create table permissions (
    id serial,
    scope varchar(50) not null,
    action varchar(50) not null,

    unique(scope, action),
    primary key (id)
);

create table roles (
    id serial,
    name varchar(100) not null unique,

    primary key (id)
);

create table users (
    id bigserial,
    username text not null unique,
    password varchar(100) not null,
    last_activity timestamp null,
    creation_time timestamp not null,

    primary key (id)
);

create table roles_permissions (
    role_id serial,
    permission_id serial,

    primary key (role_id, permission_id),
    foreign key (role_id) references roles (id) on delete cascade on update cascade,
    foreign key (permission_id) references permissions (id) on delete cascade on update cascade
);

create table users_roles (
    user_id bigserial,
    role_id serial,

    primary key (user_id, role_id),
    foreign key (user_id) references users (id) on delete cascade on update cascade,
    foreign key (role_id) references roles (id) on delete cascade on update cascade
);


drop table if exists specializations;
drop table if exists disciplines_tags;
drop table if exists disciplines;
drop table if exists tags;

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
    name text not null unique,
    short_name varchar(50) not null unique,

    primary key(id)
);

create table tags (
  id serial,
  name varchar(255) not null unique,
  parent_id bigint,

  primary key(id),
  foreign key (parent_id) references tags(id)
);

create table disciplines_tags (
  discipline_id bigint not null,
  tag_id bigint not null,

  primary key (discipline_id, tag_id),
  foreign key (discipline_id) references disciplines(id),
  foreign key (tag_id) references tags(id)
);

create table curriculums (
    id serial,
    approval_date date not null,
    admission_year integer not null,
    specialization_id bigint not null,

    primary key(id),
    foreign key (specialization_id) references specializations (id)
);
