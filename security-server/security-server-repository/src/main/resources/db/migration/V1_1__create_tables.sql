create table permissions (
    id serial,
    scope varchar(100) not null,
    action varchar(100) not null,

    unique(scope, action),
    primary key (id)
);

create table roles (
    id serial,
    name text not null unique,

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