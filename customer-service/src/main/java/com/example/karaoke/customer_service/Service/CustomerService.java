package com.example.karaoke.customer_service.Service;

import com.example.karaoke.customer_service.Entity.CustomerRevenue;
import com.example.karaoke.customer_service.Entity.Customer;
import com.example.karaoke.customer_service.Mediator.CustomerMediatorImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerMediatorImp customerMediator;

    public boolean addCustomer(Customer request) {
        return customerMediator.addCustomer(request);
    }

    public Customer findCustomer(String phoneNumber) {
        return customerMediator.findCustomer(phoneNumber);
    }

    public boolean updateCustomer(Customer request) {
        return customerMediator.updateCustomer(request);
    }

    public boolean deleteCustomer(Customer request) {
        return customerMediator.deleteCustomer(request);
    }

    public List<Customer> getAllCustomers() {
        return customerMediator.getAllCustomers();
    }

    public List<CustomerRevenue> getAllCustomersRevenue() {
        return customerMediator.getAllCustomersRevenue();
    }
}
