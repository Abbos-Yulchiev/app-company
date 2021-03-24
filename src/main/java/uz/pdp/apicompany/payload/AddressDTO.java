package uz.pdp.apicompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    @NotNull(message = "Street should be filled.")
    private String street;

    @NotNull(message = "Home number should be filled")
    private String homeNumber;
}
