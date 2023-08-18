package io.goribco.apis.model.authmodels.users;

import io.goribco.apis.configs.security.permission.RoleData;
import io.goribco.apis.model.profile.ProfileData;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Document(collection = "users")
public class UserData extends UserBase implements UserDetails {
    @Id
    private String id;
    @Indexed(unique = true)
    private String uId;
    @Indexed(unique = true)
    private String url;

    private String firstname;
    private String lastname;
    private String password;
    private RoleData role;
    //private List<TokenData> tokens;

    @Transient
    private List<ProfileData> profileDataList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
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
}