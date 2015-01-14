package com.bdcyclists.bdcbook.dto;

import com.bdcyclists.bdcbook.domain.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

import java.util.Collection;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/13/15.
 */
public class SocialMediaUser extends SocialUser {
    private Long id;

    private String  firstName;

    private String  lastName;

    private Role role;

    private String socialSignInProvider;

    public SocialMediaUser(String  username, String  password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String  getFirstName() {
        return firstName;
    }

    public void setFirstName(String  firstName) {
        this.firstName = firstName;
    }

    public String  getLastName() {
        return lastName;
    }

    public void setLastName(String  lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSocialSignInProvider() {
        return socialSignInProvider;
    }

    public void setSocialSignInProvider(String socialSignInProvider) {
        this.socialSignInProvider = socialSignInProvider;
    }

    @Override
    public String  toString() {
        return "SocialMediaUser{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", socialSignInProvider=" + socialSignInProvider +
                '}';
    }
}
