package com.edh.capitole.ports;

import com.edh.capitole.business.domain.PriceRateBo;
import com.edh.capitole.business.ports.PricePort;
import com.edh.capitole.data_access.repositories.PriceRepository;
import com.edh.capitole.data_access.repositories.ProductRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PricePortImpl implements PricePort {

    private final PriceRepository priceRepository;
    private final ProductRepository productRepository;

    public PricePortImpl(PriceRepository priceRepository, ProductRepository productRepository) {
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Mono<PriceRateBo> findByBrandIdAndProductId(Long brandId, Long productId) {
        return productRepository.findByProductCodeAndBrandId(productId, brandId)
                .zipWith(priceRepository.findByProductCodeAndBrandId(productId, brandId))
                .map(it -> new PriceRateBo(it.getT1().id(), it.getT1().productCode(), it.getT1().brandId(), it.getT2().amount()));

    }
}
