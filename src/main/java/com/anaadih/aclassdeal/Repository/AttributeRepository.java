package com.anaadih.aclassdeal.Repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.anaadih.aclassdeal.Model.AttributeModel;
@Repository
public interface AttributeRepository extends PagingAndSortingRepository<AttributeModel, Integer>{

	List<AttributeModel> findByParentCategoryCatCode(int catCode);

	
}
