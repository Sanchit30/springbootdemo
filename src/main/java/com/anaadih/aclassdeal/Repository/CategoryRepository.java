package com.anaadih.aclassdeal.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadih.aclassdeal.Model.CategoryModel;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<CategoryModel,Integer> {

	//PREVIOUS QUERY
	//@Query( nativeQuery=true,value="select  c.cat_code,c.cat_name, count(p.category_cat_code) as Total,c.cat_icon from category_model c left join product_model p on c.cat_code = p.category_cat_code   group by c.cat_code")
	//Get only approved and biddng product count here
			
	@Query( nativeQuery=true,value="select  c.cat_code,c.cat_name, count(p.category_cat_code) as Total,c.cat_icon from category_model c left join product_model p on c.cat_code = p.category_cat_code   where p.status = 'APPROVED' or p.status ='BIDDING'  group by c.cat_code")
	List<?> findByjoinQuery();
}
//