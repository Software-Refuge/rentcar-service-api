package uz.carapp.rentcarapp.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.Brand} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class BrandEditDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private Boolean status;
}
