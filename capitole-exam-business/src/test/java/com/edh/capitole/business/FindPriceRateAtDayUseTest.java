package com.edh.capitole.business;

import com.edh.capitole.business.domain.PriceRateBo;
import com.edh.capitole.business.domain.RateBo;
import com.edh.capitole.business.exception.NoSuchPriceException;
import com.edh.capitole.business.ports.PricePort;
import com.edh.capitole.business.ports.RatePort;
import com.edh.capitole.business.usecases.FindPriceRateAtDayUseCase;
import com.edh.capitole.business.usecases.impl.FindPriceRateAtDayUseCaseImpl;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.*;

public class FindPriceRateAtDayUseTest {

    PricePort pricePort = mock(PricePort.class);
    RatePort ratePort = mock(RatePort.class);
    FindPriceRateAtDayUseCase useCase = new FindPriceRateAtDayUseCaseImpl(pricePort, ratePort);

    @Test
    void execute_whenExistsRateAndPrice_returnPriceRateCallingPort() {
        Long brandId = 1L;
        Long productId = 2L;
        LocalDateTime date = LocalDateTime.now();

        PriceRateBo mocked = new PriceRateBo(productId, brandId, new BigDecimal(20));
        PriceRateBo expected = new PriceRateBo(productId, brandId, new BigDecimal(20));
        RateBo rate = new RateBo(BigDecimal.ONE, LocalDateTime.now(), LocalDateTime.now());
        expected.applyRate(rate);

        when(ratePort.findAllByBrandIdAndProductIdAndDate(brandId, productId, date))
                .thenReturn(Mono.just(rate));
        when(pricePort.findByBrandIdAndProductId(brandId, productId))
                .thenReturn(Mono.just(mocked));

        StepVerifier.create(useCase.execute(productId, brandId, date))
                .expectNextMatches((e) -> e.getFinalPrice().equals(expected.getFinalPrice()))
                .expectComplete()
                .verify();

        verify(pricePort).findByBrandIdAndProductId(brandId, productId);
        verify(ratePort).findAllByBrandIdAndProductIdAndDate(brandId, productId, date);
    }

    @Test
    void execute_whenNotExistsPrice_throwExceptionCallingPort() {
        Long brandId = 2L;
        Long productId = 1L;
        LocalDateTime date = LocalDateTime.now();

        RateBo rate = new RateBo(BigDecimal.ONE, LocalDateTime.now(), LocalDateTime.now());
        when(ratePort.findAllByBrandIdAndProductIdAndDate(brandId, productId, date))
                .thenReturn(Mono.just(rate));
        when(pricePort.findByBrandIdAndProductId(brandId, productId))
                .thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(productId, brandId, date))
                .expectError(NoSuchPriceException.class)
                .verify();

        verify(pricePort).findByBrandIdAndProductId(brandId, productId);
        verify(ratePort).findAllByBrandIdAndProductIdAndDate(brandId, productId, date);
    }

    @Test
    void execute_whenNotExistsRate_throwExceptionCallingPort() {
        Long brandId = 2L;
        Long productId = 1L;
        LocalDateTime date = LocalDateTime.now();

        PriceRateBo mocked = new PriceRateBo(productId, brandId, new BigDecimal(20));
        when(ratePort.findAllByBrandIdAndProductIdAndDate(brandId, productId, date))
                .thenReturn(Mono.empty());
        when(pricePort.findByBrandIdAndProductId(brandId, productId))
                .thenReturn(Mono.just(mocked));

        StepVerifier.create(useCase.execute(productId, brandId, date))
                .expectError(NoSuchPriceException.class)
                .verify();

        verify(pricePort).findByBrandIdAndProductId(brandId, productId);
        verify(ratePort).findAllByBrandIdAndProductIdAndDate(brandId, productId, date);
    }

}
