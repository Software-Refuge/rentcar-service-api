package uz.carapp.rentcarapp.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String CONTENT_MANAGER = "ROLE_CONTENT_MANAGER";

    public static final String SALE_MANAGER = "ROLE_SALE_MANAGER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {}
}
