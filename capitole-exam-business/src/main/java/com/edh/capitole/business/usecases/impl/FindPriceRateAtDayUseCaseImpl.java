package com.edh.capitole.business.usecases.impl;

import com.edh.capitole.business.domain.PriceRateBo;
import com.edh.capitole.business.exception.NoSuchPriceException;
import com.edh.capitole.business.ports.PricePort;
import com.edh.capitole.business.ports.RatePort;
import com.edh.capitole.business.usecases.FindPriceRateAtDayUseCase;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class FindPriceRateAtDayUseCaseImpl implements FindPriceRateAtDayUseCase {

    public final PricePort pricePort;
    public final RatePort ratePort;

    public FindPriceRateAtDayUseCaseImpl(PricePort pricePort, RatePort ratePort) {
        this.pricePort = pricePort;
        this.ratePort = ratePort;
    }

    @Override
    public Mono<PriceRateBo> execute(Long productId, Long brandId, LocalDateTime date) {
        return ratePort.findAllByBrandIdAndProductIdAndDate(brandId, productId, date)
                .zipWith(pricePort.findByBrandIdAndProductId(brandId, productId))
                .map((t) -> {
                    t.getT2().applyRate(t.getT1());
                    return t.getT2();
                })
                .switchIfEmpty(Mono.error(new NoSuchPriceException()));
    }
}
