DROP TABLE IF EXISTS users, categories, locations, events, requests, compilations, compilations_events, subscriptions CASCADE;

CREATE TABLE IF NOT EXISTS users (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        name VARCHAR(250) NOT NULL,
        email VARCHAR(254) NOT NULL,
        subscribers BOOLEAN NOT NULL,
        CONSTRAINT uq_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        name VARCHAR(50) NOT NULL,
        CONSTRAINT uq_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS locations (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        lat FLOAT NOT NULL,
        lon FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS events (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        title VARCHAR(120) NOT NULL,
        annotation VARCHAR(2000) NOT NULL,
        description VARCHAR(7000) NOT NULL,
        participant_limit BIGINT NOT NULL,
        category_id BIGINT NOT NULL REFERENCES categories(id),
        initiator_id BIGINT NOT NULL REFERENCES users(id),
        location_id BIGINT NOT NULL REFERENCES locations(id),
        created_on TIMESTAMP NOT NULL,
        event_date TIMESTAMP NOT NULL,
        paid BOOLEAN NOT NULL,
        request_moderation BOOLEAN NOT NULL,
        state VARCHAR(50) NOT NULL,
        published_on TIMESTAMP
);

CREATE TABLE IF NOT EXISTS requests (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        requester_id BIGINT NOT NULL REFERENCES users(id),
        event_id BIGINT NOT NULL REFERENCES events(id),
        status VARCHAR(50) NOT NULL,
        created TIMESTAMP
);

CREATE TABLE IF NOT EXISTS compilations (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        pinned BOOLEAN NOT NULL,
        title VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS compilations_events (
        compilation_id BIGINT NOT NULL REFERENCES compilations(id),
        event_id BIGINT NOT NULL REFERENCES events(id)
);

CREATE TABLE IF NOT EXISTS subscriptions (
        id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
        subscriber_id BIGINT NOT NULL REFERENCES users(id),
        user_id BIGINT NOT NULL REFERENCES users(id),
        created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);
