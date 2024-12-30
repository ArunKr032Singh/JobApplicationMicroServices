package com.nontech.companyms.company.impl;

import com.nontech.companyms.company.Company;
import com.nontech.companyms.company.CompanyRepository;
import com.nontech.companyms.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    //    private Long nextId=1l;
    @Override
    public Company createCompany(Company company) {
//        company.setId(nextId++);
        System.out.println(company + " company data");
        return companyRepository.save(company);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }


    @Override
    public boolean updateCompany(Company updatedCompany, Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);

        if (companyOptional.isPresent()) {
            Company companyToUpdate = companyOptional.get();

            companyToUpdate.setDescription(updatedCompany.getDescription());
            companyToUpdate.setName(updatedCompany.getName());
            companyRepository.save(companyToUpdate);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }
        return false;
    }


}
