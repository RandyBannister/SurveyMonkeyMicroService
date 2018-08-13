package de.marketlogicsoftware.task.questionservice;

import de.marketlogicsoftware.task.questionservice.enity.AnswerEntity;
import de.marketlogicsoftware.task.questionservice.enity.QuestionEntity;
import de.marketlogicsoftware.task.questionservice.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.data.rest.configuration.SpringDataRestConfiguration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;


@Import(SpringDataRestConfiguration.class)
@EnableSwagger2
@EnableDiscoveryClient
@SpringBootApplication
public class QuestionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestionServiceApplication.class, args);
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
	 * @param questionRepository
	 * @return
	 */
	@Bean
	CommandLineRunner commandLineRunner(QuestionRepository questionRepository) {
		return strings -> {
			saveQuestion(new QuestionEntity("question1"), 3, questionRepository);
			saveQuestion(new QuestionEntity("question2"), 4, questionRepository);
			saveQuestion(new QuestionEntity("question3"), 8, questionRepository);
		};
	}

	/**
	 * saving question adding answers(answer count is answerRange). For example, if answerRange is 3, questions
	 * will be added with three random answers
	 *
	 * @param question
	 * @param answerRange
	 * @param questionRepository
	 */
	private void saveQuestion(QuestionEntity question, int answerRange, QuestionRepository questionRepository) {
		for (int i = 1; i < answerRange; i++) {
			AnswerEntity answer = new AnswerEntity("answer_" + String.valueOf(i));
			question.getAnswers().add(answer);
			answer.setQuestion(question);
		}
		questionRepository.save(question);
	}
}
