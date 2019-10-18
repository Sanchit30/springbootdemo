package com.anaadih.aclassdeal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadih.aclassdeal.Model.SubCategoryModel;

@Repository
public interface SubCategoryRepository  extends PagingAndSortingRepository<SubCategoryModel, Integer>{

	List<SubCategoryModel> findByParentCategoryCatCode(Integer catCode);
	
			
	@Query( nativeQuery=true,value="select  c.cat_code,c.cat_name, count(p.sub_category_cat_code) as Total from sub_category_model c left join product_model p on c.cat_code = p.sub_category_cat_code  where p.status = 'APPROVED' or p.status ='BIDDING' group by c.cat_code")	
	List<?> findByjoinQuery();

}
