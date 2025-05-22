package com.example.karaoke.customer_service.Controller;

import com.example.karaoke.customer_service.Entity.Customer;
import com.example.karaoke.customer_service.Entity.CustomerRevenue;
import com.example.karaoke.customer_service.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/addCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody Customer request) {
        boolean addCustomerStatus = customerService.addCustomer(request);
        if (!addCustomerStatus) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Custmer already exists, can not create");
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/getCustomer")
    public ResponseEntity<Customer> getCustomerByPhoneNumber(@RequestParam String phoneNumber) {
        Customer customer = customerService.findCustomer(phoneNumber);
        if (customer == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/getAllCustomers")
    public ResponseEntity<List<Customer>> GetAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(customers);
    }

    @GetMapping("/getCustomersRevenue")
    public ResponseEntity<List<Map<String, Object>>> GetCustomersRevenue() {
        List<Map<String, Object>> customersRevenue = customerService.getAllCustomersRevenue();
        if (customersRevenue == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(customersRevenue);
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<String> updateCustomerInfo(@RequestBody Customer request) {
        boolean updateCustomerResponse = customerService.updateCustomer(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    };

    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<String> deleteCustomer(@RequestBody Customer request) {
        boolean deleteCustomerRes = customerService.deleteCustomer(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    };
}
