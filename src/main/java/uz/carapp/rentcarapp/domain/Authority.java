package uz.carapp.rentcarapp.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * An authority (a security role) used by Spring Security.
 */
@Entity(name = "tb_authority")
@Getter
@Setter
@NoArgsConstructor
public class Authority implements Serializable, GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 50)
    @Id
    @Column(length = 50)
    private String name;

    public Authority(@NotNull @Size(max = 50) String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
