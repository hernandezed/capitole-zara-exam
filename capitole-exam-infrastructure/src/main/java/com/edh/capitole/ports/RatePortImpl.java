package com.edh.capitole.ports;

import com.edh.capitole.business.domain.RateBo;
import com.edh.capitole.business.ports.RatePort;
import com.edh.capitole.data_access.repositories.RateRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class RatePortImpl implements RatePort {

    private final RateRepository rateRepository;

    public RatePortImpl(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public Mono<RateBo> findAllByPriceIdAndDate(Long priceId, LocalDateTime date) {
        return rateRepository.findApplicable(priceId, date)
                .map(rate -> new RateBo(rate.rateValue(), rate.startDate(), rate.endDate()));
    }
}
