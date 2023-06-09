insert into security.permissions (id, scope, action, application_name) values
    (1, 'user', 'read', 'security-server');

insert into security.methods (permission_id, description) values
    (1, '1'),
    (1, '2'),
    (1, '3');
