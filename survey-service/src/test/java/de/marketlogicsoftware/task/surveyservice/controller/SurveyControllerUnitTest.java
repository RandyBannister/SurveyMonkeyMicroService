package de.marketlogicsoftware.task.surveyservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.marketlogicsoftware.task.surveyservice.persistence.entity.SurveyEntity;
import de.marketlogicsoftware.task.surveyservice.persistence.repository.SurveyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SurveyControllerUnitTest {

    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestTemplate restTemplate;

    @MockBean
    private SurveyRepository surveyRepository;

    @Before
    public void beforeTests() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
    }

    @Test
    public void testCreateSurveyWithEmptyQuestionId() throws Exception {
        SurveyEntity survey = new SurveyEntity(null, 0L);

        mockMvc.perform(post("/survey")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(survey)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateSurveyWithEmptyAnswerId() throws Exception {
        SurveyEntity survey = new SurveyEntity(0L, null);

        mockMvc.perform(post("/survey")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(survey)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateSurveyNonExist() throws Exception {
        SurveyEntity survey = new SurveyEntity(0L, 0L);

        when(restTemplate.getForObject(Mockito.any(String.class), eq(Boolean.class))).thenReturn(false);

        mockMvc.perform(post("/survey")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(survey)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateHystrixFallbackMethod() throws Exception {
        SurveyEntity survey = new SurveyEntity(0L, 0L);

        when(restTemplate.getForObject(Mockito.any(String.class), eq(Boolean.class))).thenThrow(new RuntimeException("random unchecked exception"));

        mockMvc.perform(post("/survey")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(survey)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void testCreateSurveySuccess() throws Exception {
        SurveyEntity survey = new SurveyEntity(0L, 0L);

        when(restTemplate.getForObject(Mockito.any(String.class), eq(Boolean.class))).thenReturn(true);

        mockMvc.perform(post("/survey")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(survey)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testLoadStatisticsNotExists() throws Exception {
        when(surveyRepository.countByQuestionId(Mockito.anyLong())).thenReturn(0L);

        mockMvc.perform(get("/survey/question/0/statistics")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // bad request, as there is no survey for this question has been done yet
    }

    @Test
    public void testLoadStatisticsSuccess() throws Exception {
        long count = 4;

        long answer1Id = 1;
        long answer1Count = 3;
        double answer1Percentage = answer1Count * 100 / count;

        long answer2Id = 2;
        long answer2Count = 1;
        double answer2Percentage = answer2Count * 100 / count;

        Object[] answer1Data = { answer1Id, answer1Count };
        Object[] answer2Data = { answer2Id, answer2Count };

        List<Object[]> data = Arrays.asList(answer1Data, answer2Data);

        when(surveyRepository.countByQuestionId(Mockito.anyLong())).thenReturn(count);
        when(surveyRepository.loadStatistics(Mockito.anyLong())).thenReturn(data);

        mockMvc.perform(get("/survey/question/0/statistics")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("totalNumber", is((int) count)))
                .andExpect(jsonPath("answerResults", hasSize(2)))
                .andExpect(jsonPath("answerResults[0].answerId", is((int) answer1Id)))
                .andExpect(jsonPath("answerResults[0].percentage", is(answer1Percentage)))
                .andExpect(jsonPath("answerResults[1].answerId", is((int) answer2Id)))
                .andExpect(jsonPath("answerResults[1].percentage", is(answer2Percentage)));
    }

}
