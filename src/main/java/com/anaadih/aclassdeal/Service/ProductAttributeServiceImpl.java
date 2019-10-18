package com.anaadih.aclassdeal.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.AttributeModel;
import com.anaadih.aclassdeal.Model.ProductattributeMapping;
import com.anaadih.aclassdeal.Repository.AttributeRepository;
import com.anaadih.aclassdeal.Repository.ProductAttributeRepository;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

	@Autowired
	private ProductAttributeRepository productAttributeRepository;
	
	@Autowired
	private AttributeRepository attributeRepository;
	
	@Override
	public Object saveMapping(int prodId,  HashMap<String, String> mappings,String userId) {
		for(String mapping: mappings.keySet()) {
			ProductattributeMapping model  = new ProductattributeMapping();
			model.setAttributeId(Integer.parseInt(mapping));
			//Setting attribute name also so that we do not have to make other query to get attribute name
			Optional<AttributeModel> attributeModel=attributeRepository.findById(Integer.parseInt(mapping));
			if(attributeModel.isPresent()) {
				model.setAttributeName(attributeModel.get().getAttributeName());
			}
			else {
				model.setAttributeName("");
			}
			
			model.setAttributeValue(mappings.get(mapping));
			model.setProdId(prodId);
			model.setUserId(userId);
			productAttributeRepository.save(model);
		}
		return null;
	}
	@Override
	public List<ProductattributeMapping> getMapping(String prodId, String userId) {
		return productAttributeRepository.findByProdIdAndUserId(prodId,userId);
		 
	}
	@Override
	public List<ProductattributeMapping> getAllAttributeOfProduct(Integer prodId) {
		return productAttributeRepository.findByProdId(prodId);
	}

}
