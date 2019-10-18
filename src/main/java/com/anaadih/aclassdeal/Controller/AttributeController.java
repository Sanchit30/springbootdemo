package com.anaadih.aclassdeal.Controller;

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

import com.anaadih.aclassdeal.Model.AttributeModel;
import com.anaadih.aclassdeal.Model.SubCategoryModel;
import com.anaadih.aclassdeal.Service.AttributeService;
import com.anaadih.aclassdeal.util.CommonResponseSender;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class AttributeController {
	
	@Autowired
	private AttributeService attributeService; 
	/**
	 * 
	 * @param subCategory
	 * @param errors
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/saveAttribute",method=RequestMethod.POST)
	public Map<String,Object> saveAttribute(@RequestBody @Valid AttributeModel attributeModel,Errors errors,HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		if(errors.hasErrors())
		{
			return (Map<String, Object>) map.put("error", "Something went wrong");
		}

		map.put("attribute", attributeService.saveAttribute(attributeModel));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value="/getAllAttributeOfCategoryCode",method=RequestMethod.GET)
	public Map<String,Object> getAllAttributeOfCategoryCode(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			@RequestParam(value="catCode") int catCode,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("attributeList", attributeService.getAllAttributeOfCategoryCode(limit,offset,catCode));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	

}
