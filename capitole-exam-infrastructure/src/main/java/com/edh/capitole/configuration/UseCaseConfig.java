package com.edh.capitole.configuration;

import com.edh.capitole.business.ports.PricePort;
import com.edh.capitole.business.usecases.FindPriceRateAtDayUseCase;
import com.edh.capitole.business.usecases.impl.FindPriceRateAtDayUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.math.BigInteger;

@Configuration
public class UseCaseConfig {
    @Bean
    public FindPriceRateAtDayUseCase findPriceRateAtDayUseCase(PricePort pricePort) {
        return new FindPriceRateAtDayUseCaseImpl(pricePort);
    }

}
