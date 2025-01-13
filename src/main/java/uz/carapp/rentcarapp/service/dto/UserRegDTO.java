package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class UserRegDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String phone;
    private String email;
    private boolean gender;
    private Instant birthDate;
    private boolean status;
    private String password;
    private Set<String> roles;
}
