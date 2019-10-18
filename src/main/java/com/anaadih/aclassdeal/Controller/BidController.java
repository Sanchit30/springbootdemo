package com.anaadih.aclassdeal.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.anaadih.aclassdeal.Model.Bid;
import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Service.BidService;
import com.anaadih.aclassdeal.util.CommonResponseSender;
import com.anaadih.aclassdeal.util.CustomException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class BidController {

	@Autowired
	private BidService bidService;
	
	
	
	/**
	 * @description Mapping for all bids on a particular product
	 * @param limit
	 * @param offset
	 * @param productId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllBidsOfProduct",method=RequestMethod.GET)
	public Map<String,Object> getAllProducts(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,@RequestParam(value="productId") int productId,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("bidList", bidService.getAllBidsOfProduct(limit,offset-1,productId));
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
	@RequestMapping(value="/saveBidForProduct",method=RequestMethod.POST)
	public Map<String,Object> saveBidForProduct(@RequestBody  Bid bid,Errors errors,HttpServletRequest request,HttpServletResponse response) throws CustomException
	{
		final HashMap<String, Object> map = new HashMap<>();
		if(errors.hasErrors())
		{
			return (Map<String, Object>) map.put("error", "Something went wrong");
		}

		map.put("bid", bidService.saveBidForProduct(bid));
		
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
	@RequestMapping(value="/getAllBidsOfUser",method=RequestMethod.GET)
	public Map<String,Object> getAllBidsOfUser(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("bidList", bidService.getAllBidsOfUser(limit,offset-1));
		map.put("count", bidService.countAllBidsOfUser());
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
	@RequestMapping(value="/getAllBidsOfAllProductsOfaUser",method=RequestMethod.GET)
	public Map<String,Object> getAllBidsOfAllProductsOfaUser(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("bidList", bidService.getAllBidsOfAllProductsOfaUser(limit,offset-1));
		map.put("count", bidService.countAllBidsOfAllProductsOfaUser());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
}
