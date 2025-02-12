package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import uz.carapp.rentcarapp.domain.enumeration.MerchantRoleEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.MerchantRole} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
public class MerchantRoleDTO implements Serializable {

    private Long id;

    private MerchantRoleEnum merchantRoleType;

    private UserDTO user;

    private MerchantDTO merchant;

    private MerchantBranchDTO merchantBranch;
}
