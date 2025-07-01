package com.wayzor.wayzor_backend.service;

import com.wayzor.wayzor_backend.dto.BookingResponse;
import com.wayzor.wayzor_backend.dto.CreateBookingRequest;
import com.wayzor.wayzor_backend.entity.Booking;
import com.wayzor.wayzor_backend.entity.TouristPackage;
import com.wayzor.wayzor_backend.entity.User;
import com.wayzor.wayzor_backend.enums.BookingStatus;
import com.wayzor.wayzor_backend.exception.ApiException;
import com.wayzor.wayzor_backend.repository.BookingRepository;
import com.wayzor.wayzor_backend.repository.TouristPackageRepository;
import com.wayzor.wayzor_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TouristPackageRepository packageRepository;
    private final UserRepository userRepository;

    public BookingResponse bookPackage(CreateBookingRequest request, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found"));

        TouristPackage touristPackage = packageRepository.findById(request.getPackageId())
                .orElseThrow(() -> new ApiException("Package not found"));

        double totalPrice = request.getNumberOfPeople() * touristPackage.getPrice();

        Booking booking = Booking.builder()
                .user(user)
                .touristPackage(touristPackage)
                .bookingDate(LocalDate.now())
                .travelDate(request.getTravelDate())
                .numberOfPeople(request.getNumberOfPeople())
                .totalPrice(totalPrice)
                .status(BookingStatus.CONFIRMED)
                .build();

        Booking saved = bookingRepository.save(booking);

        return new BookingResponse(
                saved.getId(),
                touristPackage.getTitle(),
                saved.getBookingDate(),
                saved.getTravelDate(),
                saved.getNumberOfPeople(),
                saved.getTotalPrice(),
                saved.getStatus()
        );
    }
}
