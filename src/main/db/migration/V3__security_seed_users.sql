insert into roles (name) values ('ROLE_ADMIN'), ('ROLE_OPERADOR');

insert into users (username, password, enabled)
values
    ('admin', '{noop}admin123', true),
    ('operador', '{noop}oper123', true);

insert into user_roles(user_id, role_id)
select u.id, r.id from users u, roles r
where u.username='admin' and r.name='ROLE_ADMIN';

insert into user_roles(user_id, role_id)
select u.id, r.id from users u, roles r
where u.username='operador' and r.name='ROLE_OPERADOR';
