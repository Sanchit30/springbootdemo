package com.anaadih.aclassdeal.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.ClosedDeal;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Repository.ClosedDealRepository;
import com.anaadih.aclassdeal.Repository.ProductRepository;
import com.anaadih.aclassdeal.Repository.UserRepository;
import com.anaadih.aclassdeal.util.CustomException;

@Service
public class ClosedDealServiceImpl implements ClosedDealService {

	
	@Autowired
	private ClosedDealRepository closedDealRepository;

	@Autowired 
	private  ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	@Override
	public List<ClosedDeal> getAllClosedDeals(int limit, int offset) {
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"dealDate"));
		Page<ClosedDeal> page= closedDealRepository.findAll(pg);
		return page.getContent(); 
		
	}

	@Override
	public long getCountOfClosedDeal() {
		return closedDealRepository.count();
	}

	@Override
	public Object saveClosedDeal(ClosedDeal closedDeal) {
		if(closedDeal.getProductModel()!=null) {
			Optional<ProductModel> productOpt= productRepository.findById(closedDeal.getProductModel().getProdId());
			
			if(productOpt.isPresent()) {
				ProductModel product=productOpt.get();
				//Set the status here to CLOSED
				product.setStatus("SOLD");
				product =productRepository.save(product);
				closedDeal.setProductModel(product);
				String userId=SecurityContextHolder.getContext().getAuthentication().getName();
				Optional<User> user=userRepository.findByEmail(userId);
				if(user.isPresent()) {
					closedDeal.setSeller(user.get());
					
					closedDealRepository.save(closedDeal);
					
				}
						
				
			}
			
	}
		return closedDeal;

}
	}

//closed deal,product,category
