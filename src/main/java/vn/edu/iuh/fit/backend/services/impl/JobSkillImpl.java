package vn.edu.iuh.fit.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.entities.JobSkill;
import vn.edu.iuh.fit.backend.models.entities.JobSkillId;
import vn.edu.iuh.fit.backend.repositories.JobSkillRepository;
import vn.edu.iuh.fit.backend.services.JobSkillService;

import java.util.List;

@Service
public class JobSkillImpl implements JobSkillService {

    @Autowired
    private JobSkillRepository jobSkillRepository;
    @Override
    public void addJobSkill(JobSkill jobSkill) {
        JobSkillId jobSkillId = new JobSkillId();
        jobSkillId.setJobId(jobSkill.getJob().getId());
        jobSkillId.setSkillId(jobSkill.getSkill().getId());
        jobSkill.setId(jobSkillId);
        jobSkillRepository.save(jobSkill);
    }

    @Override
    public List<JobSkill> getJobSkillsByJobId(Long jobId) {
        return jobSkillRepository.getJobSkillsByJobId(jobId);
    }
}
