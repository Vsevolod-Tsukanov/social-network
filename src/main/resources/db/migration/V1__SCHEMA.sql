CREATE SCHEMA security_schema;
CREATE SCHEMA social_network_schema;


CREATE TABLE social_network_schema.users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR,
    email    VARCHAR,
    password VARCHAR,
    role VARCHAR(20)
);

CREATE TABLE social_network_schema.posts(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    title VARCHAR,
    text VARCHAR
);

CREATE TABLE social_network_schema.post_images(
    id BIGINT AUTO_INCREMENT PRIMARY KEY ,
    post_id BIGINT REFERENCES social_network_schema.posts(id),
    image BINARY
);


CREATE TABLE refresh_token
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT REFERENCES social_network_schema.users (id),
    token       VARCHAR,
    expiry_date TIMESTAMP
);

CREATE TABLE social_network_schema.user_friends
(
    user_id   BIGINT REFERENCES social_network_schema.users (id),
    friend_id BIGINT REFERENCES social_network_schema.users (id),
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE social_network_schema.user_followers
(
    user_id     BIGINT REFERENCES social_network_schema.users (id),
    follower_id BIGINT REFERENCES social_network_schema.users (id),
    PRIMARY KEY (user_id, follower_id)
);

CREATE TABLE social_network_schema.user_friendsrequests
(
    user_from_id     BIGINT REFERENCES social_network_schema.users (id),
    user_to_id BIGINT REFERENCES social_network_schema.users (id),
    PRIMARY KEY (user_from_id, user_to_id)
);

