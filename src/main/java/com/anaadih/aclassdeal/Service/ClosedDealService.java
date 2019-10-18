package com.anaadih.aclassdeal.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.ClosedDeal;

@Service
public interface ClosedDealService {

	List<ClosedDeal> getAllClosedDeals(int limit, int i);

	long getCountOfClosedDeal();

	Object saveClosedDeal(ClosedDeal closedDeal);

}
