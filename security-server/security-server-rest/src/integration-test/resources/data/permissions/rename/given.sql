insert into security.permissions (id, scope, action, application_name) values
    (1001, 'user', 'read', 'security-server'),
    (1002, 'user', 'search', 'security-server'),
    (1003, 'user', 'update', 'security-server');

insert into security.methods (permission_id, description) values
    (1001, '1'),
    (1001, '2'),
    (1002, '5'),
    (1002, '6'),
    (1002, '7'),
    (1003, '3');
