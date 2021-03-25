package uz.pdp.apicompany.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.apicompany.entity.Worker;
import uz.pdp.apicompany.payload.Result;
import uz.pdp.apicompany.payload.WorkerDTO;
import uz.pdp.apicompany.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/worker")
public class WorkerController {

    final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @GetMapping
    public ResponseEntity<?> getWorkers(@RequestParam("page") Integer page, @RequestParam Integer size) {

        List<Worker> workerList = workerService.getWorkers(page, size);
        return ResponseEntity.ok(workerList);
    }

    @GetMapping(value = "/{workerId}")
    public ResponseEntity<?> getWorker(@PathVariable Integer workerId) {

        Worker worker = workerService.getWorker(workerId);
        return ResponseEntity.ok(worker);
    }

    @PostMapping
    public ResponseEntity<?> addWorker(@Valid @RequestBody WorkerDTO workerDTO) {

        Result result = workerService.addWorker(workerDTO);
        return ResponseEntity.status(result.getSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(result);
    }

    @PutMapping(value = "/{workerId}")
    public ResponseEntity<?> editWorker(@PathVariable Integer workerId, @Valid @RequestBody WorkerDTO workerDTO) {

        Result result = workerService.editWorker(workerId, workerDTO);
        return ResponseEntity.status(result.getSuccess() ? HttpStatus.ACCEPTED : HttpStatus.CONFLICT).body(result);
    }

    @DeleteMapping(value = "/{workerId}")
    public ResponseEntity<?> deleteWorker(@PathVariable Integer workerId) {

        Result result = workerService.deleteWorker(workerId);
        return ResponseEntity.status(result.getSuccess() ? 202 : 404).body(result);
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
