package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private boolean activated;
    private String phoneNumber;
    private boolean status;

    public UserDTO() {

    }

    public UserDTO(long id) {
        this.id = id;
    }

}
