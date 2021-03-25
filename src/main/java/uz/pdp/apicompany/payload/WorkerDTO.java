package uz.pdp.apicompany.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.apicompany.entity.Address;
import uz.pdp.apicompany.entity.Department;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerDTO {

    @NotNull(message = "Worker name should be filled!")
    private String name;

    @NotNull(message = "Worker's phone number should be filled!")
    private String phoneNumber;

    @NotNull(message = "Worker's Department Id should be filled")
    private Integer departmentId;

    @NotNull(message = "Worker's Address should be filled!")
    private Integer addressId;
}
