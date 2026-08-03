CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS roles
(
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    role_name   VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
);

INSERT INTO roles (role_name, description)
VALUES ('ADMIN', 'Administrator with full access'),
       ('USER', 'Standard user with limited access')
ON CONFLICT (role_name) DO UPDATE
    SET description = EXCLUDED.description;

CREATE TABLE IF NOT EXISTS user_role
(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);
