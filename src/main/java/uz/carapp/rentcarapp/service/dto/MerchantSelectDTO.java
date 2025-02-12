package uz.carapp.rentcarapp.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MerchantSelectDTO {
    Long merchantId;
    Long merchantBranchId;
    String token;
}
