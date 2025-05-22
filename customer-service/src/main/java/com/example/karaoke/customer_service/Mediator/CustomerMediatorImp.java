package com.example.karaoke.customer_service.Mediator;

import com.example.karaoke.customer_service.Entity.CustomerRevenue;
import com.example.karaoke.customer_service.Entity.Customer;
import com.example.karaoke.customer_service.Interface.BookingClient;
import com.example.karaoke.customer_service.Interface.CustomerMediator;
import com.example.karaoke.customer_service.Repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerMediatorImp implements CustomerMediator {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BookingClient bookingClient;

    @Override
    public boolean addCustomer(Customer request) {
        boolean checkExist = customerRepository.existsByPhoneNumber(request.getPhoneNumber());
        if (!checkExist) {
            Customer customer = new Customer();
            customer.setFullName(request.getFullName());
            customer.setPhoneNumber(request.getPhoneNumber());
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Override
    public Customer findCustomer(String phoneNumber) {
        Customer customer = customerRepository.findByPhoneNumber(phoneNumber);
        if (customer == null) {
            return null;
        }
        return customer;
    }

    @Override
    public boolean updateCustomer (Customer request) {
        Customer existingCustomer = customerRepository.findByPhoneNumber(request.getPhoneNumber());
        existingCustomer.setPhoneNumber(request.getPhoneNumber());
        existingCustomer.setFullName(request.getFullName());
        customerRepository.save(existingCustomer);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteCustomer(Customer request) {
        long rowEffected = customerRepository.deleteByPhoneNumber(request.getPhoneNumber());
        return rowEffected > 0;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Map<String, Object>> getAllCustomersRevenue() {
        List<Customer> customers = customerRepository.findAll();
        List<Map<String, Object>> customersRevenue = customers.stream()
                .map(customer -> {
                    Map<String, Object> mp = new HashMap<>();
                    mp.put("id", customer.getId());
                    mp.put("fullName", customer.getFullName());
                    mp.put("phoneNumber", customer.getPhoneNumber());
                    return mp;
                })
                .toList();
        ResponseEntity<List<Map<String, Object>>> customersRevenueResponse = bookingClient.getUsersBooking(customersRevenue);
        List<Map<String, Object>> customersRevenueFull = customersRevenueResponse.getBody();
        customersRevenueFull.forEach((customer) -> {
            final BigDecimal[] totalRevenue = {BigDecimal.ZERO};
            List<Map<String, Object>> customerBookings = (List<Map<String, Object>>) customer.get("bookings");
            if (!customerBookings.isEmpty()) {
                customerBookings.forEach((booking) -> {
                    List<Map<String, Object>> bookedRooms = (List<Map<String, Object>>) booking.get("bookedRooms");
                    if (!bookedRooms.isEmpty()) {
                        BigDecimal bookingRevenue  = bookedRooms.stream()
                                .map(bookedRoom -> {
                                    LocalDateTime checkInTime = LocalDateTime.parse((String) bookedRoom.get("checkInTime"));
                                    LocalDateTime checkOutTime = LocalDateTime.parse((String) bookedRoom.get("checkOutTime"));

                                    Duration duration = Duration.between(checkInTime, checkOutTime);
                                    BigDecimal hours = BigDecimal.valueOf(duration.toMinutes() / 60);

                                    Object priceObj = bookedRoom.get("priceAtBookTime");
                                    BigDecimal priceAtBookTime;
                                    if (priceObj instanceof BigDecimal) {
                                        priceAtBookTime = (BigDecimal) priceObj;
                                    } else if (priceObj instanceof Number) {
                                        priceAtBookTime = BigDecimal.valueOf(((Number) priceObj).doubleValue());
                                    } else {
                                        priceAtBookTime = new BigDecimal(priceObj.toString());
                                    }

                                    return priceAtBookTime.multiply(hours);
                                })
                                .reduce(BigDecimal.ZERO, BigDecimal::add);
                        totalRevenue[0] = totalRevenue[0].add(bookingRevenue);
                    }
                });
            }

            customer.put("revenue", totalRevenue[0]);
        });
        return customersRevenueFull;
    }
}
