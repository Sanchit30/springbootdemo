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

import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.SubCategoryModel;
import com.anaadih.aclassdeal.Service.AttributeService;
import com.anaadih.aclassdeal.Service.CategoryService;
import com.anaadih.aclassdeal.Service.SubCategoryService;
import com.anaadih.aclassdeal.util.CommonResponseSender;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class SubCategoryController {

	
	@Autowired
	private SubCategoryService subCategoryService;
	
	
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
	@RequestMapping(value="/saveSubCategory",method=RequestMethod.POST)
	public Map<String,Object> saveSubCategory(@RequestBody @Valid SubCategoryModel subCategory,Errors errors,HttpServletRequest request,HttpServletResponse response)
	{
		System.out.println("Inside save sub category");
		final HashMap<String, Object> map = new HashMap<>();
		if(errors.hasErrors())
		{
			return (Map<String, Object>) map.put("error", "Something went wrong");
		}

		map.put("subCategory", subCategoryService.saveSubCategory(subCategory));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	/**
	 * 
	 * @param limit
	 * @param offset
	 * @param catCode
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/getAllSubCategoryOfCategoryCode",method=RequestMethod.GET)
	public Map<String,Object> getAllSubCategoryOfCategory(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			@RequestParam(value="catCode") int catCode,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("subCategoryList", subCategoryService.getAllSubCategoryOfCategory(limit,offset,catCode));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	@RequestMapping(value="/getAllSubCategorieswithCount",method=RequestMethod.GET)
	public Map<String,Object> getAllSubCategorieswithCount(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response){
		final HashMap<String, Object> map = new HashMap<>();
		map.put("subCategoryData", subCategoryService.getAllSubCategorieswithCount(limit,offset));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	@RequestMapping(value="/deleteSubCategory",method=RequestMethod.DELETE)
	public Map<String,Object> deleteSubCategory(@RequestParam(value="catCode") int  catCode,HttpServletRequest request,HttpServletResponse response) 
	{
		final HashMap<String, Object> map = new HashMap<>();	
		
		subCategoryService.deleteSubCategory(catCode);
		return CommonResponseSender.recordDeleteSuccessResponse(map, response);
	}
	
	
	
	@RequestMapping(value="/getAllAttributeOfSubCategoryCode",method=RequestMethod.GET)
	public Map<String,Object> getAllSubCategoryAndAttributeOfCategoryCode(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			@RequestParam(value="catCode") int catCode,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		map.put("attributeList", attributeService.getAllAttributeOfCategoryCode(limit,offset,catCode));
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
}
