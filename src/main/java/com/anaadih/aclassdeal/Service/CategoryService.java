package com.anaadih.aclassdeal.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.CategoryModel;

@Service
public interface CategoryService {

	CategoryModel saveCategory(CategoryModel category);

	List<CategoryModel> getAllCategories(int limit, int offset);

	HashMap<String,Object> getAllCategorywithCount(int limit, int offset);

	long getCountOfAllCategories();

	LinkedHashMap<String,Object> getAllCategorieswithSubcategory();

	void deleteCategory(int catCode);

	

}
