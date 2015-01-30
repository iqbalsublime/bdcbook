package com.bdcyclists.bdcbook.service;

import com.bdcyclists.bdcbook.domain.Role;
import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.domain.UserProfile;
import com.bdcyclists.bdcbook.dto.RegistrationForm;
import com.bdcyclists.bdcbook.repository.UserProfileRepository;
import com.bdcyclists.bdcbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public User registerNewUserAccount(RegistrationForm userAccountData)
            throws DuplicateEmailException {
        LOGGER.debug("Registering new user account with information: {}", userAccountData);

        if (emailExist(userAccountData.getEmail())) {
            LOGGER.debug("Email: {} exists. Throwing exception.", userAccountData.getEmail());

            throw new DuplicateEmailException("The email address: " + userAccountData.getEmail() + " is already in use.");
        }

        LOGGER.debug("Email: {} does not exist. Continuing registration.", userAccountData.getEmail());

        String encodedPassword = encodePassword(userAccountData);

        User.Builder userBuilder = User.getBuilder()
                .email(userAccountData.getEmail())
                .firstName(userAccountData.getFirstName())
                .lastName(userAccountData.getLastName())
                .role(Role.ROLE_USER)
                .enabled(true)
                .locked(false)
                .password(encodedPassword);

        if (userAccountData.getSocialSignInProvider() != null) {
            LOGGER.debug("signInProvider is found ={}", userAccountData.getSocialSignInProvider());

            userBuilder.setSignInProvider(userAccountData.getSocialSignInProvider());
        }

        User registered = userBuilder.build();

        LOGGER.debug("Persisting new user with information: {}", registered);

        return userRepository.save(registered);
    }

    @Override
    public User findByEmail(String email) {
        LOGGER.debug("Retrieving user from database by email = {} ", email);

        return userRepository.findByEmail(email);
    }

    @Override
    public void updatePassword(User user) {
        User userFromDb = findByEmail(user.getEmail());
        userFromDb.setPasswordResetHash(user.getPasswordResetHash());

        userRepository.save(userFromDb);
    }

    @Override
    public User findByEmailAndResetHash(String emailAddress, String resetKey) {

        return userRepository.findByEmailAndPasswordResetHash(emailAddress, resetKey);
    }

    @Override
    public User getCurrentLoggedInUser() {
        LOGGER.debug("getCurrentLoggedInUser() > Retrieving current logged in user from database ");

        //Though SecurityContextHolder return User object, but lets fetch from database to get always updated one
        String email = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getEmail();
        LOGGER.debug("Currently logged in user is : {}", email);

        LOGGER.debug("Fetch user from database and return ");
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveProfile(UserProfile userProfile) {
        LOGGER.debug("going to save userProfile : {}", userProfile);

        User currentLoggedInUser = getCurrentLoggedInUser();

        if (userProfile.getId() == null) {
            UserProfile profileSaved = userProfileRepository.save(userProfile);
            currentLoggedInUser.setUserProfile(profileSaved);
            userRepository.save(currentLoggedInUser);
        } else {
            UserProfile userProfileToBeUpdated = userProfileRepository.findOne(userProfile.getId());

            userProfileToBeUpdated.setAddress(userProfile.getAddress());
            userProfileToBeUpdated.setBirthDate(userProfile.getBirthDate());
            userProfileToBeUpdated.setGender(userProfile.getGender());
            userProfileToBeUpdated.setMobileNo(userProfile.getMobileNo());
            userProfileToBeUpdated.setBloodGroup(userProfile.getBloodGroup());
            userProfileToBeUpdated.setEmergency(userProfile.getEmergency());

            userProfileRepository.save(userProfileToBeUpdated);
        }
    }

    private boolean emailExist(String email) {
        LOGGER.debug("Checking if email {} is already found from the database.", email);

        User user = userRepository.findByEmail(email);

        if (user != null) {
            LOGGER.debug("User account: {} found with email: {}. Returning true.", user, email);
            return true;
        }

        LOGGER.debug("No user account found with email: {}. Returning false.", email);

        return false;
    }

    private String encodePassword(RegistrationForm dto) {

        return passwordEncoder.encode(dto.getPassword());
    }
}
