insert into security.permissions (id, scope, action, application_name) values
    (1001, 'user', 'read', 'security-server'),
    (1002, 'user', 'create', 'security-server');

insert into security.methods (permission_id, description) values
    (1001, '1'),
    (1001, '2'),
    (1001, '3'),
    (1002, '5');
