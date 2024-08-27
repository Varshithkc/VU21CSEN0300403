package com.neo.muskan.dao;

import com.neo.muskan.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    
    List<Product> findByCategoryAndPriceBetween(String category, double minPrice, double maxPrice, Pageable pageable);
    
    List<Product> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);
}
