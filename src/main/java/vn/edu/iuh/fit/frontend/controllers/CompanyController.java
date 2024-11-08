package vn.edu.iuh.fit.frontend.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.edu.iuh.fit.backend.exceptions.ResourceNotFoundException;
import vn.edu.iuh.fit.backend.models.dto.CompanyForm;
import vn.edu.iuh.fit.backend.models.entities.Address;
import vn.edu.iuh.fit.backend.models.entities.Company;
import vn.edu.iuh.fit.backend.services.AddressService;
import vn.edu.iuh.fit.backend.services.CompanyService;
import vn.edu.iuh.fit.backend.enums.CountryCode;

@Controller
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private final CompanyService companyService;
    @Autowired
    private AddressService addressService;
    private final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Hiển thị form đăng nhập công ty
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "companies/login";
    }

    /**
     * Xử lý đăng nhập công ty
     */
    @PostMapping("/login")
    public String handleLogin(@RequestParam("email") String email, Model model, RedirectAttributes redirectAttributes, HttpSession session) {
        try {
            Company company = companyService.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Email không tồn tại: " + email));
            Address address = addressService.getAddressById(company.getAddress().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Địa chỉ không tồn tại: " + company.getAddress().getId()));
            // Ghi log thông tin công ty
            logger.info("Company logged in: {}", company);
            company.setAddress(address);
            // Lưu thông tin công ty vào session
            session.setAttribute("loggedInCompany", company);

            // Chuyển hướng đến trang hồ sơ công ty
            return "redirect:/companies/profile";
        } catch (ResourceNotFoundException e) {
            logger.error("ResourceNotFoundException: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/companies/login";
        } catch (Exception e) {
            logger.error("Exception during login", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Đăng nhập thất bại!");
            return "redirect:/companies/login";
        }
    }

    /**
     * Hiển thị hồ sơ công ty
     */
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Company company = (Company) session.getAttribute("loggedInCompany");
        if (company == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập trước!");
            return "redirect:/companies/login";
        }

        model.addAttribute("company", company);
        return "companies/profile";
    }

    /**
     * Xử lý đăng xuất công ty
     */
    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("loggedInCompany");
        redirectAttributes.addFlashAttribute("successMessage", "Đăng xuất thành công!");
        return "redirect:/companies/login";
    }

    /**
     * Hiển thị form đăng ký công ty
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("companyForm", new CompanyForm());
        model.addAttribute("countryCodes", CountryCode.values());
        return "companies/register";
    }

    /**
     * Xử lý đăng ký công ty
     */
    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("companyForm") @Valid CompanyForm companyForm,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            if (companyService.findByEmail(companyForm.getEmail()).isPresent()) {
                redirectAttributes.addFlashAttribute("errorMessage", "Email đã được sử dụng!");
                return "redirect:/companies/register";
            }

            // Tạo Address
            Address address = new Address();
            address.setStreet(companyForm.getAddress().getStreet());
            address.setCity(companyForm.getAddress().getCity());
            address.setCountry(companyForm.getAddress().getCountry());
            address.setZipcode(companyForm.getAddress().getZipcode());
            address.setNumber(companyForm.getAddress().getNumber());

            addressService.createAddress(address);

            // Tạo Company
            Company company = new Company();
            company.setCompName(companyForm.getCompName());
            company.setEmail(companyForm.getEmail());
            company.setPhone(companyForm.getPhone());
            company.setWebUrl(companyForm.getWebUrl());
            company.setAbout(companyForm.getAbout());
            company.setAddress(address);

            companyService.createCompany(company);

            logger.info("Registered new company: {}", company);

            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công! Vui lòng đăng nhập.");
            return "redirect:/companies/login";
        } catch (Exception e) {
            logger.error("Exception during registration", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Đăng ký thất bại!");
            return "redirect:/companies/register";
        }
    }

    @GetMapping("/edit")
    public String showEditForm(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Company loggedInCompany = (Company) session.getAttribute("loggedInCompany");
        if (loggedInCompany == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập trước!");
            return "redirect:/companies/login";
        }

        // Tạo CompanyForm từ Company hiện tại
        CompanyForm companyForm = new CompanyForm();
        companyForm.setCompName(loggedInCompany.getCompName());
        companyForm.setEmail(loggedInCompany.getEmail());
        companyForm.setPhone(loggedInCompany.getPhone());
        companyForm.setWebUrl(loggedInCompany.getWebUrl());
        companyForm.setAbout(loggedInCompany.getAbout());

        CompanyForm.AddressForm addressForm = new CompanyForm.AddressForm();
        addressForm.setStreet(loggedInCompany.getAddress().getStreet());
        addressForm.setCity(loggedInCompany.getAddress().getCity());
        addressForm.setCountry(loggedInCompany.getAddress().getCountry());
        addressForm.setZipcode(loggedInCompany.getAddress().getZipcode());
        addressForm.setNumber(loggedInCompany.getAddress().getNumber());
        companyForm.setAddress(addressForm);

        model.addAttribute("companyForm", companyForm);
        model.addAttribute("countryCodes", CountryCode.values());

        return "companies/edit";
    }

    /**
     * Xử lý chỉnh sửa hồ sơ công ty
     */
    @PostMapping("/edit")
    public String handleEdit(@ModelAttribute("companyForm") @Valid CompanyForm companyForm,
                             HttpSession session,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        try {
            Company loggedInCompany = (Company) session.getAttribute("loggedInCompany");
            if (loggedInCompany == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Bạn cần đăng nhập trước!");
                return "redirect:/companies/login";
            }

            // Cập nhật thông tin công ty
            loggedInCompany.setCompName(companyForm.getCompName());
            loggedInCompany.setPhone(companyForm.getPhone());
            loggedInCompany.setWebUrl(companyForm.getWebUrl());
            loggedInCompany.setAbout(companyForm.getAbout());

            // Cập nhật địa chỉ
            Address address = loggedInCompany.getAddress();
            address.setStreet(companyForm.getAddress().getStreet());
            address.setCity(companyForm.getAddress().getCity());
            address.setCountry(companyForm.getAddress().getCountry());
            address.setZipcode(companyForm.getAddress().getZipcode());
            address.setNumber(companyForm.getAddress().getNumber());

            // Lưu thay đổi vào cơ sở dữ liệu
            companyService.updateCompany(loggedInCompany.getId(), loggedInCompany);

            // Cập nhật session
            session.setAttribute("loggedInCompany", loggedInCompany);

            redirectAttributes.addFlashAttribute("successMessage", "Chỉnh sửa hồ sơ thành công!");
            return "redirect:/companies/profile";
        } catch (Exception e) {
            logger.error("Exception during profile edit", e);
            redirectAttributes.addFlashAttribute("errorMessage", "Chỉnh sửa hồ sơ thất bại!");
            return "redirect:/companies/edit";
        }
    }
}
