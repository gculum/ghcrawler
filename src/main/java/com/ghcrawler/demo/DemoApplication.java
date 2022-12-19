package com.ghcrawler.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.spi.DocumentationType.SWAGGER_2;

@EnableSwagger2
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	/**
	 * Creates RestTemplate bean used in GithubFetcher
	 * service
	 *
	 * @return RestTemplate bean
	 */
	@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate =  new RestTemplate();
		restTemplate.setErrorHandler(new MyApiRestTemplateErrorHandler());
		return restTemplate;
	}

	/**
	 * Creates WebClient bean used in GithubFetcher
	 * service
	 *
	 * @return WebClient bean
	 */
	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}


	/**
	 * Creates Docket bean used in Swagger
	 *
	 * @return Docket bean
	 */
	@Bean
	public Docket swagger() {
		return new Docket(SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

}
