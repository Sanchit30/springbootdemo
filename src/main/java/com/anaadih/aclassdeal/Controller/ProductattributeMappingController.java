package com.anaadih.aclassdeal.Controller;

import java.util.HashMap;
import java.util.List;
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

import com.anaadih.aclassdeal.Model.ReportedAdsModel;
import com.anaadih.aclassdeal.Service.ProductAttributeService;
import com.anaadih.aclassdeal.Service.productService;
import com.anaadih.aclassdeal.util.CommonResponseSender;

/**
 * 
 * @author Paras
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class ProductattributeMappingController {
	
	@Autowired 
	private ProductAttributeService productattrService;
	
	@Autowired
	private productService productService; 

	@RequestMapping(value="/addAttributeMapping",method=RequestMethod.POST)
	public Map<String,Object> addAttributeMapping(@RequestParam (value="prodId") String prodId,@RequestBody @Valid HashMap<String,String> mappings,Errors errors,HttpServletRequest request,HttpServletResponse response)
	{
		String userId="";
		final HashMap<String, Object> map = new HashMap<>();
		if(errors.hasErrors())
		{
			return (Map<String, Object>) map.put("error", "Something went wrong");
		}
		map.put("mappings", productattrService.saveMapping(Integer.parseInt(prodId),mappings,userId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	@RequestMapping(value="/getAllAttributeOfProduct",method=RequestMethod.GET)
	public Map<String,Object> getAllAttributeOfProduct(@RequestParam (value="prodId") Integer prodId,HttpServletRequest request,HttpServletResponse response)
	{
		String userId="";
		final HashMap<String, Object> map = new HashMap<>();

		//map.put("attributes", productattrService.getAllAttributeOfProduct(prodId));
		return CommonResponseSender.createdSuccessResponse(map, response);
	}
	
	
}
