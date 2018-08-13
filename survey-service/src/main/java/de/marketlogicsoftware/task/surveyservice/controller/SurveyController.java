package de.marketlogicsoftware.task.surveyservice.controller;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import de.marketlogicsoftware.task.surveyservice.controller.handler.NotFoundException;
import de.marketlogicsoftware.task.surveyservice.controller.handler.BadRequestException;
import de.marketlogicsoftware.task.surveyservice.controller.handler.RequestException;
import de.marketlogicsoftware.task.surveyservice.dto.SurveyResultDTO;
import de.marketlogicsoftware.task.surveyservice.persistence.entity.SurveyEntity;
import de.marketlogicsoftware.task.surveyservice.persistence.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * has endpoints for posting survey and loading statistics for a particular question
 */
@RequestMapping("survey")
@RestController
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EurekaClient eurekaClient;

    @Value("${service.question.serviceId}")
    private String questionServiceId;

    /**
     * this method is called when posting a survey is failed due unhandled exception, such, as if question-service
     * service is not available
     *
     * @param survey
     * @return
     */
    public ResponseEntity createFallback(@RequestBody SurveyEntity survey) {
        return new ResponseEntity("question service is temporary unavailable to validate question and answer combination", HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * validates request and saves in case it is a valid request, otherwise throws corresponding exception with error code
     *
     * @param survey - to create
     * @return - no content (nothing is required to be returned)
     */
    @HystrixCommand(fallbackMethod = "createFallback", ignoreExceptions = RequestException.class )
    @PostMapping
    public ResponseEntity create(@RequestBody SurveyEntity survey) {
        validateRequest(survey);
        surveyService.save(survey);
        return ResponseEntity.noContent().build();
    }

    /**
     * load statistics for a question id, if question id don't have statistics throws {@link BadRequestException} with
     * a proper error code and message
     *
     * @param questionId - question for which statistics should be returned
     * @return - statistics
     */
    @GetMapping("/question/{questionId}/statistics")
    public ResponseEntity<SurveyResultDTO> getQuestionResult(@PathVariable Long questionId) {
        SurveyResultDTO surveyResult = surveyService.getStatistics(questionId);
        if (surveyResult.getTotalNumber() == 0) {
            throw new BadRequestException("No survey has been done for this question");
        }
        return ResponseEntity.ok(surveyResult);
    }

    /**
     * validates whether the survey is valid or not. If no throws an exception with details
     *
     * @param survey - to validate
     */
    private void validateRequest(SurveyEntity survey) {
        if (survey.getQuestionId() == null) {
            throw new BadRequestException("Question id is a must");
        }
        if (survey.getAnswerId() == null) {
            throw new BadRequestException("Answer id is a must");
        }

        StringBuilder validationUrl = new StringBuilder("http://");

        Application application = eurekaClient.getApplication(questionServiceId);
        if (application != null) {
            InstanceInfo instanceInfo = application.getInstances().get(0);
            validationUrl.append(instanceInfo.getIPAddr()).append(":")
                    .append(instanceInfo.getPort()).append("/answers/search/exists")
                    .append("?answerId=").append(survey.getAnswerId()).append("&questionId=").append(survey.getQuestionId());
        }

        boolean exists = restTemplate.getForObject(validationUrl.toString(), Boolean.class);

        if (!exists) {
            throw new NotFoundException("The question and answer combination is not found");
        }
    }

}
