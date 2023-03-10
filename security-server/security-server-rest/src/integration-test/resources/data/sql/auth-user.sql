insert into permissions(scope, action) values
    ('USER', 'SEARCH');

insert into roles(name) values
    ('test');

insert into roles_permissions(role_id, permission_id) values
    (1,1);

insert into users(username, password,last_activity, creation_time) values
    ('test', '$2a$10$makl04WkLsNL93zWEDecNesAdNWXfhwGn9yV.qM0mBlhTrVey.WLu', timestamp '2015-01-10 00:51:14', timestamp '2015-01-10 00:51:14'); -- pass test

insert into users_roles(user_id, role_id) values
    (1,1);