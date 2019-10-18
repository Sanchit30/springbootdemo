package com.anaadih.aclassdeal.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Repository.UserRepository;

@Service
public class CommonService {
	
	@Autowired
	private UserRepository userrepository;

	
	public User getLoggedInUser() {
		
	
	String userId=SecurityContextHolder.getContext().getAuthentication().getName();
	//Setting logged in user
	//Optional<User> user = userrepository.findByUsername(userId);
	Optional<User> user = userrepository.findByEmail(userId);
	if(user.isPresent()) {
		return user.get();
	}
	return null;
}
}
