package com.project.restaurant.repository;

import com.project.restaurant.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {

}
