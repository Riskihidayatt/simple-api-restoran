package com.project.restaurant.repository;
import com.project.restaurant.entity.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, String> {

    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    Page<Customer> findAll(Specification<Customer> spec, Pageable pageable);
}
