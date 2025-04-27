package com.enigma.restaurant.repository;

import com.enigma.restaurant.dto.response.MenuResponse;
import com.enigma.restaurant.entity.Customer;
import com.enigma.restaurant.entity.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, String> {

    Page<Menu> findAll(Specification<Menu> spec, Pageable pageable);
}
