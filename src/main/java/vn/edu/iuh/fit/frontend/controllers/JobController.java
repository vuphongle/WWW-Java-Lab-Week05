package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.iuh.fit.backend.models.dto.JobForm;
import vn.edu.iuh.fit.backend.models.dto.JobSkillForm;
import vn.edu.iuh.fit.backend.models.entities.Company;
import vn.edu.iuh.fit.backend.models.entities.Job;
import vn.edu.iuh.fit.backend.models.entities.JobSkill;
import vn.edu.iuh.fit.backend.models.entities.Skill;
import vn.edu.iuh.fit.backend.services.JobService;
import vn.edu.iuh.fit.backend.services.JobSkillService;
import vn.edu.iuh.fit.backend.services.SkillService;

import java.util.*;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private JobService jobService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private JobSkillService jobSkillService;

    /**
     * Hiển thị form đăng tin tuyển dụng
     */

    @GetMapping("/new")
    public String showJobForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Company loggedInCompany = (Company) session.getAttribute("loggedInCompany");
        if (loggedInCompany == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập trước!");
            return "redirect:/companies/login";
        }

        JobForm jobForm = new JobForm();
        // Thêm một JobSkillForm mặc định để form có ít nhất một trường kỹ năng
        jobForm.getJobSkills().add(new JobSkillForm());

        model.addAttribute("jobForm", jobForm);

        List<Skill> skills = skillService.getAllSkills();
        model.addAttribute("skills", skills);
        return "jobs/new";
    }

    /**
     * Xử lý đăng tin tuyển dụng
     */
    @PostMapping("/new")
    public String submitJobForm(@ModelAttribute JobForm jobForm, HttpSession session, RedirectAttributes redirectAttributes) {
        Company loggedInCompany = (Company) session.getAttribute("loggedInCompany");
        if (loggedInCompany == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập trước!");
            return "redirect:/companies/login";
        }

        Job job = new Job();
        job.setJobName(jobForm.getJobName());
        job.setJobDesc(jobForm.getJobDesc());
        job.setCompany(loggedInCompany);

        Job job1 = jobService.createJob(job);

        // Xử lý danh sách kỹ năng
        List<JobSkillForm> jobSkillForms = jobForm.getJobSkills();

        for (JobSkillForm form : jobSkillForms) {
            if (form.getSkillId() != null && form.getSkillLevel() != null) {
                JobSkill jobSkill = new JobSkill();
                Skill skill = new Skill();

                skill.setId(form.getSkillId());
                jobSkill.setJob(job1);
                jobSkill.setSkill(skill);
                jobSkill.setSkillLevel(form.getSkillLevel());
                jobSkill.setMoreInfos(form.getMoreInfos());

                System.out.println("jobId: " + job.getId());
                System.out.println("skillId: " + form.getSkillId());
                System.out.println("skillLevel: " + form.getSkillLevel());
                System.out.println("moreInfos: " + form.getMoreInfos());

                jobSkillService.addJobSkill(jobSkill);
            }
        }



        redirectAttributes.addFlashAttribute("successMessage", "Đăng tin tuyển dụng thành công!");
        return "redirect:/jobs/new";
    }
}
