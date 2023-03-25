/*DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS events CASCADE;*/


CREATE TABLE IF NOT EXISTS users
(
    users_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    email    VARCHAR(256)                            NOT NULL,
    name     VARCHAR(256)                            NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (users_id),
    CONSTRAINT uq_users_email UNIQUE (email)
);


CREATE TABLE IF NOT EXISTS categories
(
    categories_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name          VARCHAR(256)                            NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (categories_id)
);

CREATE TABLE IF NOT EXISTS location
(
    location_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    latitude    REAL                                    NOT NULL,
    longitude   REAL                                    NOT NULL,
    CONSTRAINT pk_location PRIMARY KEY (location_id)
);

CREATE TABLE IF NOT EXISTS events
(
    events_id          BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    category_id        BIGINT REFERENCES categories (categories_id),
    user_id            BIGINT REFERENCES users (users_id),
    location_id        BIGINT REFERENCES location (location_id),
    annotation         VARCHAR(2000)                           NOT NULL,
    CONSTRAINT annotation_min_value CHECK (LENGTH(annotation) > 20),
    description        VARCHAR(7000)                           NOT NULL
        CONSTRAINT description_min_value CHECK (LENGTH(description) > 20),
    event_date         TIMESTAMP                               NOT NULL,
    paid               BOOLEAN                                 NOT NULL,
    participant_limit  INTEGER                                 NOT NULL,
    CONSTRAINT participant_limit_min_value CHECK ((participant_limit) > 0),
    request_moderation BOOLEAN                                 NOT NULL,
    title              VARCHAR(120)                            NOT NULL,
    created_on         TIMESTAMP                               NOT NULL,
    published_on       TIMESTAMP,
    state              VARCHAR(56),
    CONSTRAINT title_min_value CHECK (LENGTH(title) > 3),
    CONSTRAINT pk_events PRIMARY KEY (events_id)
);
