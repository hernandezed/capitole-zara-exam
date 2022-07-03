package com.edh.capitole.business;

import com.edh.capitole.business.domain.PriceRateBo;
import com.edh.capitole.business.exception.NoSuchPriceException;
import com.edh.capitole.business.ports.PricePort;
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
    FindPriceRateAtDayUseCase useCase = new FindPriceRateAtDayUseCaseImpl(pricePort);

    @Test
    void execute_whenExistsRate_returnPriceRateCallingPort() {
        Long brandId = 1L;
        Long productId = 2L;
        LocalDateTime date = LocalDateTime.now();

        PriceRateBo expected = new PriceRateBo(productId, brandId, 1L, date.minus(2, ChronoUnit.DAYS), date.plus(2, ChronoUnit.DAYS), new BigDecimal(20));

        when(pricePort.findByBrandIdAndProductIdAndDate(brandId, productId, date))
                .thenReturn(Mono.just(expected));

        StepVerifier.create(useCase.execute(productId, brandId, date))
                .expectNext(expected)
                .expectComplete()
                .verify();

        verify(pricePort).findByBrandIdAndProductIdAndDate(brandId, productId, date);
    }

    @Test
    void execute_whenNotExistsRate_throwExceptionCallingPort() {
        Long brandId = 2L;
        Long productId = 1L;
        LocalDateTime date = LocalDateTime.now();

        when(pricePort.findByBrandIdAndProductIdAndDate(brandId, productId, date))
                .thenReturn(Mono.empty());

        StepVerifier.create(useCase.execute(productId, brandId, date))
                .expectError(NoSuchPriceException.class)
                .verify();

        verify(pricePort).findByBrandIdAndProductIdAndDate(brandId, productId, date);
    }
}
