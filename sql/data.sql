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
    ('Mathematics - Logic And Foundations Of Mathematics', 'MLFOM', 'MLFOM cipher', null),
    ('Mechanical Engineering', 'ME', 'ME cipher', 5),
    ('Biology - Paleobiology', 'BP', 'BP cipher', 6),
    ('Education - Curriculum And Instruction', 'ECI', 'ECI cipher', 7),
    ('Earth Sciences - Soil Science', 'ESSS', 'ESSS cipher', 7),
    ('Computer Sciences - Information Science : Information Retrieval', 'CSISIR', 'CSISIR cipher', 7),
    ('Sociology - Military Sociology', 'SMS', 'SMS cipher', 7);


insert into disciplines (name, short_name) values
  ('amet','fringilla'),
  ('mi','Aliquam'),
  ('nisi','elit'),
  ('Nam','lacus'),
  ('urna','hendrerit'),
  ('dictum','faucibus'),
  ('lacus','auctor'),
  ('Quisque','velit'),
  ('cursus','adipiscingt'),
  ('non','nec,'),
  ('sociis','netus'),
  ('Nulla','adipiscing,'),
  ('ligula','Aliquamr'),
  ('enimg','at'),
  ('turpis.','in'),
  ('ametr','ipsum'),
  ('consequat','vitae'),
  ('pede,','Integer'),
  ('Proin','magna.'),
  ('lorem','adipiscing'),
  ('aliquam','Donec'),
  ('tellus','purus,'),
  ('felis','tellus'),
  ('tellusa','tellusw'),
  ('tellusf','mauris'),
  ('egetg','Etiam'),
  ('In','Praesent'),
  ('sed','elementum'),
  ('nunc','sed'),
  ('etg','ornare'),
  ('dolor','ets'),
  ('Vestibulum','cubilia'),
  ('mauris','Quisque'),
  ('tempus','ligula.'),
  ('pede','nonummy'),
  ('et','turpis'),
  ('id,','tristique'),
  ('bibendum','nec'),
  ('loremg','nisl'),
  ('Nunc','vel'),
  ('feugiat','velf'),
  ('Sed','congue'),
  ('eget','pharetra'),
  ('lacinia','mattis'),
  ('enim','gravida'),
  ('purus','nonummyg'),
  ('sollicitudin','cursus'),
  ('quis','Proin'),
  ('interdum','dolor'),
  ('malesuada','aliquet');