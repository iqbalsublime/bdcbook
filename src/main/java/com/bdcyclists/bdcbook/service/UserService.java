package com.bdcyclists.bdcbook.service;

import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.domain.UserProfile;
import com.bdcyclists.bdcbook.dto.RegistrationForm;

public interface UserService {
    public User registerNewUserAccount(RegistrationForm userAccountData) throws DuplicateEmailException;

    public User findByEmail(String email);

    void updatePassword(User user);

    User findByEmailAndResetHash(String emailAddress, String resetKey);

    User getCurrentLoggedInUser();

    void saveProfile(UserProfile userProfile);
}
