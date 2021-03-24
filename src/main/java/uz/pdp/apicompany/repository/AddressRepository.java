package uz.pdp.apicompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apicompany.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    boolean existsAllByStreet(String street);

    boolean existsByStreetAndIdNot(String street, Integer id);
}
