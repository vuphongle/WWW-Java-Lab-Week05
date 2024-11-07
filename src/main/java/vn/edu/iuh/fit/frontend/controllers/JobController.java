package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.models.dto.JobForm;
import vn.edu.iuh.fit.backend.models.entities.Company;
import vn.edu.iuh.fit.backend.models.entities.Job;
import vn.edu.iuh.fit.backend.models.entities.Skill;
import vn.edu.iuh.fit.backend.services.CompanyService;
import vn.edu.iuh.fit.backend.services.JobService;
import vn.edu.iuh.fit.backend.services.SkillService;
import vn.edu.iuh.fit.backend.exceptions.ResourceNotFoundException;

import java.util.List;

@Controller
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private SkillService skillService;

    // Hiển thị form đăng tin tuyển dụng mới
    @GetMapping("/post-job")
    public String showPostJobForm(Model model) {
        model.addAttribute("jobForm", new JobForm());
        List<Company> companies = companyService.getAllCompanies();
        model.addAttribute("companies", companies);
        List<Skill> skills = skillService.getAllSkills();
        model.addAttribute("skills", skills);
        return "jobs/post-job"; // Đảm bảo rằng template này tồn tại
    }

    // Xử lý đăng tin tuyển dụng mới
    @PostMapping("/post-job")
    public String postJob(@ModelAttribute("jobForm") JobForm jobForm) {
        Job job = new Job();
        job.setJobName(jobForm.getJobName());
        job.setJobDesc(jobForm.getJobDesc());

        // Gắn công ty cho job
        Company company = companyService.getCompanyById(jobForm.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id: " + jobForm.getCompanyId()));
        job.setCompany(company);

        jobService.createJob(job);

        // Thêm các kỹ năng cho Job
        jobService.addSkillsToJob(job.getId(), jobForm.getSkillIds(), jobForm.getSkillLevels());

        return "redirect:/job/list"; // Chuyển hướng đến danh sách công việc
    }

    // Hiển thị danh sách công việc
    @GetMapping("/list")
    public String listJobs(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "jobs/list-jobs"; // Đảm bảo rằng template này tồn tại
    }

    // Các phương thức khác (edit, update, delete) có thể được thêm tương tự
}
