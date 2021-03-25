package uz.pdp.apicompany.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apicompany.entity.Company;
import uz.pdp.apicompany.entity.Department;
import uz.pdp.apicompany.payload.CompanyDTO;
import uz.pdp.apicompany.payload.DepartmentDTO;
import uz.pdp.apicompany.payload.Result;
import uz.pdp.apicompany.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/department")
public class DepartmentController {


    final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<?> getDepartments(@RequestParam Integer page) {

        List<Department> departmentList = departmentService.getCompanies(page);
        return ResponseEntity.ok(departmentList);
    }

    @GetMapping(value = "/{departmentId}")
    public ResponseEntity<Department> getDepartment(@PathVariable Integer departmentId) {

        Department department = departmentService.getCompany(departmentId);
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<Result> addCompany(@Valid @RequestBody DepartmentDTO departmentDTO) {

        Result result = departmentService.addDepartment(departmentDTO);
        if (result.getSuccess())
            return ResponseEntity.status(201).body(result);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
    }

    @PutMapping(value = "/{departmentId}")
    public ResponseEntity<Result> editDepartment(@PathVariable Integer departmentId, @Valid @RequestBody DepartmentDTO departmentDTO) {

        final Result result = departmentService.editDepartment(departmentId, departmentDTO);
        return ResponseEntity.status(result.getSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping(value = "/{companyId}")
    public ResponseEntity<Result> deleteCompany(@PathVariable Integer companyId) {

        Result result = departmentService.deleteDepartment(companyId);
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
