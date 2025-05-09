package com.example.karaoke.customer_service.Repository;

import com.example.karaoke.customer_service.Entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    boolean existsByPhoneNumber(String phoneNumber);
    Customer findByPhoneNumber(String phoneNumber);
    long deleteByPhoneNumber(String phoneNumber);
}
