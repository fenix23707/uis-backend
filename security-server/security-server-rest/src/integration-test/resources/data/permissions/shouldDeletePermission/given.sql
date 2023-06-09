insert into security.permissions (id, scope, action, application_name) values
    (1, 'user', 'read', 'security-server'),
    (2, 'user', 'get', 'security-server'),
    (3, 'user', 'create', 'security-server');

insert into security.methods (permission_id, description) values
    (1, '1'),
    (1, '2'),
    (2, '3'),
    (2, '4'),
    (2, '5'),
    (3, '7'),
    (3, '8');