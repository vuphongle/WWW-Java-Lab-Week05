package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.backend.models.entities.Candidate;
import vn.edu.iuh.fit.backend.models.entities.Job;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findByCompany_Id(Long companyId);

    @Query(
            value = "SELECT DISTINCT j.* " +
                    "FROM job j " +
                    "JOIN job_skill js ON js.job_id = j.job_id " +
                    "JOIN candidate_skill cs ON cs.skill_id = js.skill_id " +
                    "JOIN candidate c ON c.id = cs.can_id " +
                    "WHERE c.id = :candidateId " +
                    "AND cs.skill_level >= js.skill_level",
            nativeQuery = true
    )
    List<Job> findJobsByCandidateSkills(@Param("candidateId") Long candidateId);
}
