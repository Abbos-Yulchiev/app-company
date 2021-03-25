package uz.pdp.apicompany.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.apicompany.entity.Address;
import uz.pdp.apicompany.entity.Company;
import uz.pdp.apicompany.entity.Department;
import uz.pdp.apicompany.payload.CompanyDTO;
import uz.pdp.apicompany.payload.DepartmentDTO;
import uz.pdp.apicompany.payload.Result;
import uz.pdp.apicompany.repository.CompanyRepository;
import uz.pdp.apicompany.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    final DepartmentRepository departmentRepository;
    final CompanyRepository companyRepository;

    public DepartmentService(DepartmentRepository departmentRepository, CompanyRepository companyRepository) {
        this.departmentRepository = departmentRepository;
        this.companyRepository = companyRepository;
    }

    public List<Department> getCompanies(Integer page) {

        Pageable pageable = PageRequest.of(page, 10);
        return departmentRepository.findAll(pageable).getContent();
    }

    public Department getCompany(Integer departmentId) {

        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        return optionalDepartment.orElse(null);
    }

    public Result addDepartment(DepartmentDTO departmentDTO) {

        Optional<Company> optionalCompany = companyRepository.findById(departmentDTO.getCompanyId());
        if (!optionalCompany.isPresent())
            return new Result("Invalid Company Id", false);

        Department department = new Department();
        department.setCompany(optionalCompany.get());
        department.setName(departmentDTO.getName());
        departmentRepository.save(department);
        return new Result("New Department saved.", true);
    }

    public Result editDepartment(Integer departmentId, DepartmentDTO departmentDTO) {

        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (!optionalDepartment.isPresent())
            return new Result("Invalid Department Id", false);

        Optional<Company> optionalCompany = companyRepository.findById(departmentDTO.getCompanyId());
        if (!optionalCompany.isPresent())
            return new Result("Invalid Company id", false);

        Department department = optionalDepartment.get();
        department.setName(departmentDTO.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new Result("Department edited.", true);
    }

    public Result deleteDepartment(Integer departmentId) {

        Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
        if (!optionalDepartment.isPresent())
            return new Result("Invalid Company Id", false);

        companyRepository.deleteById(departmentId);
        return new Result("Department deleted.", true);
    }
}
