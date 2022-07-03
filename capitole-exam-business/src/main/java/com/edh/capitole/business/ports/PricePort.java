package com.edh.capitole.business.ports;

import com.edh.capitole.business.domain.PriceRateBo;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDateTime;

public interface PricePort {
    Mono<PriceRateBo> findByBrandIdAndProductIdAndDate(Long brandId, Long productId, LocalDateTime date);
}
