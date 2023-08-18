package io.goribco.apis.service;

import io.goribco.apis.model.authmodels.users.UserData;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDataService {
    UserData findByUsername(String username) throws UsernameNotFoundException;
}
