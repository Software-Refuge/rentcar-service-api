package uz.carapp.rentcarapp.service.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.MerchantBranch} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class MerchantBranchDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String address;

    private String latitude;

    private String longitude;

    private String phone;

    private MerchantDTO merchant;
}
