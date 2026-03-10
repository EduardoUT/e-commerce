package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class User implements UserDetails, Identifiable<Long> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    @Column(length = 50, nullable = false, unique = true)
    private String username;
    @Getter
    @Setter@Column(nullable = false)
    private String password;
    @Getter
    @Setter
    @Column(length = 20, nullable = false)
    private String role;
    @Getter
    @Setter
    @Column(length = 92, nullable = false, unique = true)
    private String email;

    @Builder(builderMethodName = "anUser", setterPrefix = "with")
    private User(String username,
                 String password,
                 String role,
                 String email) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    @NullMarked
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
