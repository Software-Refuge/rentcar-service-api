package uz.carapp.rentcarapp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {

    private long userId;
    private long branchId;
    private long merchantId;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, long branchId, long merchantId, long userId) {
        super(username, password, authorities);
        this.branchId = branchId;
        this.merchantId = merchantId;
        this.userId = userId;
    }

    public long getBranchId() {
        return branchId;
    }

    public long getMerchantId() {
        return merchantId;
    }

    public long getUserId() {
        return userId;
    }
}
