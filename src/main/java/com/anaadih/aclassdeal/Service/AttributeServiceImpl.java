package com.anaadih.aclassdeal.Service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.AttributeModel;
import com.anaadih.aclassdeal.Model.SubCategoryModel;
import com.anaadih.aclassdeal.Repository.AttributeRepository;

@Service
public class AttributeServiceImpl implements AttributeService {

	@Autowired
	private AttributeRepository attributeRepository;
 	@Override
	public Object saveAttribute(@Valid AttributeModel attributeModel) {
		if(attributeRepository.existsById(attributeModel.getAttributeId())) {
			System.out.println("Duplicate Record");
		}
		else {
			attributeRepository.save(attributeModel);
		}
		//it iwill be changed
		return new AttributeModel();
	}
	@Override
	public List<AttributeModel> getAllAttributeOfCategoryCode(int limit, int offset, int catCode) {
		List<AttributeModel> attributeList=new ArrayList<>();
		//pagination will be applied later here
		attributeList=attributeRepository.findByParentCategoryCatCode(catCode);
		
		return attributeList;
	}

}
