insert into users(username, password, status) values
    ('admin', '$2a$10$g8ZjNE897R0QQR1pcsJARun8qvgS8xtMQKBJNNbRLm6.w11P2bt.m', 'ACTIVE'), -- pass admin
    ('root', '$2a$10$V.TPYMleRGuZxnmiaE6YqeE2MLckhXtYc5vCGqLreghqB/6.ESoJO', 'ACTIVE'), -- pass root
    ('test', '$2a$10$makl04WkLsNL93zWEDecNesAdNWXfhwGn9yV.qM0mBlhTrVey.WLu', 'ACTIVE'); -- pass test

insert into roles(name) values
    ('ADMIN'),
    ('DEFAULT');

insert into users_roles(user_id, role_id) values
    (1, 1),
    (1, 2),
    (2, 1),
    (3, 2);