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

import com.anaadih.aclassdeal.Model.Bid;
import com.anaadih.aclassdeal.Model.ClosedDeal;
import com.anaadih.aclassdeal.Service.ClosedDealService;
import com.anaadih.aclassdeal.util.CommonResponseSender;
import com.anaadih.aclassdeal.util.CustomException;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class ClosedDealController {

	@Autowired
	private ClosedDealService closedDealService;
	
	/**
	 * @description Mapping for all bids on a particular product
	 * @param limit
	 * @param offset
	 * @param productId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllClosedDeals",method=RequestMethod.GET)
	public Map<String,Object> getAllClosedDeals(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("closedDealList", closedDealService.getAllClosedDeals(limit,offset-1));
		map.put("count",closedDealService.getCountOfClosedDeal());
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
	@RequestMapping(value="/saveClosedDeal",method=RequestMethod.POST)
	public Map<String,Object> saveBidForProduct(@RequestBody  ClosedDeal closedDeal,Errors errors,HttpServletRequest request,HttpServletResponse response) throws CustomException
	{
		final HashMap<String, Object> map = new HashMap<>();
		if(errors.hasErrors())
		{
			return (Map<String, Object>) map.put("error", "Something went wrong");
		}

		map.put("closedDeal", closedDealService.saveClosedDeal(closedDeal));
		
		//WEBSOCKET FOR SENDING NOTIFCATION TO ADMIN OF THAT PRODUCT
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
}
