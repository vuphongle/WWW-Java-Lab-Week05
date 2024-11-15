package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.models.entities.JobSkill;

import java.util.List;

public interface JobSkillService {
    void addJobSkill(JobSkill jobSkill);

    List<JobSkill> getJobSkillsByJobId(Long jobId);
}
