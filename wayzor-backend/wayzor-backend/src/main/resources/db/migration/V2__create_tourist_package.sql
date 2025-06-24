CREATE TABLE tourist_package (
    id BIGSERIAL PRIMARY KEY,
    city VARCHAR(100),
    title VARCHAR(100),
    description TEXT,
    days INT,
    nights INT,
    price DOUBLE PRECISION,
    hotel_name VARCHAR(100),
    host_id BIGINT,
    CONSTRAINT fk_host FOREIGN KEY (host_id) REFERENCES users(id)
);
