package com.example.karaoke.customer_service.Interface;

import com.example.karaoke.customer_service.Entity.CustomerRevenue;
import com.example.karaoke.customer_service.Entity.Customer;

import java.util.List;

public interface CustomerMediator {
    boolean addCustomer(Customer request);
    Customer findCustomer(String phoneNumber);
    boolean updateCustomer(Customer request);
    boolean deleteCustomer(Customer request);
    List<Customer> getAllCustomers();
    List<CustomerRevenue> getAllCustomersRevenue();
}
