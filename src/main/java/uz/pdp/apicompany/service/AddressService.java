package uz.pdp.apicompany.service;

import org.springframework.stereotype.Service;
import uz.pdp.apicompany.entity.Address;
import uz.pdp.apicompany.payload.AddressDTO;
import uz.pdp.apicompany.payload.Result;
import uz.pdp.apicompany.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<Address> getAddresses() {

        List<Address> addressList = addressRepository.findAll();
        return addressList;
    }

    public Address getAddress(Integer addressId) {

        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        return optionalAddress.orElse(null);
    }

    public Result addAddress(AddressDTO addressDTO) {

        boolean existsAllByStreet = addressRepository.existsAllByStreet(addressDTO.getStreet());
        if (existsAllByStreet)
            return new Result("The Street name already used", false);

        Address address = new Address();
        address.setHomeNumber(addressDTO.getHomeNumber());
        address.setStreet(addressDTO.getStreet());
        addressRepository.save(address);
        return new Result("New Address successfully added", true);
    }

    public Result editAddress(Integer addressId, AddressDTO addressDTO) {

        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (!optionalAddress.isPresent())
            return new Result("Invalid address id used", false);

        boolean existsByStreetAndIdNot = addressRepository.existsByStreetAndIdNot(addressDTO.getStreet(), addressId);
        if (existsByStreetAndIdNot)
            return new Result("The street name already exist", false);

        Address address = optionalAddress.get();
        address.setHomeNumber(addressDTO.getHomeNumber());
        address.setStreet(addressDTO.getStreet());
        addressRepository.save(address);
        return new Result("Address successfully edited", true);
    }

    public Result deleteAddress(Integer addressId) {

        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (!optionalAddress.isPresent())
            return new Result("Invalid address Id", false);
        addressRepository.deleteById(addressId);
        return new Result("Address deleted", true);
    }
}
