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

import com.anaadih.aclassdeal.Model.Bid;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Repository.BidRepository;
import com.anaadih.aclassdeal.Repository.ProductRepository;
import com.anaadih.aclassdeal.Repository.UserRepository;
import com.anaadih.aclassdeal.util.CustomException;

@Service
public class BidServiceImpl implements BidService{

	@Autowired 
	private  ProductRepository productRepository;
	
	@Autowired
	private BidRepository bidRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public List<Bid> getAllBidsOfProduct(int limit, int offset, int productId) {
		List<Bid> bidList= new ArrayList<Bid>();
		Optional<ProductModel> productOpt= productRepository.findById(productId);
		if(productOpt.isPresent()) {
			ProductModel product=productOpt.get();
			bidList=bidRepository.findByProduct(product);
			
		}
		 return bidList;
	}
	@Override
	public Bid saveBidForProduct(Bid bid) throws CustomException {

		if(bid.getProduct()!=null) {
			Optional<ProductModel> productOpt= productRepository.findById(bid.getProduct().getProdId());
			
			if(productOpt.isPresent()) {
				ProductModel product=productOpt.get();
				//Set the status here to bidding
				product.setStatus("BIDDING");
				product =productRepository.save(product);
				bid.setProduct(product);
				//String userId=SecurityContextHolder.getContext().getAuthentication().getName();
				String userId=SecurityContextHolder.getContext().getAuthentication().getName();
				//Optional<User> user=userRepository.findByUsername(userId);
				Optional<User> user=userRepository.findByEmail(userId);
				if(user.isPresent()) {
					bid.setUser(user.get());
					//Check for previous Bids
					boolean alreadyAddedBid = bidRepository.existsByProductAndUser(product,bid.getUser());
					
					if(alreadyAddedBid)
					{
						throw new CustomException("Sorry,You have already placed a bid");
					}
					else {
					return bidRepository.save(bid);
					}
					
				}
						
				
			}	
		}
		
		return new Bid();
	}
	//All bids placed by that user
	@Override
	public List<Bid> getAllBidsOfUser(int limit, int offset) {
		List<Bid> bidList= new ArrayList<Bid>();
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"bidDate"));
		User user =commonService.getLoggedInUser();
		if(user!=null) {
			bidList=bidRepository.findByUser(user,pg);
			
		}
		 return bidList;
	}
	//all bids placed on product of a user
	@Override
	public List<Bid> getAllBidsOfAllProductsOfaUser(int limit, int offset) {
		List<Bid> bidList= new ArrayList<Bid>();
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"bidDate"));
		User user =commonService.getLoggedInUser();
		//All bids on products added by that user
		if(user!=null) {
			bidList=bidRepository.findByProductUserId(user,pg);
			
		}
		 return bidList;
	}
	@Override
	public long countAllBidsOfUser() {
		User user =commonService.getLoggedInUser();
		if(user!=null) {
			return bidRepository.countByUser(user);
			
		}
		return 0;
	}
	@Override
	public long countAllBidsOfAllProductsOfaUser() {
		User user =commonService.getLoggedInUser();
		//All bids on products added by that user
		if(user!=null) {
			return  bidRepository.countByProductUserId(user);
			
		}
		return 0;
	}

}
