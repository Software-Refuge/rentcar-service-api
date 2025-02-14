package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import uz.carapp.rentcarapp.domain.enumeration.GenderEnum;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class UserRegDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    private GenderEnum gender;
    private Instant birthDate;
    private boolean status;
    private String password;
    private Set<String> roles;
}
