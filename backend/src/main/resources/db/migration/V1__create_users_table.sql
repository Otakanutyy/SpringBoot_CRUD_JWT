CREATE TABLE users (
                       id             BIGSERIAL PRIMARY KEY,
                       email          VARCHAR(255) NOT NULL UNIQUE,
                       password_hash  VARCHAR(255) NOT NULL,
                       role      VARCHAR(50)  NOT NULL
                           CONSTRAINT chk_role
                               CHECK (role IN ('ADMIN','USER')),
                       created_at     TIMESTAMP    NOT NULL DEFAULT now(),
                       updated_at     TIMESTAMP    NOT NULL DEFAULT now()
);
