package vn.edu.iuh.fit.backend.services;

import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.models.entities.Company;

import java.util.List;
import java.util.Optional;


public interface CompanyService {
    List<Company> getAllCompanies();
    Optional<Company> getCompanyById(Long id);
    Company createCompany(Company company);
    Company updateCompany(Long id, Company companyDetails);
    void deleteCompany(Long id);

    Optional<Company> findByEmail(String email);

    boolean existsByEmail(String email);
}
