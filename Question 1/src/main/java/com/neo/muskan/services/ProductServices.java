package com.neo.muskan.services;

import com.neo.muskan.entities.Product;
import com.neo.muskan.dao.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServices {

    @Autowired
    private ProductRepo productRepository;

    public List<Product> getTopProducts(String category, int top, double minPrice, double maxPrice, int page, String sortBy, String sortOrder) {
        Pageable pageable = PageRequest.of(page - 1, top > 0 ? top : Integer.MAX_VALUE,
                sortOrder != null && sortOrder.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());

        if (category != null && !category.isEmpty()) {
            return productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice, pageable);
        } else {
            return productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        }
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        if (product.getId() != null && productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Product with ID " + product.getId() + " already exists.");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setExternalId(productDetails.getExternalId());
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            product.setDescription(productDetails.getDescription());
            product.setCategory(productDetails.getCategory());
            return productRepository.save(product);
        } else {
            throw new IllegalArgumentException("Product not found with ID " + id);
        }
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with ID " + id);
        }
        productRepository.deleteById(id);
    }
}
