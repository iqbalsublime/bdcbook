package com.bdcyclists.bdcbook.service;

import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.dto.SocialMediaUser;
import com.bdcyclists.bdcbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/13/15.
 */

@Service
public class BdcbookUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BdcbookUserDetailsService.class);

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Loading user by username: {}", username);

        User user = repository.findByEmail(username);

        LOGGER.debug("Found user: {}", user);

        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        SocialMediaUser socialMediaUser = new SocialMediaUser(user.getUsername(),user.getPassword(),user.getAuthorities());
        socialMediaUser.setFirstName(user.getFirstName());
        socialMediaUser.setLastName(user.getLastName());
        socialMediaUser.setSocialSignInProvider(user.getSocialSignInProvider());

        LOGGER.debug("Returning user details: {}", socialMediaUser);
        return socialMediaUser;
    }
}
