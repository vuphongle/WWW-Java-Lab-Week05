package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.iuh.fit.backend.models.entities.Candidate;
import vn.edu.iuh.fit.backend.models.entities.Address;
import vn.edu.iuh.fit.backend.models.dto.CandidateForm;
import vn.edu.iuh.fit.backend.services.CandidateService;
import vn.edu.iuh.fit.backend.services.AddressService;
import vn.edu.iuh.fit.backend.exceptions.ResourceNotFoundException;
import vn.edu.iuh.fit.backend.enums.CountryCode;

import java.util.List;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private AddressService addressService;

    // Hiển thị danh sách các ứng viên
    @GetMapping
    public String listCandidates(Model model) {
        List<Candidate> candidates = candidateService.getAllCandidates();
        model.addAttribute("candidates", candidates);
        return "candidates/candidates"; // Đảm bảo rằng template này tồn tại
    }

    // Hiển thị form thêm ứng viên mới
    @GetMapping("/add")
    public String showAddCandidateForm(Model model) {
        model.addAttribute("candidateForm", new CandidateForm());
        return "candidates/add-candidate"; // Đảm bảo rằng template này tồn tại
    }

    // Xử lý thêm ứng viên mới
    @PostMapping("/add")
    public String addCandidate(@ModelAttribute("candidateForm") CandidateForm candidateForm) {
        // Tạo Address mới
        Address address = new Address();
        address.setStreet(candidateForm.getStreet());
        address.setCity(candidateForm.getCity());
        address.setCountry(candidateForm.getCountry()); // Đã là CountryCode
        address.setNumber(candidateForm.getNumber());
        address.setZipcode(candidateForm.getZipcode());

        addressService.createAddress(address);

        // Tạo Candidate mới
        Candidate candidate = new Candidate();
        candidate.setEmail(candidateForm.getEmail());
        candidate.setFullName(candidateForm.getFullName());
        candidate.setPhone(candidateForm.getPhone());
        candidate.setDob(candidateForm.getDob());
        candidate.setAddress(address);

        candidateService.createCandidate(candidate);

        return "redirect:/candidates";
    }

    // Các phương thức khác (edit, update, delete) tương tự
}
