package de.marketlogicsoftware.task.surveyservice.dto;

/**
 * holds statistics for a particular answer
 */
public class AnswerResultDTO {

    private Long answerId;

    private Double percentage;

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
