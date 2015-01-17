package com.bdcyclists.bdcbook.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class User extends BaseEntity<Long> implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Column(name = "password", length = 255)
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserRole> userRoles = new HashSet<>();

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = false)
    private boolean locked;

    private String socialSignInProvider;

    private String passwordResetHash;

    @OneToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @OneToOne
    @JoinColumn(name = "user_profile_id")
    private UserProfile userProfile;

    @Transient
    private List<SimpleGrantedAuthority> simpleGrantedAuthorityList;

    public User() {

    }

    public static Builder getBuilder() {
        return new Builder();
    }

    @Override
    public Long getId() {

        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    // Supported methods for springframework security UserDetails interface
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (simpleGrantedAuthorityList == null && !CollectionUtils.isEmpty(getUserRoles())) {
            simpleGrantedAuthorityList = new ArrayList<>();

            simpleGrantedAuthorityList
                    .addAll(getUserRoles()
                            .stream()
                            .map(userRole -> new SimpleGrantedAuthority(userRole.getRole().name())).collect(Collectors.toList()));
        }

        return simpleGrantedAuthorityList;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //Not implemented
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Not implemented
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocialSignInProvider() {
        return socialSignInProvider;
    }

    public void setSocialSignInProvider(String socialSignInProvider) {
        this.socialSignInProvider = socialSignInProvider;
    }

    public String getPasswordResetHash() {
        return passwordResetHash;
    }

    public void setPasswordResetHash(String passwordResetHash) {
        this.passwordResetHash = passwordResetHash;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", userRoles=" + userRoles +
                ", enabled=" + enabled +
                ", locked=" + locked +
                ", socialSignInProvider=" + socialSignInProvider +
                '}';
    }

    public static class Builder {

        private User user;

        public Builder() {
            user = new User();
        }

        public Builder email(String email) {
            user.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            user.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            user.lastName = lastName;
            return this;
        }

        public Builder password(String password) {
            user.password = password;
            return this;
        }

        public User build() {
            return user;
        }

        public Builder id(Long id) {
            user.id = id;
            return this;
        }

        public Builder role(Role role) {
            user.userRoles.add(new UserRole(this.user, role));
            return this;
        }

        public Builder enabled(boolean enabled) {
            user.enabled = enabled;
            return this;
        }

        public Builder locked(boolean locked) {
            user.locked = locked;
            return this;
        }

        public Builder setSignInProvider(String signInProvider) {
            user.socialSignInProvider = signInProvider;
            return this;
        }

    }
}
