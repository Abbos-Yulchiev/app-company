package uz.pdp.apicompany.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apicompany.entity.Company;
import uz.pdp.apicompany.payload.CompanyDTO;
import uz.pdp.apicompany.payload.Result;
import uz.pdp.apicompany.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/company")
public class CompanyController {

    final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * GEt all Companies
     *
     * @param page
     * @return CompanyPage
     */
    @GetMapping
    public ResponseEntity<?> getCompanies(@RequestParam Integer page) {

        List<Company> companyList = companyService.getCompanies(page);
        return ResponseEntity.ok(companyList);
    }

    @GetMapping(value = "/{companyId}")
    public ResponseEntity<Company> getCompany(@PathVariable Integer companyId) {

        Company company = companyService.getCompany(companyId);
        return ResponseEntity.ok(company);
    }

    @PostMapping
    public ResponseEntity<Result> addCompany(@Valid @RequestBody CompanyDTO companyDTO) {

        Result result = companyService.addCompany(companyDTO);
        if (result.getSuccess())
            return ResponseEntity.status(201).body(result);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    @PutMapping(value = "/{companyId}")
    public ResponseEntity<Result> editCompany(@PathVariable Integer companyId, @Valid @RequestBody CompanyDTO companyDTO) {

        Result result = companyService.editCompany(companyId, companyDTO);
        return ResponseEntity.status(result.getSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping(value = "/{companyId}")
    public ResponseEntity<Result> deleteCompany(@PathVariable Integer companyId) {

        Result result = companyService.deleteCompany(companyId);
        return ResponseEntity.status(result.getSuccess() ? 202 : 409).body(result);
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

/*
  worker post da address findById orqali emas yangi address chib birga save qilinadi
file
company put da address yangi emas findById orqali olib kelinadi
file
 company many to one ulanadi, bitta korxonada ko'plab bo'limlar mavjud bo'ladi
file
department many to one bo'ladi, bitta bo'limda ko'plab hodimlar ishlaydi
file
* */
