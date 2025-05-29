CREATE TABLE tasks (
                       id          BIGSERIAL PRIMARY KEY,
                       title       VARCHAR(255) NOT NULL,
                       description TEXT,
                       status      VARCHAR(50)  NOT NULL
                           CONSTRAINT chk_status
                               CHECK (status IN ('TODO','IN_PROGRESS','DONE')),
                       user_id     BIGINT       NOT NULL
                           REFERENCES users(id)
                               ON DELETE CASCADE,
                       created_at  TIMESTAMP    NOT NULL DEFAULT now(),
                       updated_at  TIMESTAMP    NOT NULL DEFAULT now()
);
