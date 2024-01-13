INSERT INTO `usuarios` (username, password, enable, nombre, apellido, email) VALUES ('aaredo', 'admin', 1, 'Alex', 'Aredo', 'aaredo@gmail.com');
INSERT INTO `usuarios` (username, password, enable, nombre, apellido, email) VALUES ('loana', 'admin', 1, 'Loana', 'Yanez', 'loa@gmail.com');

INSERT INTO `roles` (nombre) VALUES ('ROLE_USER');
INSERT INTO `roles` (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (1,1);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2,2);
INSERT INTO `usuarios_roles` (usuario_id, role_id) VALUES (2,1);
