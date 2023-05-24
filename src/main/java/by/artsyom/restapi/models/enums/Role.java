package by.artsyom.restapi.models.enums;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_MANEGER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
