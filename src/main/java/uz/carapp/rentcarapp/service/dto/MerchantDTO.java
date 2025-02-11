package uz.carapp.rentcarapp.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.Merchant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class MerchantDTO implements Serializable {

    private Long id;

    @NotNull
    private String companyName;

    private String brandName;

    private String inn;

    private String owner;

    private String phone;

    private String address;

    private UserDTO user;
}
