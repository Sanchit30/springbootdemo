package com.anaadih.aclassdeal.Service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.anaadih.aclassdeal.Model.IntrestModel;
import com.anaadih.aclassdeal.util.CustomException;

@Service
public interface InterestService {

	List<IntrestModel> getAllInterestOfProduct(int limit, int i, int productId);

	IntrestModel saveInterestForProduct(IntrestModel bid) throws CustomException;

	List<IntrestModel> getAllInterestOfUser(int limit, int offset);

	List<IntrestModel> getAllInterestOfAllProductsOfaUser(int limit, int i);

	long countAllInterestOfUser();

	long countAllInterestOfAllProductsOfaUser();

}
