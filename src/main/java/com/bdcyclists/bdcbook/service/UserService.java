package com.bdcyclists.bdcbook.service;

import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.dto.RegistrationForm;

public interface UserService {
    public User registerNewUserAccount(RegistrationForm userAccountData) throws DuplicateEmailException;

}
