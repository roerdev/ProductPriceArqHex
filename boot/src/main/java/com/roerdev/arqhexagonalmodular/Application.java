package com.roerdev.arqhexagonalmodular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.roerdev.arqhexagonalmodular"})
@EnableJpaRepositories(basePackages={"com.roerdev.arqhexagonalmodular.brand.adapter.jpa",
                                    "com.roerdev.arqhexagonalmodular.productprice.adapter.jpa"})
@EntityScan(basePackages={"com.roerdev.arqhexagonalmodular.brand.adapter.entity",
                            "com.roerdev.arqhexagonalmodular.productprice.adapter.entity"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
