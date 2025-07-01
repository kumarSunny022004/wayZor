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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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

    public List<BookingResponse> getUserBookings(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found"));

        return bookingRepository.findByUserId(user.getId())
                .stream()
                .map(booking -> new BookingResponse(
                        booking.getId(),
                        booking.getTouristPackage().getTitle(),
                        booking.getBookingDate(),
                        booking.getTravelDate(),
                        booking.getNumberOfPeople(),
                        booking.getTotalPrice(),
                        booking.getStatus()
                ))
                .toList();
    }

    @Transactional
    public void cancelBooking(Long bookingId, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ApiException("Booking not found"));

        if (booking.getUser().getId() != user.getId()) {
            throw new ApiException("You are not authorized to cancel this booking");
        }

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new ApiException("Booking is already cancelled");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }

    public List<BookingResponse> getBookingsForHost(String hostEmail) {
        User host = userRepository.findByEmail(hostEmail)
                .orElseThrow(() -> new ApiException("Host not found"));

        List<Booking> bookings = bookingRepository.findByTouristPackage_HostId(host.getId());

        return bookings.stream()
                .map(booking -> new BookingResponse(
                        booking.getId(),
                        booking.getTouristPackage().getTitle(),
                        booking.getBookingDate(),
                        booking.getTravelDate(),
                        booking.getNumberOfPeople(),
                        booking.getTotalPrice(),
                        booking.getStatus()
                ))
                .toList();
    }



}
