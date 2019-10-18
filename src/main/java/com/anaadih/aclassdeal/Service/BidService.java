package com.anaadih.aclassdeal.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.Bid;
import com.anaadih.aclassdeal.util.CustomException;

@Service
public interface BidService {

	List<Bid> getAllBidsOfProduct(int limit, int i, int productId);

	Bid saveBidForProduct(Bid bid) throws CustomException;

	List<Bid> getAllBidsOfUser(int limit, int offset);

	List<Bid> getAllBidsOfAllProductsOfaUser(int limit, int i);

	long countAllBidsOfUser();

	long countAllBidsOfAllProductsOfaUser();

}
