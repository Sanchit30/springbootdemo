package com.anaadih.aclassdeal.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.SubCategoryModel;
import com.anaadih.aclassdeal.Repository.CategoryRepository;
import com.anaadih.aclassdeal.Repository.SubCategoryRepository;
import com.anaadih.aclassdeal.util.CustomException;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	
	private SubCategoryRepository subCategoryRepository;
	@Override
	public CategoryModel saveCategory(CategoryModel category) {
		if(categoryRepository.existsById(category.getCatCode())) {
			System.out.println("Duplicate Record");
		}
		else {
			return categoryRepository.save(category);
		}
		//it iwill be changed
		return null;
	}
	
	
	@Override
	public List<CategoryModel> getAllCategories(int limit, int offset) {
		Page<CategoryModel> page=categoryRepository.findAll(new PageRequest(offset, limit, new Sort(Direction.ASC,"modifiedDate")));
		System.out.println(page.getNumber());
		return page.getContent();
	}

	@Override
	public HashMap<String,Object> getAllCategorywithCount(int limit, int offset) {
		HashMap<String,Object> map = new HashMap();
		List<?> catCount = categoryRepository.findByjoinQuery();
//		List<?> allcategories = (List<?>) categoryRepository.findAll();
//		System.out.println("COUNT"+catCount);
//		map.put("allCategories", allcategories);
		map.put("catCount", catCount);
		return map;
	}


	@Override
	public long getCountOfAllCategories() {
		return categoryRepository.count();
	}


	@Override
	public LinkedHashMap<String, Object> getAllCategorieswithSubcategory() {
		LinkedHashMap<String, Object> categoryMap= new LinkedHashMap<>();
		List<CategoryModel> categoryList=(List<CategoryModel>) categoryRepository.findAll();
		for(CategoryModel category : categoryList )
		{
			String catCodeAndName=category.getCatCode()+","+category.getCatName();
			List<SubCategoryModel> subCategoryList= new ArrayList<SubCategoryModel>();
			categoryMap.put(catCodeAndName, getSubcategoryMapForThisCategory(subCategoryList,category));
			
			
			//Create a  New Map For SubCategorry here
			
			//categoryMap.put("SUBCAT-"+category.getCatCode(),getSubcategoryMapForThisCategory(subCategoryList,category));
			
		}
		return categoryMap;
	}


	private LinkedHashMap<Integer,Object> getSubcategoryMapForThisCategory(List<SubCategoryModel> subCategoryList, CategoryModel category) {
		LinkedHashMap<Integer,Object> subCategoryMap= new LinkedHashMap<>();
		List<SubCategoryModel>subCategryList=subCategoryRepository.findByParentCategoryCatCode(category.getCatCode());
		
		for(SubCategoryModel subCategry:subCategryList)
		{
			subCategoryMap.put(subCategry.getCatCode(), subCategry.getCatName());
		}
		return subCategoryMap;
	}


	@Override
	public void deleteCategory(int catCode) {
		Optional<CategoryModel> catOpt =categoryRepository.findById(catCode);
		if(catOpt.isPresent())
		{
			CategoryModel category=catOpt.get();
			if(!category.isInUse()) {
				//DELETE ALLSUBCATEGORY For now
				deleteSubCategory(category);
				categoryRepository.delete(category);
			}
			else {
				try {
					throw new CustomException("Category Already in use ");
				} catch (CustomException e) {
					
					e.printStackTrace();
				}
			}
		}
	}


	private void deleteSubCategory(CategoryModel category) {
		List<SubCategoryModel> subCategoryList=subCategoryRepository.findByParentCategoryCatCode(category.getCatCode());
		subCategoryRepository.deleteAll(subCategoryList);
	}


	
	
}
