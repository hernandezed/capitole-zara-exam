package com.edh.capitole.business.usecases.impl;

import com.edh.capitole.business.domain.PriceRateBo;
import com.edh.capitole.business.domain.RateBo;
import com.edh.capitole.business.exception.NoSuchPriceException;
import com.edh.capitole.business.ports.PricePort;
import com.edh.capitole.business.ports.RatePort;
import com.edh.capitole.business.usecases.FindPriceRateAtDayUseCase;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

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
        return pricePort.findByBrandIdAndProductId(brandId, productId)
                .zipWhen(priceRateBo -> ratePort.findAllByPriceIdAndDate(priceRateBo.getPriceId(), date))
                .map(this::getPriceRateBo)
                .switchIfEmpty(Mono.error(new NoSuchPriceException()));
    }

    private PriceRateBo getPriceRateBo(Tuple2<PriceRateBo, RateBo> priceAndRate) {
        priceAndRate.getT1().applyRate(priceAndRate.getT2());
        return priceAndRate.getT1();
    }
}
