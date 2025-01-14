package uz.carapp.rentcarapp.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MerchantBranchSaveDTO implements Serializable {
    private Long id;

    @NotNull
    private String name;

    private String address;

    private String latitude;

    private String longitude;

    private String phone;

    private Long merchantId;
}
