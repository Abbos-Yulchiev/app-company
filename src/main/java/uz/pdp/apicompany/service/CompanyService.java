package uz.pdp.apicompany.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.apicompany.entity.Address;
import uz.pdp.apicompany.entity.Company;
import uz.pdp.apicompany.payload.CompanyDTO;
import uz.pdp.apicompany.payload.Result;
import uz.pdp.apicompany.repository.AddressRepository;
import uz.pdp.apicompany.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    final CompanyRepository companyRepository;
    final AddressRepository addressRepository;

    public CompanyService(CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    public List<Company> getCompanies(Integer page) {

        Pageable pageable = PageRequest.of(page, 10);
        return companyRepository.findAll(pageable).getContent();
    }

    public Company getCompany(Integer companyId) {

        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        return optionalCompany.orElse(null);
    }

    public Result addCompany(Company company) {

        boolean existsByCorpName = companyRepository.existsByCorpName(company.getCorpName());
        if (existsByCorpName)
            return new Result("CorpName already exist chose another name", false);

        Address address = new Address();
        address.setHomeNumber(company.getAddress().getHomeNumber());
        address.setStreet(company.getAddress().getStreet());
        addressRepository.save(address);

        Company newCompany = new Company();
        newCompany.setAddress(address);
        newCompany.setCorpName(company.getCorpName());
        newCompany.setDirectorName(company.getDirectorName());
        companyRepository.save(company);
        return new Result("New Company Successfully saved.", true);
    }

    public Result editCompany(Integer companyId, CompanyDTO companyDTO) {

        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (!optionalCompany.isPresent())
            return new Result("Invalid Company Id", false);

        Optional<Address> optionalAddress = addressRepository.findById(companyDTO.getAddressId());
        if (!optionalAddress.isPresent())
            return new Result("Invalid address Id", false);

        Company company = optionalCompany.get();
        company.setDirectorName(companyDTO.getDirectorName());
        company.setCorpName(companyDTO.getCorpName());
        company.setAddress(optionalAddress.get());
        companyRepository.save(company);
        return new Result("Company successfully edited.", true);
    }

    public Result deleteCompany(Integer companyId) {

        Optional<Company> optionalCompany = companyRepository.findById(companyId);
        if (!optionalCompany.isPresent())
            return new Result("Invalid Company Id", false);

        companyRepository.deleteById(companyId);
        return new Result("Company deleted.", true);
    }
}
