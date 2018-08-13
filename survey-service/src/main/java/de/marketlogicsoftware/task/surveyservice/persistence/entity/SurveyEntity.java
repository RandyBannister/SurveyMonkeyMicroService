package de.marketlogicsoftware.task.surveyservice.persistence.entity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * entity class for survey
 */
@Entity
public class SurveyEntity implements Serializable {

    private static final long serialVersionUID = -2341386266675036041L;

    /**
     * for JPA
     */
    public SurveyEntity() {
    }

    /**
     * assigns given parameters to corresponding properties
     *
     * @param questionId
     * @param answerId
     */
    public SurveyEntity(Long questionId, Long answerId) {
        this.questionId = questionId;
        this.answerId = answerId;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long questionId;

    private Long answerId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }
}
