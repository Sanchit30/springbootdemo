package com.anaadih.aclassdeal.Service;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.SubCategoryModel;

@Service
public interface SubCategoryService {

	SubCategoryModel saveSubCategory(@Valid SubCategoryModel subCategory);

	List<SubCategoryModel> getAllSubCategoryOfCategory(int limit, int offset, int catCode);

	HashMap<String,Object> getAllSubCategorieswithCount(int limit, int offset);

	void deleteSubCategory(int catCode);

}
