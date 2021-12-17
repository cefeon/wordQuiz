package net.cefeon.wordquiz.nonmodel;

import net.cefeon.wordquiz.model.WordQuizUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUserDetails implements UserDetails {

    private final String userName;
    private final String password;
    private final boolean active;
    private final List<GrantedAuthority> authorithies;

    public AppUserDetails(WordQuizUser wordQuizUser) {
        this.userName = wordQuizUser.getUserName();
        this.password = wordQuizUser.getPassword();
        this.active = wordQuizUser.isActive();
        this.authorithies = Arrays.stream(wordQuizUser.getAuthority().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorithies;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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
        return active;
    }
}
