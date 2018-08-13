package de.marketlogicsoftware.task.questionservice.enity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * entity class for answers
 */
@Entity
public class AnswerEntity implements Serializable {

    private static final long serialVersionUID = -2492979600204130447L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String answer;

    @ManyToOne(cascade = CascadeType.ALL)
    private QuestionEntity question;

    /**
     * for JPA
     */
    public AnswerEntity() {
    }

    /**
     * assigns given parameter to corresponding property
     *
     * @param answer
     */
    public AnswerEntity(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public QuestionEntity getQuestion() {
        return question;
    }

    public void setQuestion(QuestionEntity question) {
        this.question = question;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
