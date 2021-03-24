package uz.pdp.apicompany.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apicompany.entity.Address;
import uz.pdp.apicompany.payload.AddressDTO;
import uz.pdp.apicompany.payload.Result;
import uz.pdp.apicompany.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/address")
public class AddressController {

    final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * GET all address from DB
     * @return Address List
     */
    @GetMapping
    public ResponseEntity<List<Address>> getAddresses(){

        List<Address> addressList = addressService.getAddresses();
        return ResponseEntity.ok(addressList);
    }

    /**
     * GET address from DB by Id
     *
     * @param addressId
     * @return Address
     */
    @GetMapping(value = "/{addressId}")
    public ResponseEntity<Address> getAddress(@PathVariable Integer addressId){

        Address address = addressService.getAddress(addressId);
        return ResponseEntity.ok(address);
    }

    /**
     * Add Address
     *
     * @return ApiResponse
     * receive type of AddressDTO json
     */
    @PostMapping
    public ResponseEntity<Result> addAddress(@Valid @RequestBody AddressDTO addressDTO){

        Result result = addressService.addAddress(addressDTO);
        if (result.getSuccess())
            return ResponseEntity.status(201).body(result);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    /**
     * Edit Address
     *
     * @param addressId
     * @return Result
     * Gives Id and AddressDTO given
     */

    @PutMapping(value = "/{addressId}")
    public ResponseEntity<Result> editAddress(@PathVariable Integer addressId, @Valid @RequestBody AddressDTO addressDTO){

        Result result = addressService.editAddress(addressId, addressDTO);
        return ResponseEntity.status(result.getSuccess()?HttpStatus.ACCEPTED:HttpStatus.CONFLICT).body(result);
    }

    /**
     * Delete Address By id
     *
     * @param addressId
     * @return Result
     */
    @DeleteMapping(value = "/{addressId}")
    public ResponseEntity<Result> deleteAddress(@PathVariable Integer addressId){

        Result result = addressService.deleteAddress(addressId);
        return ResponseEntity.status(result.getSuccess()?202:409).body(result);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
