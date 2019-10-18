package com.anaadih.aclassdeal.Controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Service.AttributeService;
import com.anaadih.aclassdeal.Service.CategoryService;
import com.anaadih.aclassdeal.Service.FileUploadService;
import com.anaadih.aclassdeal.Service.SubCategoryService;
import com.anaadih.aclassdeal.util.CommonResponseSender;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class CategoryController {

	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SubCategoryService subCategoryService; 
	
	@Autowired
	private FileUploadService fileUploadService;

	@RequestMapping(value="/saveCategory",method=RequestMethod.POST)
	public Map<String,Object> saveCategory(@RequestParam(value="file",required=false) MultipartFile[] files,@RequestParam(value="categoryString",required=false) String categoryString,HttpServletRequest request,HttpServletResponse response) throws JsonParseException, JsonMappingException, IOException
	{
		final HashMap<String, Object> map = new HashMap<>();	
		ObjectMapper objMapper= new ObjectMapper();
		TypeReference<CategoryModel> mapType= new TypeReference<CategoryModel>() {
		};
		CategoryModel category= objMapper.readValue(categoryString, mapType);
		System.out.println("CATEGORY IS"+category);
		category=categoryService.saveCategory(category);
		if(category!=null) {
		fileUploadService.saveIconofCategory(files,category);
		}
		map.put("category", category);
		return CommonResponseSender.createdSuccessResponse(map, response);
	}

	

	@RequestMapping(value="/deleteCategory",method=RequestMethod.DELETE)
	public Map<String,Object> deleteCategory(@RequestParam(value="catCode") int  catCode,HttpServletRequest request,HttpServletResponse response) 
	{
		final HashMap<String, Object> map = new HashMap<>();	
		
		categoryService.deleteCategory(catCode);
		return CommonResponseSender.recordDeleteSuccessResponse(map, response);
	}

		
	
	
	@RequestMapping(value="/getAllCategories",method=RequestMethod.GET)
	public Map<String,Object> getAllCategories(@RequestParam(value="limit")int limit,
			@RequestParam(value="offset")int offset,
			HttpServletRequest request,HttpServletResponse response)
	{
		final HashMap<String, Object> map = new HashMap<>();
		if(offset<=0) {
			offset=1;
		}
		//First Page is 0th index
		offset=offset-1;
		map.put("categoryList", categoryService.getAllCategories(limit,offset));
		map.put("count",categoryService.getCountOfAllCategories());
		return CommonResponseSender.createdSuccessResponse(map, response);
		
	}
	
	
	

	

	
	

	
	/**
	 * service for dashboard for all category count .....
	 * 
	 */
		/**
		 * 
		 * @param limit
		 * @param offset
		 * @param catCode
		 * @param request
		 * @param response
		 * @return
		 */
		
		@RequestMapping(value="/getAllCategorieswithCount",method=RequestMethod.GET)
		public Map<String,Object> getAllCategorieswithCount(@RequestParam(value="limit")int limit,
				@RequestParam(value="offset")int offset,
				HttpServletRequest request,HttpServletResponse response){
			final Map<String, Object> map = new HashMap<>();
			map.put("subCategoryList", categoryService.getAllCategorywithCount(limit,offset));
			return CommonResponseSender.createdSuccessResponse(map, response);
			
		}
	
		//HERE DIRECT MAP RETURN
		//catcode=>catName
		//Subcategory=>Map (code and description)
		@RequestMapping(value="/getAllCategorieswithSubcategory",method=RequestMethod.GET)
		public LinkedHashMap<String,Object> getAllCategorieswithSubcategory(
				HttpServletRequest request,HttpServletResponse response){
			final LinkedHashMap<String, Object> map = new LinkedHashMap<>();
			map.put("categoryWithSubcategory", categoryService.getAllCategorieswithSubcategory());
	
			LinkedHashMap<String, Object> newMap= new LinkedHashMap<>();
			newMap.put("data", map);
			return newMap;
			
			
			
		}
	
	
}
