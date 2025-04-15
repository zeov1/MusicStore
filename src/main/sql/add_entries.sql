-- users

SELECT *
FROM users;

TRUNCATE users;

-- password: zeovl123

INSERT INTO users(username, password, email)
VALUES ('zeovl', '$2a$10$Xq8862XY3CCOf/g9NHSdHO9cD5nager7EFkdxSEBR9aGLuIWm17v6', 'zeovl@vk.com');

INSERT INTO users(username, password, email)
VALUES ('pavel', '$2a$10$Xq8862XY3CCOf/g9NHSdHO9cD5nager7EFkdxSEBR9aGLuIWm17v6', 'pavel@example.com');

INSERT INTO users(username, password, email)
VALUES ('alex', '$2a$10$Xq8862XY3CCOf/g9NHSdHO9cD5nager7EFkdxSEBR9aGLuIWm17v6', 'alex@example.com');

