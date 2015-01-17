package com.bdcyclists.bdcbook.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author Bazlur Rahman Rokon
 * @date 1/18/15.
 */
@Entity
public class UserProfile extends BaseEntity<Long> {

    @Id
    @GeneratedValue
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 6)
    private Gender gender;

    @Size(max = 30)
    private String mobileNo;

    @Column(length = 16)
    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Size(max = 100)
    private String address;

    @Size(max = 150)
    private String emergency;

    @Size(max = 100)
    private String facebookProfile;

    @OneToOne(mappedBy = "userProfile")
    private User user;

    @Override
    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public BloodGroup getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(BloodGroup bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getFacebookProfile() {
        return facebookProfile;
    }

    public void setFacebookProfile(String facebookProfile) {
        this.facebookProfile = facebookProfile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
