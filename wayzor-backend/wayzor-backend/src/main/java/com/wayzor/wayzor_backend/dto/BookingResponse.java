package com.wayzor.wayzor_backend.dto;

import com.wayzor.wayzor_backend.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private String packageTitle;
    private LocalDate getBookingDate;
    private LocalDate travelDate;
    private int numberOfPeople;
    private double totalPrice;
    private BookingStatus status;
}
