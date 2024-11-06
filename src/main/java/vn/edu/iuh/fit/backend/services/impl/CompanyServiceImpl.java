package vn.edu.iuh.fit.backend.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.backend.exceptions.ResourceNotFoundException;
import vn.edu.iuh.fit.backend.models.entities.Company;
import vn.edu.iuh.fit.backend.repositories.CompanyRepository;
import vn.edu.iuh.fit.backend.services.CompanyService;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + id));

        company.setAbout(companyDetails.getAbout());
        company.setEmail(companyDetails.getEmail());
        company.setCompName(companyDetails.getCompName());
        company.setPhone(companyDetails.getPhone());
        company.setWebUrl(companyDetails.getWebUrl());
        company.setAddress(companyDetails.getAddress());

        return companyRepository.save(company);
    }

    @Override
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + id));
        companyRepository.delete(company);
    }
}
