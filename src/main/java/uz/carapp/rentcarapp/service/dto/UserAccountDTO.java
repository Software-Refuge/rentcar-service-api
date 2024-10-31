package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import uz.carapp.rentcarapp.domain.Authority;
import uz.carapp.rentcarapp.domain.User;

import java.io.Serializable;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with only the public attributes.
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserAccountDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private boolean activated = false;

    private String phoneNumber;
    private boolean status;
    private Set<String> authorities;

    private Set<String> getAuthoritiesOfString(Set<Authority> authorities){
        if (!authorities.isEmpty()){
            return authorities.stream().map(Authority::getName).collect(Collectors.toSet());
        }
        return null;
    }
    public UserAccountDTO(User user) {
        Set<String> authoritiesOfString = getAuthoritiesOfString(user.getAuthorities());
        this.id = user.getId();
        // Customize it here if you need, or not, firstName/lastName/etc
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.login = user.getLogin();
        this.email = user.getEmail();
        this.activated = user.isActivated();
        this.status = user.isStatus();
        this.authorities = authoritiesOfString;
    }

    public UserAccountDTO(Long id) {
        this.id=id;
    }

}
