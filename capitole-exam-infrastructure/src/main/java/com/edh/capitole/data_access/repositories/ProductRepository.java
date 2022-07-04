package com.edh.capitole.data_access.repositories;

import com.edh.capitole.data_access.entities.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    Mono<Product> findByProductCodeAndBrandId(Long productCode, Long brandId);
}
