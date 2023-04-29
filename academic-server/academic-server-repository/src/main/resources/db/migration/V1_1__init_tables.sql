create table if not exists specializations (
    id bigserial,
    name text not null unique,
    short_name varchar(50) not null unique,
    cipher varchar(100) not null,
    parent_id bigint null,

    primary key (id),
    foreign key (parent_id) references specializations (id) on delete cascade on update cascade
);

create table if not exists disciplines (
    id bigserial,
    name text not null unique,
    short_name varchar(50) not null unique,

    primary key(id)
);

create table if not exists tags (
  id serial,
  name varchar(255) not null unique,
  parent_id bigint,

  primary key(id),
  foreign key (parent_id) references tags(id)
);

create table if not exists disciplines_tags (
  discipline_id bigint not null,
  tag_id bigint not null,

  primary key (discipline_id, tag_id),
  foreign key (discipline_id) references disciplines(id),
  foreign key (tag_id) references tags(id)
);

create table if not exists curriculums (
    id serial,
    approval_date date not null,
    admission_year integer not null,
    specialization_id bigint not null,

    primary key(id),
    foreign key (specialization_id) references specializations (id)
);
