insert into permissions(scope, action) values
    ('SUBJECT', 'READ');

insert into roles(name) values
    ('student');

insert into users(username, password,status) values
    ('admin', '$2a$10$g8ZjNE897R0QQR1pcsJARun8qvgS8xtMQKBJNNbRLm6.w11P2bt.m','ACTIVE'), -- pass admin
    ('root', '$2a$10$V.TPYMleRGuZxnmiaE6YqeE2MLckhXtYc5vCGqLreghqB/6.ESoJO', 'ACTIVE'), -- pass root
    ('test', '$2a$10$makl04WkLsNL93zWEDecNesAdNWXfhwGn9yV.qM0mBlhTrVey.WLu', 'ACTIVE'); -- pass test

insert into roles_permissions(role_id, permission_id) values
    (1,1);

insert into users_roles(user_id, role_id) values
    (1,1);

insert into specializations (name, short_name, cipher, parent_id) values
    ('Applied Computer Science', 'ACS', 'PI cipher', null),
    ('Computer systems software', 'CSS', 'CSS cipher', 1),
    ('Web programming and computer design', 'WPACD', 'WPACD cipher', 1),
    ('Information technology software', 'ITS', 'ITS cipher', null),
    ('Mathematics and Computer science', 'MACS', 'MACS cipher', null);


