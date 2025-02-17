package uz.carapp.rentcarapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private Long userId;
    private Long branchId;
    private Long merchantId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long branchId, Long merchantId, Long userId) {
        super(username, password, authorities);
        this.userId = userId;
        this.branchId = branchId;
        this.merchantId = merchantId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public Long getUserId() {
        return userId;
    }
}
