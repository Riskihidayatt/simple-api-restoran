package com.enigma.restaurant.specification;

import com.enigma.restaurant.entity.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomerSpecification {
    public static Specification<Customer> getSpecification(String name) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            // Tambahkan filter lain jika perlu
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
