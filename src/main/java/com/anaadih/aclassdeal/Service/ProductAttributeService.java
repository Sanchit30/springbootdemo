package com.anaadih.aclassdeal.Service;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.ProductattributeMapping;

@Service
public interface ProductAttributeService {

	Object saveMapping(int prodId, HashMap<String, String> mappings,String userId);

	List<ProductattributeMapping> getMapping(String prodId, String userId);

 	List<ProductattributeMapping> getAllAttributeOfProduct(Integer prodId);


}
