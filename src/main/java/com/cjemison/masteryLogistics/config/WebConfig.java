package com.cjemison.masteryLogistics.config;

import com.cjemison.masteryLogistics.domain.ShipmentDO;
import com.cjemison.masteryLogistics.domain.TruckDO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@ComponentScan(basePackages = {"com.cjemison.masteryLogistics"})
@EnableSwagger2WebFlux
public class WebConfig {

  @Autowired
  private ResourceLoader resourceLoader;

  @Autowired
  private ObjectMapper objectMapper;

  @Bean("threadPoolTaskExecutor")
  public Executor executor() {
    final ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setMaxPoolSize(100);
    threadPoolTaskExecutor.setCorePoolSize(20);
    threadPoolTaskExecutor.setQueueCapacity(25000);
    return threadPoolTaskExecutor;
  }

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.cjemison.masteryLogistics.controller.impl"))
        .paths(PathSelectors.any())
        .build();
  }

  @Bean("trucks")
  public List<TruckDO> trucks() throws IOException {
    final var resource = resourceLoader.getResource("classpath:trucks.json");
    Objects.requireNonNull(resource);

    final var inputStream = resource.getInputStream();
    Objects.requireNonNull(inputStream);

    final List<TruckDO> list = objectMapper
        .readValue(inputStream, new TypeReference<List<TruckDO>>() {
        });

    return list;
  }

  @Bean("shipments")
  public List<ShipmentDO> shipments() throws IOException {
    final var resource = resourceLoader.getResource("classpath:shipments.json");
    Objects.requireNonNull(resource);

    final var inputStream = resource.getInputStream();
    Objects.requireNonNull(inputStream);

    final List<ShipmentDO> list = objectMapper
        .readValue(inputStream, new TypeReference<List<ShipmentDO>>() {
        });

    return list;
  }
}
