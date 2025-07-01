
CREATE TABLE bookings (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    package_id BIGINT NOT NULL,
    booking_date DATE NOT NULL,
    travel_date DATE NOT NULL,
    number_of_people INT NOT NULL,
    total_price DOUBLE PRECISION NOT NULL,
    status VARCHAR(20) NOT NULL,

    CONSTRAINT fk_booking_user FOREIGN KEY (user_id)
        REFERENCES users(id) ON DELETE CASCADE,

    CONSTRAINT fk_booking_package FOREIGN KEY (package_id)
        REFERENCES tourist_package(id) ON DELETE CASCADE
);
