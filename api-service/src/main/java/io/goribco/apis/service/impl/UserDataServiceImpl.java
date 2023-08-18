package io.goribco.apis.service.impl;

import io.goribco.apis.model.authmodels.users.UserData;
import io.goribco.apis.repository.UserDataRepository;
import io.goribco.apis.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDataServiceImpl implements UserDetailsService, UserDataService {

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserData userData = userDataRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));

        return userData;
    }

    @Override
    public UserData findByUsername(String username) throws UsernameNotFoundException {
        UserData userData = userDataRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));

        return userData;
    }
}