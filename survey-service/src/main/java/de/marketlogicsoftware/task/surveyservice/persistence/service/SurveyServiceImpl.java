package de.marketlogicsoftware.task.surveyservice.persistence.service;

import de.marketlogicsoftware.task.surveyservice.dto.AnswerResultDTO;
import de.marketlogicsoftware.task.surveyservice.dto.SurveyResultDTO;
import de.marketlogicsoftware.task.surveyservice.persistence.entity.SurveyEntity;
import de.marketlogicsoftware.task.surveyservice.persistence.repository.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * default implementation of SurveyService
 *
 * @see SurveyService
 */
@Service
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    private SurveyRepository surveyRepository;

    /**
     * @see SurveyService#save(SurveyEntity)
     */
    @Transactional
    @Override
    public SurveyEntity save(SurveyEntity survey) {
        return surveyRepository.save(survey);
    }

    /**
     * @see SurveyService#getStatistics(Long)
     */
    @Transactional(readOnly = true)
    @Override
    public SurveyResultDTO getStatistics(Long questionId) {
        SurveyResultDTO result = new SurveyResultDTO();

        long count = surveyRepository.countByQuestionId(questionId);
        result.setTotalNumber(count);

        if (count != 0) {
            List<Object[]> data = surveyRepository.loadStatistics(questionId);

            for (Object[] objects : data) {
                AnswerResultDTO answerResult = new AnswerResultDTO();
                answerResult.setAnswerId((Long) objects[0]);

                long answersCount = (long) objects[1];
                double percentage = answersCount * 100 / count;
                answerResult.setPercentage(percentage);

                result.getAnswerResults().add(answerResult);
            }
        }

        return result;
    }
}
