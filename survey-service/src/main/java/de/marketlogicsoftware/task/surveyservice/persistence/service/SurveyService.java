package de.marketlogicsoftware.task.surveyservice.persistence.service;

import de.marketlogicsoftware.task.surveyservice.dto.SurveyResultDTO;
import de.marketlogicsoftware.task.surveyservice.persistence.entity.SurveyEntity;

/**
 * provides method for survey posting and loading statistics
 */
public interface SurveyService {

    /**
     * saving the survey
     *
     * @param survey - to save
     * @return - saved
     */
    SurveyEntity save(SurveyEntity survey);

    /**
     * loads survey data by question id
     *
     * @param questionId
     * @return
     */
    SurveyResultDTO getStatistics(Long questionId);

}
