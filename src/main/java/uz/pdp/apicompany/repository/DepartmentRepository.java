package uz.pdp.apicompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apicompany.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
}
