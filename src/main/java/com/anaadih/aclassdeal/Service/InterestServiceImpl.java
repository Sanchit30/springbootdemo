package com.anaadih.aclassdeal.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.IntrestModel;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Repository.InterestRepository;
import com.anaadih.aclassdeal.Repository.ProductRepository;
import com.anaadih.aclassdeal.Repository.UserRepository;
import com.anaadih.aclassdeal.util.CustomException;

@Service
public class InterestServiceImpl implements InterestService {

	
	@Autowired 
	private  ProductRepository productRepository;
	
	@Autowired
	private InterestRepository interestRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public List<IntrestModel> getAllInterestOfProduct(int limit, int offset, int productId) {
		List<IntrestModel> interestList= new ArrayList<IntrestModel>();
		Optional<ProductModel> productOpt= productRepository.findById(productId);
		if(productOpt.isPresent()) {
			ProductModel product=productOpt.get();
			interestList=interestRepository.findByProduct(product);
			
		}
		 return interestList;
	}
	@Override
	public IntrestModel saveInterestForProduct(IntrestModel interest) throws CustomException {

		if(interest.getProduct()!=null) {
			Optional<ProductModel> productOpt= productRepository.findById(interest.getProduct().getProdId());
			
			if(productOpt.isPresent()) {
				ProductModel product=productOpt.get();
				
//				//Set the status here to interestding
//				product.setStatus("BIDDING");
//				product =productRepository.save(product);
				
				interest.setProduct(product);
				//String userId=SecurityContextHolder.getContext().getAuthentication().getName();
				String userId=SecurityContextHolder.getContext().getAuthentication().getName();
				//Optional<User> user=userRepository.findByUsername(userId);
				Optional<User> user=userRepository.findByEmail(userId);
				if(user.isPresent()) {
					interest.setUser(user.get());
					//Check for previous Interest
					boolean alreadyAddedBid = interestRepository.existsByProductAndUser(product,interest.getUser());
					
					if(alreadyAddedBid)
					{
						throw new CustomException("Sorry,You have already showed interest on this ad!");
					}
					else {
						
						if(interest.isProfileNumber())
						{
							interest.setPhoneNumber(user.get().getPhoneNumber());
						}
						
					return interestRepository.save(interest);
					}
					
				}
						
				
			}	
		}
		
		return new IntrestModel();
	}
	//All interests placed by that user
	@Override
	public List<IntrestModel> getAllInterestOfUser(int limit, int offset) {
		List<IntrestModel> interestList= new ArrayList<IntrestModel>();
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"intrestDate"));
		User user =commonService.getLoggedInUser();
		if(user!=null) {
			interestList=interestRepository.findByUser(user,pg);
			
		}
		 return interestList;
	}
	//all interests placed on product of a user
	@Override
	public List<IntrestModel> getAllInterestOfAllProductsOfaUser(int limit, int offset) {
		List<IntrestModel> interestList= new ArrayList<IntrestModel>();
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"intrestDate"));
		User user =commonService.getLoggedInUser();
		//All interests on products added by that user
		if(user!=null) {
			interestList=interestRepository.findByProductUserId(user,pg);
			
		}
		 return interestList;
	}
	@Override
	public long countAllInterestOfUser() {
		User user =commonService.getLoggedInUser();
		if(user!=null) {
			return interestRepository.countByUser(user);
			
		}
		return 0;
	}
	@Override
	public long countAllInterestOfAllProductsOfaUser() {
		User user =commonService.getLoggedInUser();
		//All interests on products added by that user
		if(user!=null) {
			return  interestRepository.countByProductUserId(user);
			
		}
		return 0;
	}

	
}
