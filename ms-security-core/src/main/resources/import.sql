INSERT INTO ms_roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO ms_roles (name) VALUES ('ROLE_USER');

INSERT INTO ms_users (username, password, email, enabled) VALUES ('admin', 'admin', 'admin@gmail.com', true);
INSERT INTO ms_users (username, password, email, enabled) VALUES ('user', 'user', 'user@gmail.com', true);

INSERT INTO ms_users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO ms_users_roles (user_id, role_id) VALUES (2, 2);

