package com.edh.capitole.configuration;

import com.edh.capitole.business.ports.PricePort;
import com.edh.capitole.business.usecases.FindPriceRateAtDayUseCase;
import com.edh.capitole.business.usecases.impl.FindPriceRateAtDayUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public FindPriceRateAtDayUseCase findPriceRateAtDayUseCase(PricePort pricePort) {
        return new FindPriceRateAtDayUseCaseImpl(pricePort);
    }

}
