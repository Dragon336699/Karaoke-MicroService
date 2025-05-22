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
import java.util.List;

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
    public List<CustomerRevenue> getAllCustomersRevenue() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerRevenue> customersRevenue = customers.stream()
                .map(customer -> mapper.map(customer, CustomerRevenue.class))
                .toList();
        ResponseEntity<List<CustomerRevenue>> customersRevenueResponse = bookingClient.getUsersBooking(customersRevenue);
        List<CustomerRevenue> customersRevenueFull = customersRevenueResponse.getBody();
        customersRevenueFull.forEach((customer) -> {
            final BigDecimal[] totalRevenue = {BigDecimal.ZERO};
            customer.getBookings().forEach((booking) -> {
                BigDecimal bookingRevenue  = booking.getBookedRooms().stream()
                        .map(bookedRoom -> {
                            Duration duration = Duration.between(bookedRoom.getCheckInTime(), bookedRoom.getCheckOutTime());
                            BigDecimal hours = BigDecimal.valueOf(duration.toMinutes() / 60);
                            return bookedRoom.getPriceAtBookTime().multiply(hours);
                        })
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                totalRevenue[0] = totalRevenue[0].add(bookingRevenue);
            });
            customer.setRevenue(totalRevenue[0]);
        });
        return customersRevenueFull;
    }
}
