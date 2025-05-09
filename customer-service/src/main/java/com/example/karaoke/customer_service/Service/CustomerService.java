package com.example.karaoke.customer_service.Service;

import com.example.karaoke.customer_service.Response.CustomerRevenue;
import com.example.karaoke.customer_service.Entity.Customer;
import com.example.karaoke.customer_service.Mediator.CustomerMediatorImp;
import com.example.karaoke.customer_service.Request.AddCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerMediatorImp customerMediator;

    public boolean addCustomer(AddCustomerRequest request) {
        return customerMediator.addCustomer(request);
    }

    public Customer findCustomer(String phoneNumber) {
        return customerMediator.findCustomer(phoneNumber);
    }

    public boolean updateCustomer(AddCustomerRequest request) {
        return customerMediator.updateCustomer(request);
    }

    public boolean deleteCustomer(AddCustomerRequest request) {
        return customerMediator.deleteCustomer(request);
    }

    public List<Customer> getAllCustomers() {
        return customerMediator.getAllCustomers();
    }

    public List<CustomerRevenue> getAllCustomersRevenue() {
        return customerMediator.getAllCustomersRevenue();
    }
}
