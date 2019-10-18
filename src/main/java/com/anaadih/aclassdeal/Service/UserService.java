package com.anaadih.aclassdeal.Service;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.anaadih.aclassdeal.Model.PasswordDTO;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.util.CustomException;

public interface UserService {

	List<User> getAllUsers(int limit, int offset);

	long getCountOfAllUsers();

	Object blockUser(int userId);

	User getLoggedInUser();

	User updateUser(User user);

	boolean enableDisablePhoneNumber(long userId, boolean enable);


	Object changeUserPassword(long userId, PasswordDTO passwordDTO) throws CustomException;

	boolean deactivateUserAccount(int userId, String message);

	List<User> getAllDeactivatedAccount(int limit, int offset);

	long getCountOfAllDeactivatedAccount();

	boolean reactivateUserAccount(int userId);

}
