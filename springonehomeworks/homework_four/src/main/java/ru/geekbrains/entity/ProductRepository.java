package ru.geekbrains.entity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByPriceGreaterThanEqual(BigDecimal min, Pageable pageable);

    Page<Product> findAllByPriceLessThanEqual(BigDecimal max, Pageable pageable);

    Page<Product> findAllByPriceBetween(BigDecimal min, BigDecimal max, Pageable pageable);

}
