insert into permissions(scope, action) values
    ('specialization', 'get'),
    ('specialization', 'search'),
    ('specialization', 'create'),
    ('specialization', 'update'),
    ('discipline',     'get'),
    ('discipline',     'search'),
    ('discipline',     'create'),
    ('discipline',     'update'),
    ('user',           'read'),
    ('user',           'search'),
    ('user',           'create'),
    ('user',           'manage_roles'),
    ('role',           'create'),
    ('role',           'read');

insert into roles(name) values
    ('admin');

insert into users(username, password,last_activity, creation_time) values
    ('admin', '$2a$10$g8zjne897r0qqr1pcsjarun8qvgs8xtmqkbjnnbrlm6.w11p2bt.m',timestamp '2015-01-10 00:51:14', timestamp '2015-01-10 00:51:14'), -- pass admin
    ('root', '$2a$10$v.tpymlerguzxnmiae6yqee2mlckhxtyc5vcgqlreghqb/6.esojo', timestamp '2015-01-10 00:51:14', timestamp '2015-01-10 00:51:14'), -- pass root
    ('test', '$2a$10$makl04wklsnl93zwedecnesadnwxfhwgn9yv.qm0mblhtrvey.wlu', timestamp '2015-01-10 00:51:14', timestamp '2015-01-10 00:51:14'); -- pass test

insert into roles_permissions(role_id, permission_id) values
    (1,1),
    (1,2),
    (1,3),
    (1,4),
    (1,5),
    (1,6),
    (1,7),
    (1,8),
    (1,9),
    (1,10),
    (1,11),
    (1,12),
    (1,13),
    (1,14);

insert into users_roles(user_id, role_id) values
    (1,1);

insert into specializations (name, short_name, cipher, parent_id) values
    ('applied computer science', 'acs', 'pi cipher', null),
    ('computer systems software', 'css', 'css cipher', 1),
    ('web programming and computer design', 'wpacd', 'wpacd cipher', 1),
    ('information technology software', 'its', 'its cipher', null),
    ('mathematics - logic and foundations of mathematics', 'mlfom', 'mlfom cipher', null),
    ('mechanical engineering', 'me', 'me cipher', 5),
    ('biology - paleobiology', 'bp', 'bp cipher', 6),
    ('education - curriculum and instruction', 'eci', 'eci cipher', 7),
    ('earth sciences - soil science', 'esss', 'esss cipher', 7),
    ('computer sciences - information science : information retrieval', 'csisir', 'csisir cipher', 7),
    ('sociology - military sociology', 'sms', 'sms cipher', 7);

insert into disciplines (name, short_name) values
    ('computer science', 'cs'),
    ('mathematics', 'math'),
    ('english literature', 'englit'),
    ('physics', 'phys'),
    ('biology', 'bio'),
    ('chemistry', 'chem'),
    ('history', 'hist'),
    ('psychology', 'psy'),
    ('sociology', 'soc');

insert into tags (name, parent_id) values
    ('programming', null),
    ('data structures', 1),
    ('algorithms', 1),
    ('calculus', null),
    ('linear algebra', 4),
    ('differential equations', 4),
    ('american literature', null),
    ('british literature', null),
    ('classical mechanics', null),
    ('thermodynamics', null),
    ('evolutionary biology', null),
    ('cell biology', null),
    ('organic chemistry', null),
    ('inorganic chemistry', null),
    ('european history', null),
    ('asian history', null),
    ('abnormal psychology', null),
    ('social psychology', null),
    ('cultural sociology', null);

insert into disciplines_tags (discipline_id, tag_id) values
    (1, 1),
    (1, 2),
    (1, 3),
    (2, 4),
    (2, 5),
    (2, 6),
    (3, 7),
    (3, 8),
    (4, 9),
    (4, 10),
    (5, 11),
    (5, 12),
    (6, 13),
    (6, 14),
    (7, 15),
    (7, 16),
    (8, 17),
    (8, 18),
    (9, 19);