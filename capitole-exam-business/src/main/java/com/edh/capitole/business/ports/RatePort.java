package com.edh.capitole.business.ports;

import com.edh.capitole.business.domain.RateBo;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface RatePort {

    Mono<RateBo> findAllByPriceIdAndDate(Long priceId, LocalDateTime date);
}