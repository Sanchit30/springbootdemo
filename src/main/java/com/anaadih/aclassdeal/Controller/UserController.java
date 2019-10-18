package com.anaadih.aclassdeal.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.DeactivateUserDTO;
import com.anaadih.aclassdeal.Model.PasswordDTO;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Repository.UserRepository;
import com.anaadih.aclassdeal.Service.FileUploadService;
import com.anaadih.aclassdeal.util.CommonResponseSender;
import com.anaadih.aclassdeal.util.CustomException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class UserController {

	@Autowired
	private com.anaadih.aclassdeal.Service.UserService userService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping(value="/getAllUsers",method=RequestMethod.GET)
	public Map<String,Object> getAllUsers(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		//First Page is 0th index
		offset=offset-1;
		map.put("userList", userService.getAllUsers(limit,offset));
		map.put("count",userService.getCountOfAllUsers());
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	@RequestMapping(value="/updateUser",method=RequestMethod.PUT)
	public Map<String,Object> updateUser(@RequestParam(value="file",required=false) MultipartFile[] files,@RequestParam(value="userString",required=false) String userString,

			HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException
	{
		final HashMap<String, Object> map = new HashMap<>();
		ObjectMapper objMapper= new ObjectMapper();
		TypeReference<User> mapType= new TypeReference<User>() {
		};
		User user= objMapper.readValue(userString, mapType);
		user=userService.updateUser(user);
		if(user!=null)
		{
			fileUploadService.saveImageofUser(files,user);
		}
		
		map.put("user", user);
		
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public Map<String, Object> changePassword(
			@RequestBody PasswordDTO passwordDTO,
			@RequestParam(value = "userId", required = true) long userId,
			 HttpServletResponse response,
			HttpServletRequest request) throws CustomException {
		final Map<String, Object> map = new HashMap<>();
		
			map.put("updatedUser", userService.changeUserPassword(userId, passwordDTO));
		
		return CommonResponseSender.updatedSuccessResponse(map, response);
	}
	
	@RequestMapping(value = "/enableDisablePhoneNumber", method = RequestMethod.GET)
	public Map<String, Object> setEnableDisablePhoneNumber(
			@RequestParam(value = "id", required = true) long userId,
			@RequestParam(value = "enable", required = true) boolean enable,
			 HttpServletResponse response,
			HttpServletRequest request) throws CustomException {
		final Map<String, Object> map = new HashMap<>();
		map.put("enable", userService.enableDisablePhoneNumber(userId,enable));
		return CommonResponseSender.updatedSuccessResponse(map, response);
	}
	
	
	@RequestMapping(value="/getAllCountUsers",method=RequestMethod.GET)
	public Map<String,Object> getAllCountUsers(
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		//First Page is 0th index
		
		map.put("count",userService.getCountOfAllUsers());
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	@RequestMapping(value="/getLoggedInUser",method=RequestMethod.GET)
	public Map<String,Object> getLoggedInUser(
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();

		map.put("user",userService.getLoggedInUser());
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	
	@RequestMapping(value="/blockUser",method=RequestMethod.GET)
	public Map<String,Object> getAllCountUsers(
			@RequestParam(value="userId")int userId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		//First Page is 0th index
		
		map.put("user",userService.blockUser(userId));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	
	@RequestMapping(value="/deactivateUserAccount",method=RequestMethod.POST)
	public Map<String,Object> deactivateUserAccount(
			@RequestBody DeactivateUserDTO dto,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		//First Page is 0th index
		if(dto!=null && dto.getUserId()!=0 && dto.getDescription()!=null) {
		map.put("deactivated",userService.deactivateUserAccount((int)dto.getUserId(),dto.getDescription()));
		}
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value="/reactivateUserAccount",method=RequestMethod.GET)
	public Map<String,Object> reactivateUserAccount(
			@RequestParam(value="userId")int userId,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		//First Page is 0th index
		map.put("reactivated",userService.reactivateUserAccount(userId));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value="/getAllDeactivatedAccount",method=RequestMethod.GET)
	public Map<String,Object> getAllDeactivatedAccount(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,			
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		//First Page is 0th index
		offset=offset-1;
		map.put("deactivatedAccountList", userService.getAllDeactivatedAccount(limit,offset));
		map.put("count",userService.getCountOfAllDeactivatedAccount());
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	
}
