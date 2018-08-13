package de.marketlogicsoftware.task.surveyservice.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * holds data for survey statistics
 */
public class SurveyResultDTO {

    private Long totalNumber;

    private List<AnswerResultDTO> answerResults;

    public SurveyResultDTO() {
        answerResults = new ArrayList<>();
    }

    public Long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Long totalNumber) {
        this.totalNumber = totalNumber;
    }

    public List<AnswerResultDTO> getAnswerResults() {
        return answerResults;
    }

    public void setAnswerResults(List<AnswerResultDTO> answerResults) {
        this.answerResults = answerResults;
    }
}
