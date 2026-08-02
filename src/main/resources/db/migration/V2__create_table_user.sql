CREATE TABLE IF NOT EXISTS user
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE IF NOT EXISTS role
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name   VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

INSERT INTO role (role_name, description)
VALUES ('ADMIN', 'Administrator with full access'),
       ('USER', 'Standard user with limited access')
ON DUPLICATE KEY UPDATE
    description = VALUES(description);

CREATE TABLE IF NOT EXISTS user_role
(
    user_id BIGINT,
    role_id BIGINT,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role (id) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;