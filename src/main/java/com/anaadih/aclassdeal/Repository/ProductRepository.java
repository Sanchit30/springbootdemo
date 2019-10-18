package com.anaadih.aclassdeal.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.SubCategoryModel;
import com.anaadih.aclassdeal.Model.User;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductModel,Integer>{

	@Transactional
	@Modifying
	@Query("update ProductModel set status=?2 where prodId in(?1)")
	void approveProduct(List<Integer> ids,String status);

	Page<ProductModel> findByStatus(String status, Pageable pg);
	
	Page<ProductModel> findByUserId(User user, Pageable pg);

	Page<ProductModel> findByStatusIn(List<String> statusList, Pageable pg);

	Page<ProductModel> findByCategory(CategoryModel categoryModel, Pageable pg);

	long countByStatus(String status);

	Page<ProductModel> findBySubCategory(SubCategoryModel subCategoryModel, Pageable pg);

	Page<ProductModel> findByCategoryAndStatusIn(CategoryModel categoryModel, List<String> statusList, Pageable pg);

	Page<ProductModel> findBySubCategoryAndStatusIn(SubCategoryModel subCategoryModel, List<String> statusList, Pageable pg);

	long countByStatusIn(List<String> statusListNew);

	long countByCategoryAndStatusIn(CategoryModel categoryModel, List<String> statusList);

	long countBySubCategoryAndStatusIn(SubCategoryModel subCategoryModel, List<String> statusList);

	List<ProductModel> findByStatusIn(List<String> statusList);

	Page<ProductModel> findByUserIdAndStatusIn(User user, List<String> statusList, Pageable pg);

	List<ProductModel> findByProdIdIn(List<Integer> ids);

}
