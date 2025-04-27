package com.enigma.restaurant.specification;
import com.enigma.restaurant.entity.Transaction;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils; // Helper StringUtils

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {

    public static Specification<Transaction> getSpecification(
            String customerId,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by Customer ID
            if (StringUtils.hasText(customerId)) { // Cek jika customerId tidak null atau kosong
                // Perlu join ke customer untuk filter by ID
                // Join<Transaction, Customer> customerJoin = root.join("customer");
                predicates.add(criteriaBuilder.equal(root.get("customer").get("id"), customerId));
            }

            // Filter by Date Range
            if (startDate != null && endDate != null) {
                // Set ke awal hari startDate dan akhir hari endDate
                LocalDateTime startOfDay = startDate.with(LocalTime.MIN);
                LocalDateTime endOfDay = endDate.with(LocalTime.MAX);
                predicates.add(criteriaBuilder.between(root.get("transactionDate"), startOfDay, endOfDay));
            } else if (startDate != null) {
                LocalDateTime startOfDay = startDate.with(LocalTime.MIN);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDate"), startOfDay));
            } else if (endDate != null) {
                LocalDateTime endOfDay = endDate.with(LocalTime.MAX);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("transactionDate"), endOfDay));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
