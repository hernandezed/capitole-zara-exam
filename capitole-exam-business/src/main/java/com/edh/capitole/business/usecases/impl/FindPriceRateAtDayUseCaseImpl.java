package com.edh.capitole.business.usecases.impl;

import com.edh.capitole.business.domain.PriceRateBo;
import com.edh.capitole.business.exception.NoSuchPriceException;
import com.edh.capitole.business.ports.PricePort;
import com.edh.capitole.business.usecases.FindPriceRateAtDayUseCase;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class FindPriceRateAtDayUseCaseImpl implements FindPriceRateAtDayUseCase {

    public final PricePort pricePort;

    public FindPriceRateAtDayUseCaseImpl(PricePort pricePort) {
        this.pricePort = pricePort;
    }

    @Override
    public Mono<PriceRateBo> execute(BigInteger productId, BigInteger brandId, LocalDateTime date) {
        return pricePort.findByBrandIdAndProductIdAndDate(brandId, productId, date)
                .switchIfEmpty(Mono.error(new NoSuchPriceException()));
    }
}
