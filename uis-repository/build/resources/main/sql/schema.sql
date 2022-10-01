create table users (
    id bigserial,
    username varchar(80) unique not null,
    password varchar(100) not null,
    status varchar(30),

    primary key(id)
);

create table roles(
    id serial,
    name varchar(30) unique not null,

    primary key(id)
);

create table users_roles(
    user_id bigint,
    role_id int,

    primary key(user_id, role_id),
    foreign key (role_id) references roles(id),
    foreign key (user_id) references users(id)
)