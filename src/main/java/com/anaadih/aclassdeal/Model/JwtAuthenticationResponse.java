package com.anaadih.aclassdeal.Model;

import com.anaadih.aclassdeal.config.AuthProvider;
import com.anaadih.aclassdeal.oauth2.UserPrincipal;

//Response class changed
public class JwtAuthenticationResponse {
//    private String accessToken;
//    private String tokenType = "Bearer";
//
//    public JwtAuthenticationResponse(String accessToken) {
//        this.accessToken = accessToken;
//    }
//
//    public String getAccessToken() {
//        return accessToken;
//    }
//
//    public void setAccessToken(String accessToken) {
//        this.accessToken = accessToken;
//    }
//
//    public String getTokenType() {
//        return tokenType;
//    }
//
//    public void setTokenType(String tokenType) {
//        this.tokenType = tokenType;
//    }
	
	
	private String email;
	private String token;
	private String userName;
	private String userType;
	private String bio;
	private AuthProvider provider;
	private long id;
	private boolean blocked;
	private boolean enableMobileNumber; 
	private String imageUrl;
	
	public JwtAuthenticationResponse(String jwt, UserPrincipal userPrincipal) {
		this.token=jwt;
		this.email=userPrincipal.getEmail();
		this.userName=userPrincipal.getUsername();
		this.userType=userPrincipal.getUserType();
		this.id=userPrincipal.getId();
		this.enableMobileNumber=userPrincipal.isEnableMobileNumber();
		this.blocked=userPrincipal.isBlocked();
		this.provider=userPrincipal.getProvider();
		this.imageUrl=userPrincipal.getImageUrl();
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public boolean isEnableMobileNumber() {
		return enableMobileNumber;
	}
	public void setEnableMobileNumber(boolean enableMobileNumber) {
		this.enableMobileNumber = enableMobileNumber;
	}
	public AuthProvider getProvider() {
		return provider;
	}
	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	
	
	
	
}
