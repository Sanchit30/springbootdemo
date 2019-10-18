package com.anaadih.aclassdeal.oauth2;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.config.AuthProvider;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPrincipal implements OAuth2User, UserDetails {
	private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private String userType;
    private boolean blocked;
    private boolean enableMobileNumber;
    private AuthProvider provider;
    private  String imageUrl;
    private boolean deactivated;

    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities,String userType,boolean blocked,boolean enableMobileNumber,AuthProvider authProvider,String imageUrl,boolean deactivated) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.userType=userType;
        this.blocked=blocked;
        this.enableMobileNumber=enableMobileNumber;
        this.provider=authProvider;
        this.imageUrl=imageUrl;
        this.deactivated=deactivated;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = Collections.
                singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new UserPrincipal(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities,
                user.getUserType(),
                user.isBlocked(),
                user.isEnableMobileNumber(),
                user.getProvider(),
                user.getImageUrl(),
                user.isDeactivated()
        );
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }

	public String getUserType() {
		return userType;
	}
	
	

	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	
	
	

	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public boolean isEnableMobileNumber() {
		return enableMobileNumber;
	}

	public void setEnableMobileNumber(boolean enableMobileNumber) {
		this.enableMobileNumber = enableMobileNumber;
	}
	
	

	public boolean isDeactivated() {
		return deactivated;
	}

	public void setDeactivated(boolean deactivated) {
		this.deactivated = deactivated;
	}

	@Override
	public String toString() {
		return "UserPrincipal [id=" + id + ", email=" + email + ", password=" + password + ", authorities="
				+ authorities + ", attributes=" + attributes + ", userType=" + userType + ", blocked=" + blocked + "]";
	}
	
	
		
}
