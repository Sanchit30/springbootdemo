package com.anaadih.aclassdeal.Repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.ProductattributeMapping;

@Repository
public interface ProductAttributeRepository extends PagingAndSortingRepository<ProductattributeMapping,Long>{

	List<ProductattributeMapping> findByProdIdAndUserId(String prodId, String userId);
	
	List<ProductattributeMapping> findByProdId(Integer prodId);

}
