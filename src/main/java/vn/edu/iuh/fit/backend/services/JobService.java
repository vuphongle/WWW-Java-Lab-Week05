// JobService.java
package vn.edu.iuh.fit.backend.services;

import vn.edu.iuh.fit.backend.models.entities.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {
    List<Job> getAllJobs();
    Optional<Job> getJobById(Long id);
    Job createJob(Job job);
    Job updateJob(Job job);
    void deleteJob(Long id);

    void addSkillsToJob(Long jobId, List<Long> skillIds, List<Byte> skillLevels);
}