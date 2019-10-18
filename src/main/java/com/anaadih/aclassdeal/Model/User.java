package com.anaadih.aclassdeal.Model;


import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.*;
import com.anaadih.aclassdeal.config.AuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Email
    @Column(nullable = false)
    private String email;

    private String bio;
    
    private String imageUrl;
    
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;
    
    private String userType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;
    
    //For blocking the user
    private boolean blocked;
    
    private boolean isFirstTimeLogin;
    
    private Date joinDate;
    
    private boolean enableMobileNumber;
    
    private boolean deactivated;
    
    private String deactivatedMessage;
    
    private Date deactivatedDate;
    
    @PrePersist
    public void setData() {
    	this.setUserType("USER");
    	this.setJoinDate(new Date());
    	this.setEnableMobileNumber(true);
    	this.setFirstTimeLogin(true);
    }

    
    

	public boolean isDeactivated() {
		return deactivated;
	}



	public String getDeactivatedMessage() {
		return deactivatedMessage;
	}




	public void setDeactivatedMessage(String deactivatedMessage) {
		this.deactivatedMessage = deactivatedMessage;
	}




	public Date getDeactivatedDate() {
		return deactivatedDate;
	}




	public void setDeactivatedDate(Date deactivatedDate) {
		this.deactivatedDate = deactivatedDate;
	}




	public void setDeactivated(boolean deactivated) {
		this.deactivated = deactivated;
	}




	public String getPhoneNumber() {
		return phoneNumber;
	}






	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}






	public String getBio() {
		return bio;
	}




	public void setBio(String bio) {
		this.bio = bio;
	}




	public boolean isEnableMobileNumber() {
		return enableMobileNumber;
	}




	public void setEnableMobileNumber(boolean enableMobileNumber) {
		this.enableMobileNumber = enableMobileNumber;
	}




	public Date getJoinDate() {
		return joinDate;
	}


	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}


	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isFirstTimeLogin() {
		return isFirstTimeLogin;
	}

	public void setFirstTimeLogin(boolean isFirstTimeLogin) {
		this.isFirstTimeLogin = isFirstTimeLogin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

    
}
