package vn.edu.iuh.fit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.backend.models.entities.JobSkill;
import vn.edu.iuh.fit.backend.models.entities.JobSkillId;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
}
