package com.enigma.restaurant.repository;

import com.enigma.restaurant.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    Page<Transaction> findAll(Specification<Transaction> spec, Pageable pageable);
}
