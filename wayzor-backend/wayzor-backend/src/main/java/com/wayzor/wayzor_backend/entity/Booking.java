package com.wayzor.wayzor_backend.entity;

import com.wayzor.wayzor_backend.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private TouristPackage touristPackage;

    private LocalDate bookingDate;
    private LocalDate travelDate;
    private int numberOfPeople;
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}
