package com.bdcyclists.bdcbook.service;

import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.dto.RegistrationForm;
import org.springframework.security.access.annotation.Secured;

public interface UserService {
    public User registerNewUserAccount(RegistrationForm userAccountData) throws DuplicateEmailException;

}
