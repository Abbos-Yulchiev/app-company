package uz.pdp.apicompany.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.apicompany.entity.Address;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {


    @NotNull(message = "corp name should be filled")
    private String corpName;

    @NotNull(message = "director name should be filled")
    private String directorName;

    @NotNull(message = "Address field should be filled")
    private Address address;
}
