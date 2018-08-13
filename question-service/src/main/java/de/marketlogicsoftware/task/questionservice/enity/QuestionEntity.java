package de.marketlogicsoftware.task.questionservice.enity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * entity class for questions
 */
@Entity
public class QuestionEntity implements Serializable {

    private static final long serialVersionUID = -1115149921693657140L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String question;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private Set<AnswerEntity> answers;

    /**
     * for JPA
     */
    public QuestionEntity() {
    }

    /**
     * assigns given parameter to corresponding property
     *
     * @param question
     */
    public QuestionEntity(String question) {
        this.question = question;
        this.answers = new HashSet<>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Set<AnswerEntity> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<AnswerEntity> answers) {
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
