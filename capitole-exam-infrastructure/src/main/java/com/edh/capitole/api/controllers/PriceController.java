package com.edh.capitole.api.controllers;

import com.edh.capitole.api.dtos.PriceRateDto;
import com.edh.capitole.business.usecases.FindPriceRateAtDayUseCase;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("prices")
public class PriceController {

    private final FindPriceRateAtDayUseCase findPriceRateAtDayUseCase;

    public PriceController(FindPriceRateAtDayUseCase findPriceRateAtDayUseCase) {
        this.findPriceRateAtDayUseCase = findPriceRateAtDayUseCase;
    }

    @GetMapping
    public Mono<PriceRateDto> getPrice(@RequestParam Long brandId,
                                       @RequestParam Long productId,
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       @RequestParam LocalDateTime date) {
        return findPriceRateAtDayUseCase.execute(productId, brandId, date)
                .map(it -> new PriceRateDto(it.getProductId(), it.getBrandId(), it.getRateId(), it.getStartDate(), it.getEndDate(), it.getPrice()));
    }

}
