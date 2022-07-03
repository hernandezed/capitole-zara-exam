package com.edh.capitole.data_access.repositories;

import com.edh.capitole.data_access.entities.Price;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PriceRepository extends ReactiveCrudRepository<Price, Long> {
    @Query("select * from price  where brand_id = :brandId and product_id = :productId " +
            "and start_date <= :date and end_date >= :date order by priority desc fetch " +
            "first 1 rows only")
    Mono<Price> findApplicable(Long brandId, Long productId, LocalDateTime date);
}
