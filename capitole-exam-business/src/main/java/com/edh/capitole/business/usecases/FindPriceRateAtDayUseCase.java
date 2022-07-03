package com.edh.capitole.business.usecases;

import com.edh.capitole.business.domain.PriceRateBo;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDateTime;

public interface FindPriceRateAtDayUseCase {
    Mono<PriceRateBo> execute(Long productId, Long brandId, LocalDateTime date);
}
