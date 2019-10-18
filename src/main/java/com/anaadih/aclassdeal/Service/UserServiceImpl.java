package com.anaadih.aclassdeal.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.PasswordDTO;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Repository.UserRepository;
import com.anaadih.aclassdeal.util.CustomException;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository; 
	@Autowired
	private CommonService commonService;
	
	   @Autowired
	    private PasswordEncoder passwordEncoder;

	@Override
	public List<User> getAllUsers(int limit, int offset) {
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.DESC,"joinDate"));
		return userRepository.findByUserType("USER",pg);
	}

	@Override
	public long getCountOfAllUsers() {
		return userRepository.count();
	}
	
	@Override
	public List<User> getAllDeactivatedAccount(int limit, int offset) {
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.DESC,"joinDate"));
		return userRepository.findByUserTypeAndDeactivated("USER",true,pg);
	}

	@Override
	public long getCountOfAllDeactivatedAccount() {
		return userRepository.countByDeactivated(true);
	}

	@Override
	public Object blockUser(int userId) {
		Optional<User> useropt=userRepository.findById((long) userId);
		if(useropt.isPresent()) {
			User user = useropt.get();
			if(!user.isBlocked()) {
			user.setBlocked(true);
			
			userRepository.save(user);
			}
		}
		return null;
	}

	@Override
	public User getLoggedInUser() {
		return commonService.getLoggedInUser();
	}

	@Override
	public User updateUser(User user) {
		Optional<User> useropt=userRepository.findById(user.getId());
		if(useropt.isPresent()) {
			User prevuser = useropt.get();
			prevuser.setBio(user.getBio());
			prevuser.setPhoneNumber(user.getPhoneNumber());
			prevuser.setName(user.getName());
			return userRepository.save(prevuser);
		}
		return user;
	}

	@Override
	public boolean enableDisablePhoneNumber(long userId, boolean enable) {
		Optional<User> useropt=userRepository.findById(userId);
		if(useropt.isPresent()) {
			User prevuser = useropt.get();
			prevuser.setEnableMobileNumber(enable);
			prevuser= userRepository.save(prevuser);
			return prevuser.isEnableMobileNumber();
		}
		return true;
	}

	@Override
	public Object changeUserPassword(long userId, PasswordDTO passwordDTO) throws CustomException {
		Optional<User> useropt=userRepository.findById(userId);
		if(useropt.isPresent()) {
			User prevuser = useropt.get();
			//Check for Previous password , check for new pasword
			Boolean match = passwordEncoder.matches(passwordDTO.getOldPassword(), prevuser.getPassword());
			System.out.println("Match "+match);
			if(match) {
			prevuser.setPassword(passwordEncoder.encode(passwordDTO.getNewPassword()));
			return userRepository.save(prevuser);
			}
			else
			{
				throw new CustomException("Entered Password is wrong");
			}
			
		}
		return null;
	}

	@Override
	public boolean deactivateUserAccount(int userId, String message) {
		Optional<User> useropt=userRepository.findById((long) userId);
		if(useropt.isPresent()) {
			User user = useropt.get();
			if(!user.isDeactivated()) {
			user.setDeactivated(true);
			user.setDeactivatedMessage(message);
			user.setDeactivatedDate(new Date());
			userRepository.save(user);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean reactivateUserAccount(int userId) {
		Optional<User> useropt=userRepository.findById((long) userId);
		if(useropt.isPresent()) {
			User user = useropt.get();
			if(user.isDeactivated()) {
			user.setDeactivated(false);
			user.setDeactivatedMessage(null);
			user.setDeactivatedDate(null);
			userRepository.save(user);
			}
			return true;
		}
		return false;
	}



}
