package com.enigma.restaurant.repository;

import com.enigma.restaurant.entity.Transaction;
import com.enigma.restaurant.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {

}
