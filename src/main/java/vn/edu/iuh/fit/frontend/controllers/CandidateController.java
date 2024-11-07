package vn.edu.iuh.fit.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.iuh.fit.backend.models.entities.Candidate;
import vn.edu.iuh.fit.backend.models.entities.Address;
import vn.edu.iuh.fit.backend.models.dto.CandidateForm;
import vn.edu.iuh.fit.backend.services.CandidateService;
import vn.edu.iuh.fit.backend.services.AddressService;
import vn.edu.iuh.fit.backend.exceptions.ResourceNotFoundException;

import java.util.List;

@Controller
@RequestMapping("/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private AddressService addressService;

    // Hiển thị danh sách các ứng viên không phân trang
    @GetMapping("/list")
    public String listCandidates(Model model) {
        List<Candidate> candidates = candidateService.getAllCandidates();
        model.addAttribute("candidates", candidates);
        return "candidates/candidates";
    }

    // Hiển thị danh sách các ứng viên phân trang
    @GetMapping("/list-paged")
    public String listCandidatesPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Candidate> candidatePage = candidateService.getAllCandidates(pageable);
        model.addAttribute("candidatePage", candidatePage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "candidates/candidates-paging";
    }
    // Hiển thị form thêm ứng viên mới
    @GetMapping("/add")
    public String showAddCandidateForm(Model model) {
        model.addAttribute("candidateForm", new CandidateForm());
        return "candidates/add-candidate"; // Đảm bảo rằng template này tồn tại
    }

    @PostMapping("/add")
    public String addCandidate(@ModelAttribute("candidateForm") CandidateForm candidateForm, RedirectAttributes redirectAttributes) {
        try{
            // Tạo Address mới
            Address address = new Address();
            address.setStreet(candidateForm.getStreet());
            address.setCity(candidateForm.getCity());
            address.setCountry(candidateForm.getCountry());
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

            redirectAttributes.addFlashAttribute("successMessage", "Thêm ứng viên thành công!");
            return "redirect:/candidates/list";
        } catch (Exception e){
            e.printStackTrace();
        }

        redirectAttributes.addFlashAttribute("errorMessage", "Thêm ứng viên thất bại!");
        return "redirect:/candidates/add";
    }

    // Hiển thị form sửa ứng viên
    @GetMapping("/edit/{id}")
    public String showEditCandidateForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Candidate candidate = candidateService.getCandidateById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ứng viên không tồn tại với ID: " + id));

        // Chuyển đổi đối tượng Candidate thành CandidateForm để dễ dàng xử lý trong form
        CandidateForm candidateForm = new CandidateForm();
        candidateForm.setId(candidate.getId());
        candidateForm.setFullName(candidate.getFullName());
        candidateForm.setEmail(candidate.getEmail());
        candidateForm.setPhone(candidate.getPhone());
        candidateForm.setDob(candidate.getDob());
        candidateForm.setStreet(candidate.getAddress().getStreet());
        candidateForm.setCity(candidate.getAddress().getCity());
        candidateForm.setCountry(candidate.getAddress().getCountry());
        candidateForm.setNumber(candidate.getAddress().getNumber());
        candidateForm.setZipcode(candidate.getAddress().getZipcode());

        model.addAttribute("candidateForm", candidateForm);
        return "candidates/update-candidates";
    }

    // Xử lý cập nhật ứng viên
    @PostMapping("/edit/{id}")
    public String updateCandidate(@PathVariable("id") Long id,
                                  @ModelAttribute("candidateForm") CandidateForm candidateForm,
                                  RedirectAttributes redirectAttributes) {
        try {
            Candidate existingCandidate = candidateService.getCandidateById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Ứng viên không tồn tại với ID: " + id));

            // Cập nhật thông tin ứng viên
            existingCandidate.setFullName(candidateForm.getFullName());
            existingCandidate.setEmail(candidateForm.getEmail());
            existingCandidate.setPhone(candidateForm.getPhone());
            existingCandidate.setDob(candidateForm.getDob());

            // Cập nhật địa chỉ
            Address existingAddress = existingCandidate.getAddress();
            existingAddress.setStreet(candidateForm.getStreet());
            existingAddress.setCity(candidateForm.getCity());
            existingAddress.setCountry(candidateForm.getCountry());
            existingAddress.setNumber(candidateForm.getNumber());
            existingAddress.setZipcode(candidateForm.getZipcode());

            // Lưu địa chỉ và ứng viên
            addressService.updateAddress(existingAddress);
            candidateService.updateCandidate(id, existingCandidate);

            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật ứng viên thành công!");
            return "redirect:/candidates/list";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Cập nhật ứng viên thất bại!");
            return "redirect:/candidates/edit/" + id;
        }
    }

    // Xử lý xóa ứng viên
    @GetMapping("/delete/{id}")
    public String deleteCandidate(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            candidateService.deleteCandidate(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa ứng viên thành công!");
        } catch (ResourceNotFoundException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "Xóa ứng viên thất bại!");
        }
        return "redirect:/candidates/list";
    }
}
