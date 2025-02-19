package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import uz.carapp.rentcarapp.domain.enumeration.GenderEnum;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String middleName;
    private GenderEnum gender;
    private String email;
    private boolean activated;
    private String phone;
    private boolean status;

    public UserDTO() {

    }

    public UserDTO(long id) {
        this.id = id;
    }

}
