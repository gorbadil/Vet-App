package simsek.ali.VeterinaryManagementProject.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {
    Long id;
    String name;
    String phone;
    String email;
    String address;
    String city;

}