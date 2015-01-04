package com.bdcyclists.bdcbook.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdcyclists.bdcbook.domain.User;
import com.bdcyclists.bdcbook.dto.RegistrationForm;
import com.bdcyclists.bdcbook.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserServiceImpl.class);

	private UserRepository repository;

	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository repository,
			PasswordEncoder passwordEncoder) {

		this.repository = repository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	@Override
	public User registerNewUserAccount(RegistrationForm userAccountData)
			throws DuplicateEmailException {
		LOGGER.debug("Registering new user account with information: {}",
				userAccountData);

		if (emailExist(userAccountData.getEmail())) {
			LOGGER.debug("Email: {} exists. Throwing exception.",
					userAccountData.getEmail());
			throw new DuplicateEmailException("The email address: "
					+ userAccountData.getEmail() + " is already in use.");
		}

		LOGGER.debug("Email: {} does not exist. Continuing registration.",
				userAccountData.getEmail());

		String encodedPassword = encodePassword(userAccountData);

		User.Builder user = User.getBuilder().email(userAccountData.getEmail())
				.firstName(userAccountData.getFirstName())
				.lastName(userAccountData.getLastName())
				.password(encodedPassword);

		User registered = user.build();

		LOGGER.debug("Persisting new user with information: {}", registered);

		return repository.save(registered);
	}

	private boolean emailExist(String email) {
		LOGGER.debug(
				"Checking if email {} is already found from the database.",
				email);

		User user = repository.findByEmail(email);

		if (user != null) {
			LOGGER.debug(
					"User account: {} found with email: {}. Returning true.",
					user, email);
			return true;
		}

		LOGGER.debug("No user account found with email: {}. Returning false.",
				email);

		return false;
	}

	private String encodePassword(RegistrationForm dto) {

		return passwordEncoder.encode(dto.getPassword());
	}

}
