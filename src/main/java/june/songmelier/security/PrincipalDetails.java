package june.songmelier.security;

import june.songmelier.dto.MemberDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Setter
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User {
    private MemberDto.Principal principal;
    private boolean isFirstLogin = false;
    private String imageUrl;

    public PrincipalDetails(Long id, String username) {
        this.principal = new MemberDto.Principal(id, username);
    }

    public PrincipalDetails(Long id, String username, boolean isFirstLogin, String imageUrl) {
        this.principal = new MemberDto.Principal(id, username);
        this.isFirstLogin = isFirstLogin;
        this.imageUrl = imageUrl;
    }

    public Long getMemberId() {
        return this.principal.getMemberId();
    }

    public void firstLogin() {
        this.isFirstLogin = true;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.principal.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //===================================================================//

    @Override
    public String getName() {
        return null;
    }
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }
}
