package com.myworkshop.ordersystem.bootstrap;

import com.myworkshop.ordersystem.domain.Product;
import com.myworkshop.ordersystem.domain.Stock;
import com.myworkshop.ordersystem.repository.ProductRepository;
import com.myworkshop.ordersystem.repository.StockRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SampleDataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    public SampleDataInitializer(ProductRepository productRepository,
                                 StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (productRepository.count() > 0) {
            return;
        }

        Product keyboard = new Product("기계식 키보드", new BigDecimal("89000.00"));
        Product mouse = new Product("무선 마우스", new BigDecimal("39000.00"));
        Product monitor = new Product("27인치 모니터", new BigDecimal("249000.00"));

        @SuppressWarnings("null")
        List<Product> products = productRepository.saveAll(List.of(keyboard, mouse, monitor));

        stockRepository.save(new Stock(products.get(0), 10));
        stockRepository.save(new Stock(products.get(1), 8));
        stockRepository.save(new Stock(products.get(2), 5));
    }
}
