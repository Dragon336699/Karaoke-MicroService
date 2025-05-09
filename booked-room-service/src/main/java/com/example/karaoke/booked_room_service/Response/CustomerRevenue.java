package com.example.karaoke.booked_room_service.Response;

import com.example.karaoke.booked_room_service.Entity.Booking;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CustomerRevenue {
    private UUID Id;
    private String fullName;
    private String phoneNumber;
    private BigDecimal revenue;
    private List<Booking> bookings;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getRevenue() {
        return revenue;
    }

    public void setRevenue(BigDecimal revenue) {
        this.revenue = revenue;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
