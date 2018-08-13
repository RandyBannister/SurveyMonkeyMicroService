package de.marketlogicsoftware.task.questionservice.repository;

import de.marketlogicsoftware.task.questionservice.enity.QuestionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "questions", path = "questions", itemResourceDescription = @Description("Crud rest endpoints for Questions"))
public interface QuestionRepository extends CrudRepository<QuestionEntity, Long> {
}
