package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * A DTO for the {@link uz.carapp.rentcarapp.domain.Car} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@Getter
@Setter
@ToString
public class CarDTO implements Serializable {

    private Long id;

    private String stateNumberPlate;

    private Integer deposit;

    private ModelDTO model;

    private MerchantDTO merchant;

    private MerchantBranchDTO merchantBranch;

    private Map<String, String> params; // Param nomlari va qiymatlari
}
