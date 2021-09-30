package az.hrportal.hrportalapi.repository.position;

import az.hrportal.hrportalapi.domain.position.Position;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Integer> {
    @EntityGraph(attributePaths = {"languageKnowledge", "legislationStatements", "computerKnowledge"})
    @Query("select p from Position p where p.id = :id")
    Optional<Position> findByIdWithKnowledgeRelations(Integer id);

    @EntityGraph(attributePaths = {"skills", "functionalities", "institution",
            "department", "subDepartment", "vacancy", "educationSpeciality", "jobFamily", "salary"})
    @Query("select p from Position p where p.id = :id")
    Optional<Position> findByIdWithSkillsAndFunctionalities(Integer id);

    @Override
    @EntityGraph(attributePaths = {"skills", "functionalities", "institution",
            "languageKnowledge", "legislationStatements", "computerKnowledge",
            "department", "subDepartment", "vacancy", "educationSpeciality", "jobFamily", "salary"})
    @Cacheable("positions")
    List<Position> findAll();

    @Query("select p from Position p where p.status='APPROVED' ")
    @EntityGraph(attributePaths = {"skills", "functionalities", "institution",
            "languageKnowledge", "legislationStatements", "computerKnowledge",
            "department", "subDepartment", "vacancy", "educationSpeciality", "jobFamily", "salary"})
    @Cacheable("positions")
    List<Position> findAllByStatusIsApproved();
}
