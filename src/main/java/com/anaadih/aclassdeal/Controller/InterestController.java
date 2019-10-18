package com.anaadih.aclassdeal.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anaadih.aclassdeal.Model.IntrestModel;
import com.anaadih.aclassdeal.Service.InterestService;
import com.anaadih.aclassdeal.util.CommonResponseSender;
import com.anaadih.aclassdeal.util.CustomException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class InterestController {

	
	@Autowired
	private InterestService interestService; 
	
	
	
	/**
	 * @description Mapping for all bids on a particular product
	 * @param limit
	 * @param offset
	 * @param productId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllInterestOfProduct",method=RequestMethod.GET)
	public Map<String,Object> getAllProducts(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,@RequestParam(value="productId") int productId,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("interestList", interestService.getAllInterestOfProduct(limit,offset-1,productId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	/**
	 * @description Method for saving a bod of a user
	 * @param bid
	 * @param errors
	 * @param request
	 * @param response
	 * @return
	 * @throws CustomException
	 */
	@RequestMapping(value="/saveInterestForProduct",method=RequestMethod.POST)
	public Map<String,Object> saveInterestForProduct(@RequestBody  IntrestModel bid,Errors errors,HttpServletRequest request,HttpServletResponse response) throws CustomException
	{
		final HashMap<String, Object> map = new HashMap<>();
		if(errors.hasErrors())
		{
			return (Map<String, Object>) map.put("error", "Something went wrong");
		}

		map.put("interest", interestService.saveInterestForProduct(bid));
		
		//WEBSOCKET FOR SENDING NOTIFCATION TO USER OF THAT PRODUCT
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	/**
	 * @description Method for all bids of a User
	 * @param limit
	 * @param offset
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllInterestOfUser",method=RequestMethod.GET)
	public Map<String,Object> getAllInterestOfUser(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("interestList", interestService.getAllInterestOfUser(limit,offset-1));
		map.put("count", interestService.countAllInterestOfUser());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	/**
	 * @description Method for all bids of a User
	 * @param limit
	 * @param offset
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllInterestOfAllProductsOfaUser",method=RequestMethod.GET)
	public Map<String,Object> getAllInterestOfAllProductsOfaUser(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("interestList", interestService.getAllInterestOfAllProductsOfaUser(limit,offset-1));
		map.put("count", interestService.countAllInterestOfAllProductsOfaUser());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
}
