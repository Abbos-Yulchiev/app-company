package uz.pdp.apicompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.apicompany.entity.Company;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {


    @NotNull(message = "Name should be filled!")
    private String name;

    @NotNull(message = "Company Should be filled!")
    private Integer companyId;
}
