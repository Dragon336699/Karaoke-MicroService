package com.example.karaoke.customer_service.Interface;

import com.example.karaoke.customer_service.Response.CustomerRevenue;
import com.example.karaoke.customer_service.Entity.Customer;
import com.example.karaoke.customer_service.Request.AddCustomerRequest;

import java.util.List;

public interface CustomerMediator {
    boolean addCustomer(AddCustomerRequest request);
    Customer findCustomer(String phoneNumber);
    boolean updateCustomer(AddCustomerRequest request);
    boolean deleteCustomer(AddCustomerRequest request);
    List<Customer> getAllCustomers();
    List<CustomerRevenue> getAllCustomersRevenue();
}
