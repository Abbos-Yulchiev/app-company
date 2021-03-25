package uz.pdp.apicompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apicompany.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByCorpName(String corpName);
}
