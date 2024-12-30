package com.nontech.companyms.company;

import java.util.List;

public interface CompanyService {
    // Create a new Company
    Company createCompany(Company company);

    // Retrieve a Company by its ID
    Company getCompanyById(Long id);

    // Retrieve all Companies
    List<Company> getAllCompanies();

    // Update an existing Company
    boolean updateCompany(Company updatedCompany,Long id);

    // Delete a Company by its ID
    boolean deleteCompanyById(Long id);
}
