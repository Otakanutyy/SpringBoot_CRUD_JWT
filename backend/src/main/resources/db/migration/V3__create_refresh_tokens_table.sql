CREATE TABLE refresh_tokens (
                                id           BIGSERIAL PRIMARY KEY,
                                token        VARCHAR(512) NOT NULL UNIQUE,
                                user_id      BIGINT     NOT NULL
                                    REFERENCES users(id)
                                        ON DELETE CASCADE,
                                expiry_date  TIMESTAMP  NOT NULL,
                                created_at   TIMESTAMP  NOT NULL DEFAULT now()
);
