package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.backend.models.entities.Candidate;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>{
    Optional<Candidate> findByEmail(String email);

    @Query(value = "SELECT DISTINCT c.* FROM candidate c " +
            "JOIN candidate_skill cs ON c.id = cs.can_id " +
            "JOIN job_skill js ON cs.skill_id = js.skill_id " +
            "WHERE js.job_id = :jobId AND cs.skill_level > js.skill_level",
            nativeQuery = true)
    List<Candidate> findSuitableCandidatesForJob(@Param("jobId") Long jobId);
}
