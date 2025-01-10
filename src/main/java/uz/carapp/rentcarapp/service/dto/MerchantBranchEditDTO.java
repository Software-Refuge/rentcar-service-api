package uz.carapp.rentcarapp.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class MerchantBranchEditDTO implements Serializable {
    private Long id;

    private String name;

    private String address;

    private String location;

    private String phone;
}
