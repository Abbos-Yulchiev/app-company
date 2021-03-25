package uz.pdp.apicompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.apicompany.entity.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Integer> {
}
