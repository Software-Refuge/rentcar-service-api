package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.ParamValue} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class ParamValueSaveDTO implements Serializable {

    private Long id;

    private String name;

    private Boolean status;

    private Long paramId;
}
