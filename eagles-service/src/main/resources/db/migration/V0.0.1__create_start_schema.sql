CREATE TABLE booking
(
    id         BIGSERIAL NOT NULL
        constraint pk_customer primary key,
    room_id    BIGINT    NOT NULL,
    client_id  BIGINT    NOT NULL,
    start_date date      NOT NULL,
    end_date   date      NOT NULL
);
