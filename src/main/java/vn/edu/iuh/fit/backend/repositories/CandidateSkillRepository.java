package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.backend.models.entities.CandidateSkill;
import vn.edu.iuh.fit.backend.models.entities.CandidateSkillId;

import java.util.List;

@Repository
public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
    @Query("SELECT cs FROM CandidateSkill cs JOIN FETCH cs.skill WHERE cs.candidate.id = :canId")
    List<CandidateSkill> findByCandidateIdWithSkill(Long canId);
}
