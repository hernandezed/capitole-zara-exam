package com.edh.capitole.ports;

import com.edh.capitole.business.domain.PriceRateBo;
import com.edh.capitole.business.ports.PricePort;
import com.edh.capitole.data_access.entities.Price;
import com.edh.capitole.data_access.repositories.PriceRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class PricePortImpl implements PricePort {

    private final PriceRepository priceRepository;

    public PricePortImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Mono<PriceRateBo> findByBrandIdAndProductIdAndDate(Long brandId, Long productId, LocalDateTime date) {
        return priceRepository.findApplicable(brandId, productId, date)
                .map(this::getPricePriceRateBoFunction);
    }

    private PriceRateBo getPricePriceRateBoFunction(Price price) {
        return new PriceRateBo(price.productId(), price.brandId(), price.id(), price.startDate(), price.endDate(), price.price());
    }
}
