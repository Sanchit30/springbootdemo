package com.anaadih.aclassdeal.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.SubCategoryModel;
import com.anaadih.aclassdeal.Repository.CategoryRepository;
import com.anaadih.aclassdeal.Repository.SubCategoryRepository;
import com.anaadih.aclassdeal.util.CustomException;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@Autowired 
	private CategoryRepository categoryRepository;
	
	@Override
	public SubCategoryModel saveSubCategory(SubCategoryModel subCategory) {
		System.out.println("SUB CATEGORY IS"+subCategory);
//		if(subCategoryRepository.existsById(subCategory.getCatCode())) {
//			System.out.println("Duplicate Record");
//		}
//		else {
		
		//Now a category will be used obly if product is added
		Optional<CategoryModel> catOpt=categoryRepository.findById(subCategory.getParentCategory().getCatCode());
//		if(catOpt.isPresent())
//		{
//			CategoryModel catModel=catOpt.get();
//			if(!catModel.isInUse())
//			{
//				catModel.setInUse(true);
//				categoryRepository.save(catModel);
//			}
//		}
			subCategoryRepository.save(subCategory);
	//	}
		//it iwill be changed
		return new SubCategoryModel();
	}


	@Override
	public List<SubCategoryModel> getAllSubCategoryOfCategory(int limit, int offset, int catCode) {
		List<SubCategoryModel> subCategoryList=new ArrayList<>();
		//pagination will be applied later here
		subCategoryList=subCategoryRepository.findByParentCategoryCatCode(catCode);
		
		return subCategoryList;
		
	}


	@Override
	public HashMap<String, Object> getAllSubCategorieswithCount(int limit, int offset) {
		HashMap<String,Object> map = new HashMap();
		List<?> subCatCount = subCategoryRepository.findByjoinQuery();

		map.put("subCatCount", subCatCount);
		return map;
	}


	@Override
	public void deleteSubCategory(int catCode) {
		Optional<SubCategoryModel> subCatOpt=subCategoryRepository.findById(catCode);
		if(subCatOpt.isPresent())
		{
			SubCategoryModel subCate=subCatOpt.get();
			if(!subCate.isInUse())
			{
				subCategoryRepository.delete(subCate);
			}
			else
			{
				try {
					throw new CustomException("Sub Category already in use");
				} catch (CustomException e) {
					e.printStackTrace();
				}
			}
				
		}
	}

}
