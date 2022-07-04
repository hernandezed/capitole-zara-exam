package com.edh.capitole.data_access.repositories;

import com.edh.capitole.data_access.entities.Price;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface PriceRepository extends ReactiveCrudRepository<Price, Long> {

    @Query("select p.* from PRICE p inner join PRODUCT pr on p.product_id=pr.id where pr.product_code=:productCode and brand_id=:brandId")
    Mono<Price> findByProductCodeAndBrandId(Long productCode, Long brandId);
}
