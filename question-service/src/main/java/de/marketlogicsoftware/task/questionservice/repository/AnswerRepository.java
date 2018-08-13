package de.marketlogicsoftware.task.questionservice.repository;

import de.marketlogicsoftware.task.questionservice.enity.AnswerEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "answers", path = "answers")
public interface AnswerRepository extends CrudRepository<AnswerEntity, Long> {

    @ApiOperation(value = "Check if question and answer exists with the provided ids")
    @RestResource(path = "exists", rel = "exists")
    boolean existsByIdAndQuestion_Id(@Param("answerId") Long answerId, @Param("questionId") Long questionId);

}
