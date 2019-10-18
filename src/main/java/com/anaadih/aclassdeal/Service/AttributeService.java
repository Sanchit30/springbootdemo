package com.anaadih.aclassdeal.Service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.AttributeModel;

@Service
public interface AttributeService {

	Object saveAttribute(@Valid AttributeModel attributeModel);

	List<AttributeModel> getAllAttributeOfCategoryCode(int limit, int offset, int catCode);

}
