package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.entity.Product;
import ru.geekbrains.entity.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    Методы не нужны в данном ДЗ
//    @Transactional
//    public void insert(Product product) {
//        productRepository.save(product);
//    }
//
//    @Transactional
//    public void update(Product product) {
//        productRepository.save(product);
//    }

    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Page<Product> findAllByPriceBetween(Optional<BigDecimal> min, Optional<BigDecimal> max, Pageable pageable) {
        if (min.isPresent() && max.isPresent()) {
            return productRepository.findAllByPriceBetween(min.get(), max.get(), pageable);
        }
        if (min.isPresent()) {
            return productRepository.findAllByPriceGreaterThanEqual(min.get(), pageable);
        }
        if (max.isPresent()) {
            return productRepository.findAllByPriceLessThanEqual(max.get(), pageable);
        }
        return productRepository.findAll(pageable);
    }
}
