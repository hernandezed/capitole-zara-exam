package com.edh.capitole.data_access.repositories;

import com.edh.capitole.data_access.entities.Rate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface RateRepository extends ReactiveCrudRepository<Rate, Long> {
    @Query("select * from RATE where price_id=:priceId and start_date<=:date and end_date>=:date " +
            "order by priority desc fetch first 1 rows only")
    Mono<Rate> findApplicable(Long priceId, LocalDateTime date);
}
