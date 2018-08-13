package de.marketlogicsoftware.task.surveyservice.persistence.repository;

import de.marketlogicsoftware.task.surveyservice.persistence.entity.SurveyEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SurveyRepository extends CrudRepository<SurveyEntity, Long> {

    @Query("SELECT s.answerId, count(s.answerId) FROM SurveyEntity s WHERE s.questionId = :questionId GROUP BY s.questionId, s.answerId")
    List<Object[]> loadStatistics(@Param("questionId") Long questionId);

    long countByQuestionId(Long questionId);
}
