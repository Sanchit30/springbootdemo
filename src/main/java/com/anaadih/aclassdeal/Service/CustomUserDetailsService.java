package com.anaadih.aclassdeal.Service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Repository.UserRepository;
import com.anaadih.aclassdeal.oauth2.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	  @Autowired
	  UserRepository userRepository;
	  
	  
//	  @Override
//	  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
//	      // Let people login with either username or email
//	        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
//	                .orElseThrow(() -> 
//	                        new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
//	        );
//
//	        return UserPrincipal.create(user);
//	}
	  
	  
	  	public UserDetails loadUserByUsername(String email)
	            throws UsernameNotFoundException {
	        User user = userRepository.findByEmail(email)
	                .orElseThrow(() ->
	                        new UsernameNotFoundException("User not found with email : " + email)
	        );

	        return UserPrincipal.create(user);
	    }
	  
	  
	    @Transactional
	    public UserDetails loadUserById(Long id) {
//	        User user = userRepository.findByIdAndBlocked(id,false).orElseThrow(
//	            () -> new UsernameNotFoundException("User not found with id : " + id)
//	        );
	        User user = userRepository.findById(id).orElseThrow(
		            () -> new UsernameNotFoundException("User not found with id : " + id)
		        );

	        return UserPrincipal.create(user);
	    }
	  
	  

}
