package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.iuh.fit.backend.models.entities.Job;
import vn.edu.iuh.fit.backend.models.entities.JobSkill;
import vn.edu.iuh.fit.backend.services.CompanyService;
import vn.edu.iuh.fit.backend.services.JobService;
import vn.edu.iuh.fit.backend.services.JobSkillService;
import vn.edu.iuh.fit.backend.services.SkillService;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recruitments")
public class RecruitmentController {

    @Autowired
    private JobService jobService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private JobSkillService jobSkillService;

    @Autowired
    private SkillService skillService;
    @GetMapping("/new")
    public String showRecruitmentForm(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        for (Job job : jobs) {
            companyService.getCompanyById(job.getCompany().getId()).ifPresent(job::setCompany);

            HashSet<JobSkill> jobSkills = new HashSet<>(jobSkillService.getJobSkillsByJobId(job.getId()));
            for (JobSkill jobSkill : jobSkills) {
                skillService.findById(jobSkill.getSkill().getId()).ifPresent(jobSkill::setSkill);
            }

            job.setJobSkills(new HashSet<>(jobSkillService.getJobSkillsByJobId(job.getId())));

        }
        model.addAttribute("jobs", jobs);

        return "recruitments/new";
    }
}
