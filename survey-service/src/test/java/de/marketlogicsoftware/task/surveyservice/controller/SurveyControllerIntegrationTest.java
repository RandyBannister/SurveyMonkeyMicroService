package de.marketlogicsoftware.task.surveyservice.controller;

import de.marketlogicsoftware.task.surveyservice.persistence.entity.SurveyEntity;
import de.marketlogicsoftware.task.surveyservice.persistence.repository.SurveyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SurveyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SurveyRepository surveyRepository;

    @Test
    public void testLoadEqualStatistics() throws Exception {
        long questionId = 50L;

        for (long answerId = 1; answerId < 5; answerId++) {
            // two answers for each question
            surveyRepository.save(new SurveyEntity(questionId, answerId));
            surveyRepository.save(new SurveyEntity(questionId, answerId));
        }

        ResultActions resultActions = mockMvc.perform(get("/survey/question/" + questionId + "/statistics")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("totalNumber", is(8)))
                .andExpect(jsonPath("answerResults", hasSize(4)));

        for (int arrayIndex = 0; arrayIndex < 4; arrayIndex++) {
            //checks each answer percentage
            double expectedPercentage = 25.0D;

            resultActions.andExpect(jsonPath("answerResults["+ arrayIndex +"].answerId", is(arrayIndex + 1)));
            resultActions.andExpect(jsonPath("answerResults["+ arrayIndex +"].percentage", is(expectedPercentage)));

        }
    }

    @Test
    public void testLoadComplexStatistics() throws Exception {
        long questionId = 100L;
        long answer1Id = 10;
        long answer2Id = 11;

        // 5 answer 1 is saved
        surveyRepository.save(new SurveyEntity(questionId, answer1Id));
        surveyRepository.save(new SurveyEntity(questionId, answer1Id));
        surveyRepository.save(new SurveyEntity(questionId, answer1Id));
        surveyRepository.save(new SurveyEntity(questionId, answer1Id));
        surveyRepository.save(new SurveyEntity(questionId, answer1Id));


        // 1 answer 2 is saved
        surveyRepository.save(new SurveyEntity(questionId, answer2Id));

        long totalNumber = 6L;
        double answer1Percentage = 5 * 100 / totalNumber;
        double answer2Percentage = 1 * 100 / totalNumber;

        mockMvc.perform(get("/survey/question/" + questionId + "/statistics")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("totalNumber", is(6)))
                .andExpect(jsonPath("answerResults", hasSize(2)))
                .andExpect(jsonPath("answerResults[0].answerId", is((int) answer2Id)))
                .andExpect(jsonPath("answerResults[0].percentage", is(answer2Percentage)))
                .andExpect(jsonPath("answerResults[1].answerId", is((int) answer1Id)))
                .andExpect(jsonPath("answerResults[1].percentage", is(answer1Percentage)));
    }

}
