package uz.pdp.apicompany.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pdp.apicompany.entity.Address;
import uz.pdp.apicompany.entity.Department;
import uz.pdp.apicompany.entity.Worker;
import uz.pdp.apicompany.payload.Result;
import uz.pdp.apicompany.payload.WorkerDTO;
import uz.pdp.apicompany.repository.AddressRepository;
import uz.pdp.apicompany.repository.DepartmentRepository;
import uz.pdp.apicompany.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {


    final WorkerRepository workerRepository;
    final DepartmentRepository departmentRepository;
    final AddressRepository addressRepository;


    public WorkerService(WorkerRepository workerRepository,
                         DepartmentRepository departmentRepository,
                         AddressRepository addressRepository) {
        this.workerRepository = workerRepository;
        this.departmentRepository = departmentRepository;
        this.addressRepository = addressRepository;
    }

    public List<Worker> getWorkers(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Worker> workerPage = workerRepository.findAll(pageable);
        return workerPage.getContent();
    }

    public Worker getWorker(Integer workerId) {

        Optional<Worker> optionalWorker = workerRepository.findById(workerId);
        return optionalWorker.orElse(null);
    }

    public Result addWorker(WorkerDTO workerDTO) {

        Optional<Address> optionalAddress = addressRepository.findById(workerDTO.getAddressId());
        if (!optionalAddress.isPresent())
            return new Result("Invalid Address Id", false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDTO.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new Result("Invalid Department Id", false);

        Worker worker = new Worker();
        worker.setName(workerDTO.getName());
        worker.setPhoneNumber(workerDTO.getPhoneNumber());
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        workerRepository.save(worker);
        return new Result("New Worker successfully added", true);
    }

    public Result editWorker(Integer workerId, WorkerDTO workerDTO) {

        Optional<Worker> optionalWorker = workerRepository.findById(workerId);
        if (!optionalWorker.isPresent())
            return new Result("Invalid Worker id", false);

        Optional<Address> optionalAddress = addressRepository.findById(workerDTO.getAddressId());
        if (!optionalAddress.isPresent())
            return new Result("Invalid Address Id", false);

        Optional<Department> optionalDepartment = departmentRepository.findById(workerDTO.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new Result("Invalid Department Id", false);

        Worker worker = optionalWorker.get();
        worker.setAddress(optionalAddress.get());
        worker.setDepartment(optionalDepartment.get());
        worker.setName(workerDTO.getName());
        worker.setPhoneNumber(worker.getPhoneNumber());
        workerRepository.save(worker);
        return new Result("Worker edited.", true);
    }

    public Result deleteWorker(Integer workerId) {

        Optional<Worker> optionalWorker = workerRepository.findById(workerId);
        if (!optionalWorker.isPresent())
            return new Result("Invalid worker Id", false);

        workerRepository.deleteById(workerId);
        return new Result("Worker deleted.", true);
    }
}
