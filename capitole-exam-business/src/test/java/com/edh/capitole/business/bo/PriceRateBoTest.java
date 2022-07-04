package com.edh.capitole.business.bo;

import com.edh.capitole.business.domain.PriceRateBo;
import com.edh.capitole.business.domain.RateBo;
import com.edh.capitole.business.exception.NotApplicableRateException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PriceRateBoTest {

    @Test
    void getFinalPrice_whenApplyRate_thenReturnPriceAppliedRate() {
        RateBo rateBo = new RateBo(new BigDecimal("1.5"), LocalDateTime.now(), LocalDateTime.now());
        PriceRateBo priceRateBo = new PriceRateBo(1L, 1L, BigDecimal.TEN);
        priceRateBo.applyRate(rateBo);

        assertThat(priceRateBo.getFinalPrice()).isEqualTo(new BigDecimal("15.0"));
    }

    @Test
    void getFinalPrice_whenNotApplyRate_thenThrowException() {
        PriceRateBo priceRateBo = new PriceRateBo(1L, 1L, BigDecimal.TEN);
        assertThatThrownBy(priceRateBo::getFinalPrice).isInstanceOf(NotApplicableRateException.class);
    }
}
