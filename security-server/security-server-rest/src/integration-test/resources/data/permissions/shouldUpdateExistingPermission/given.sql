insert into security.permissions (id, scope, action, application_name)
values (100, 'user', 'read', 'security-server'),
       (200, 'permission', 'read', 'security-server'),
       (300, 'role', 'search', 'security-server'),
       (400, 'role', 'read', 'security-server'),
       (500, 'role', 'create', 'security-server'),
       (600, 'specialization', 'create', 'academic-server');

insert into security.methods (permission_id, description)
values (100, 'user desc 1'),
       (100, 'user desc 2'),
       (100, 'user desc 3'),
       (200, 'permission desc 1'),
       (300, 'role desc 1'),
       (400, 'role desc 2'),
       (500, 'role desc 3'),
       (600, 'specialization desc 3');
