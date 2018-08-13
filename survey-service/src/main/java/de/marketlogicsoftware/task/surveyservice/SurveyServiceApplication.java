package de.marketlogicsoftware.task.surveyservice;

import de.marketlogicsoftware.task.surveyservice.persistence.entity.SurveyEntity;
import de.marketlogicsoftware.task.surveyservice.persistence.repository.SurveyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableHystrix
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
public class SurveyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SurveyServiceApplication.class, args);
	}

	/**
	 * building restTemplate object and publishing
	 *
	 * @param builder
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	/**
	 * swagger docket config is returned
	 *
	 * @return
	 */
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * creating some test data
	 *
	 * @param surveyRepository
	 * @return
	 */
	@Bean
	CommandLineRunner commandLineRunner(SurveyRepository surveyRepository) {
		return args -> {
			surveyRepository.save(new SurveyEntity(1L, 1L));
			surveyRepository.save(new SurveyEntity(1L, 1L));
			surveyRepository.save(new SurveyEntity(1L, 2L));
			surveyRepository.save(new SurveyEntity(2L, 4L));
		};
	}
}
