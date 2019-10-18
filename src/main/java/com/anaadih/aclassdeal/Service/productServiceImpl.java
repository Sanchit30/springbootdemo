package com.anaadih.aclassdeal.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.CategoryModel;
import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.ProductattributeMapping;
import com.anaadih.aclassdeal.Model.SearchModel;
import com.anaadih.aclassdeal.Model.SubCategoryModel;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Repository.CategoryRepository;
import com.anaadih.aclassdeal.Repository.ProductRepository;
import com.anaadih.aclassdeal.Repository.SubCategoryRepository;
import com.anaadih.aclassdeal.Repository.UserRepository;


@Service
//@EnableScheduling

public class productServiceImpl implements productService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private ProductAttributeService productAttributeService;
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SubCategoryRepository subCategoryRepository;
	
	@Autowired
	private BidService bidService;
	
	@Autowired
	private InterestService  interestService; 
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;
	
	private static final List<String> STATUS_LIST = Arrays.asList("APPROVED","BIDDING");
	private static final List<String> STATUS_LIST_NEW = Arrays.asList("NEW");
	private static final List<String> STATUS_LIST_REJECTED = Arrays.asList("DELETED");
	private static final List<String> STATUS_LIST_EXPIRED = Arrays.asList("SOLD","NOTSOLD");
	
	
	
	@Override
	public ProductModel saveProduct(ProductModel product) {
		 return productRepository.save(product);
	}

	//USER WILL ONLY ABLE TO SEE APPROVED PRODUCTS
	@Override
	public List<ProductModel> getAllProducts(int limit, int offset) {
		//Sending Pageable Interface instead of PageRequest due to this error
//		At least 2 parameter(s) provided but only 1 parameter(s) present in query.x`x`
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"modifiedDate"));
		Page<ProductModel> page= productRepository.findByStatusIn(STATUS_LIST,pg);
		return page.getContent();
	}

	@Override
	public ProductModel getProductById(int prodId) {
		Optional<ProductModel> model =  productRepository.findById(prodId);
		if(model.isPresent()) {
			
		
			return model.get();
		}
		return null;
	}
	
	@Override
	public List<ProductModel> getAllPendingProducts(int limit, int offset) {
		//Sending Pageable Interface instead of PageRequest due to this error
//		At least 2 parameter(s) provided but only 1 parameter(s) present in query.
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"modifiedDate"));
		Page<ProductModel> page= productRepository.findByStatus("New",pg);
		return page.getContent();
	}

	@Override
	public void approveProduct(List<Integer> ids,String status) {
		System.out.println("ID"+ids);
		//WEBSOCKETTO USER
		productRepository.approveProduct(ids,status);
		System.out.println("about to send notification for approve");
		List<ProductModel> productList = productRepository.findByProdIdIn(ids);
		for(ProductModel product :productList)
		{
			System.out.println("about to send notification for approve for product"+product.getProdName() +"to user"+product.getUserId().getId());
			String message=product.getUserId().getId() +"| Your ad just went live";
		simpMessagingTemplate.convertAndSend("/topic/notification", "Your ad just went live");
		}
		 // /topic/notification
	}

	@Override
	public HashMap<String, Object> getAllDetailsOfProduct(Integer prodId) {
		HashMap<String, Object> map = new HashMap<>();
		Optional<ProductModel> model =  productRepository.findById(prodId);
		//Update the view of product here here
		if(model.isPresent()) {			
			ProductModel product =model.get();
			product.setViews(product.getViews()+1);
			product=productRepository.save(product);
			map.put("product", product);			
			//Here if bid is enabled 
			if(product.isBid())
			{
			map.put("bidList", bidService.getAllBidsOfProduct(15,0,product.getProdId()));
			}
			else
			{
				map.put("interestList", interestService.getAllInterestOfProduct(15,0,product.getProdId()));
			}
			
		}
		List<ProductattributeMapping> productAttributes=productAttributeService.getAllAttributeOfProduct(prodId);
		if(productAttributes!=null && productAttributes.size()>0) {
			map.put("attributes", productAttributes);
		}
		
		
		return map;
	}
	
	@Override
	public List<ProductModel> getAllPostedAdsOfUser(int limit, int offset) {
		String userId=SecurityContextHolder.getContext().getAuthentication().getName();
	//	Optional<User> user=userRepository.findByUsername(userId);
		Optional<User> user=userRepository.findByEmail(userId);
		System.out.println("OFFSET IS"+offset);
		if(user.isPresent()) {
			Pageable pg=PageRequest.of(offset-1,limit, new Sort(Direction.ASC,"modifiedDate"));
			Page<ProductModel> page= productRepository.findByUserId(user.get(),pg);
			return page.getContent();
		}
		return null;
	}

	
	//APPROVED AND BIDDING ARE CONSIDERED AS ACTIVE ADS
	@Override
	public List<ProductModel> getAllActivePostedAdsOfUser(int limit, int offset) {
		String userId=SecurityContextHolder.getContext().getAuthentication().getName();
		//	Optional<User> user=userRepository.findByUsername(userId);
			Optional<User> user=userRepository.findByEmail(userId);
			System.out.println("OFFSET IS"+offset);
			if(user.isPresent()) {
				Pageable pg=PageRequest.of(offset-1,limit, new Sort(Direction.ASC,"modifiedDate"));
				Page<ProductModel> page= productRepository.findByUserIdAndStatusIn(user.get(),STATUS_LIST,pg);
				return page.getContent();
			}
			return null;
	}

	//SOLD ,NOT SOLD 
	@Override
	public List<ProductModel> getAllExpiredPostedAdsOfUser(int limit, int offset) {
		String userId=SecurityContextHolder.getContext().getAuthentication().getName();
		//	Optional<User> user=userRepository.findByUsername(userId);
			Optional<User> user=userRepository.findByEmail(userId);
			System.out.println("OFFSET IS"+offset);
			if(user.isPresent()) {
				Pageable pg=PageRequest.of(offset-1,limit, new Sort(Direction.ASC,"modifiedDate"));
				Page<ProductModel> page= productRepository.findByUserIdAndStatusIn(user.get(),STATUS_LIST_EXPIRED,pg);
				return page.getContent();
			}
			return null;
	}

	@Override
	public List<ProductModel> getAllRejectedPostedAdsOfUser(int limit, int offset) {
		String userId=SecurityContextHolder.getContext().getAuthentication().getName();
		//	Optional<User> user=userRepository.findByUsername(userId);
			Optional<User> user=userRepository.findByEmail(userId);
			System.out.println("OFFSET IS"+offset);
			if(user.isPresent()) {
				Pageable pg=PageRequest.of(offset-1,limit, new Sort(Direction.ASC,"modifiedDate"));
				Page<ProductModel> page= productRepository.findByUserIdAndStatusIn(user.get(),STATUS_LIST_REJECTED,pg);
				return page.getContent();
			}
			return null;
	}
	
	
	@Override
	public List<ProductModel> getAllUnapprovedPostedAdsOfUser(int limit, int offset) {
		String userId=SecurityContextHolder.getContext().getAuthentication().getName();
		//	Optional<User> user=userRepository.findByUsername(userId);
			Optional<User> user=userRepository.findByEmail(userId);
			System.out.println("OFFSET IS"+offset);
			if(user.isPresent()) {
				Pageable pg=PageRequest.of(offset-1,limit, new Sort(Direction.ASC,"modifiedDate"));
				Page<ProductModel> page= productRepository.findByUserIdAndStatusIn(user.get(),STATUS_LIST_NEW,pg);
				return page.getContent();
			}
			return null;
	}
	
	
	
	
	

	
	
	@Override
	public List<?> getSearchResult(SearchModel searchModel,int limit,int offset) {
		
		Session session = entityManager.unwrap(Session.class);
		
		/* First Create Index */
		
		 FullTextSession fullTextSession = Search.getFullTextSession(session);
		 try {
			fullTextSession.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		/* Transaction begin  get Hibernate transaction session */
		 
		
		 FullTextEntityManager fullTextEntityManager =
		    org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
		session.getTransaction().begin();
		
		
		/* Query builder Here hibernate query builds*/
		
		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
			    .buildQueryBuilder().forEntity(ProductModel.class).get();
			
		/* SEARCH AND FILTER QUERY */
		//searchModel.setSearchString("demo");
		List<Long> longDate= new ArrayList<Long>();
		String price=searchModel.getPrice();
		String dateRange = searchModel.getDateRange();
		if(!dateRange.isEmpty() && dateRange!="") {
			List<String> dateStringList = Arrays.asList(searchModel.getDateRange().split(","));
			for(String ds:dateStringList) {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
				try {
				    Date d = f.parse(ds);
				    long milliseconds = d.getTime();
				    longDate.add(milliseconds);
				} catch (ParseException e) {
				    e.printStackTrace();
				}
			}
		}
		List<Integer> intPrices= new ArrayList<Integer>();
		
		if(!price.isEmpty() && price!="") {
		List<String> prices=	Arrays.asList(searchModel.getPrice().split(","));
//		.must(searchModel.getDateRange()!=null && searchModel.getDateRange() != ""?qb.range().onField( "modifiedDate" ).from(searchModel.getDateRange().split(",")[0]).to(searchModel.getDateRange().split(",")[1]).createQuery():null)
		
		
		for(String pr:prices) {
			intPrices.add(Integer.parseInt(pr));	
		}

		}
//		         org.apache.lucene.search.Query luceneQuery = qb
//				.bool()  
//				.must(searchModel.getSearchString()!= null && searchModel.getSearchString() != ""?qb.keyword().fuzzy().withThreshold(.8f).withPrefixLength(2).onFields("prodDesc","prodName").matching(searchModel.getSearchString().substring(0, 1).toUpperCase() + searchModel.getSearchString().substring(1)).createQuery():null)
//				.must(searchModel.getLocation()!=null && searchModel.getLocation() !=""?qb.keyword().fuzzy().withThreshold(.8f).withPrefixLength(2).onFields("city").ignoreAnalyzer().matching(searchModel.getLocation().substring(0, 1).toUpperCase() + searchModel.getLocation().substring(1)).createQuery():null)
//				.must(searchModel.getPrice()!=null && searchModel.getPrice() != ""?qb.range().onField( "price" ).from(intPrices.get(0)).to(intPrices.get(1)).createQuery():null)
//				.must(searchModel.getCategory()!=null && searchModel.getCategory() != ""? qb.keyword().fuzzy().withThreshold(.8f).withPrefixLength(2).onField("category.catName").matching(searchModel.getCategory().substring(0, 1).toUpperCase() + searchModel.getCategory().substring(1)).createQuery():null)
//				.must(searchModel.getDateRange()!=null && searchModel.getDateRange() != ""?qb.range().onField( "modifiedDate" ).from(longDate.get(0)).to(longDate.get(1)).createQuery():null)
//				.must(qb.keyword().onField( "status" ).matching("APPROVED BIDDING").createQuery())
//				.createQuery();
		
		
		  org.apache.lucene.search.Query luceneQuery = qb
					.bool()  
					.must(searchModel.getSearchString()!= null && searchModel.getSearchString() != ""?qb.keyword().fuzzy().withThreshold(.8f).withPrefixLength(2).onFields("prodDesc","prodName").matching(searchModel.getSearchString().substring(0, 1).toUpperCase() + searchModel.getSearchString().substring(1)).createQuery():null)
					.must(searchModel.getLocation()!=null && searchModel.getLocation() !=""?qb.keyword().fuzzy().withThreshold(.8f).withPrefixLength(2).onFields("city").ignoreAnalyzer().matching(searchModel.getLocation()).createQuery():null)
					.must(searchModel.getPrice()!=null && searchModel.getPrice() != ""?qb.range().onField( "price" ).from(intPrices.get(0)).to(intPrices.get(1)).createQuery():null)
					.must(searchModel.getCategory()!=null && searchModel.getCategory() != ""? qb.keyword().fuzzy().withThreshold(.8f).withPrefixLength(2).onField("category.catName").matching(searchModel.getCategory()).createQuery():null)
					.must(searchModel.getDateRange()!=null && searchModel.getDateRange() != ""?qb.range().onField( "modifiedDate" ).from(longDate.get(0)).to(longDate.get(1)).createQuery():null)
					.must(qb.keyword().onField( "status" ).matching("APPROVED BIDDING").createQuery())
					.createQuery();
		         
			
			Query jpaQuery =
				    fullTextEntityManager.createFullTextQuery(luceneQuery, ProductModel.class);
			jpaQuery.setMaxResults(limit);
			jpaQuery.setFirstResult(offset);
			
			
			
			List result = jpaQuery.getResultList();
			
		/* Commit */
			
			org.hibernate.search.jpa.Search.getFullTextEntityManager(entityManager);
			session.getTransaction().commit();
			
		/* CLOSE CONNECTION */
			
			entityManager.close();
			return result;
	}
	
	
	
	
	
	
	

	//This schedular gets all the product and match their createdate with current date
	//If product is one month old then change status to NOTSOLD
	@Scheduled(fixedDelay=1000*60*60*24*7)
	public void setStatusToCloseForOneMonthOldProduct()
	{
		List<ProductModel> expiredProductList = new ArrayList<>();
//		List<String> statusList=new ArrayList<>();
//		statusList.add("APPROVED");
//		statusList.add("BIDDING");
		List<ProductModel> productList=productRepository.findByStatusIn(STATUS_LIST); 
		//Filter STREAM API
		for(ProductModel product:productList)
		{
			//logic for 90 days expired
//			if()
			if(product.getStatus().equals("APPROVED")||product.getStatus().equals("BIDDING")){
			product.setStatus("NOTSOLD");
			expiredProductList.add(product);
			}
		}
		productRepository.saveAll(expiredProductList);
		
	}
	
	
	@Override
	public List<ProductModel> getAllProductsOfCategory(int limit, int offset,int catCode) {
		//Sending Pageable Interface instead of PageRequest due to this error
//		At least 2 parameter(s) provided but only 1 parameter(s) present in query.
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"modifiedDate"));
		Optional<CategoryModel> categoryOpt=categoryRepository.findById(catCode);
		if(categoryOpt.isPresent())
		{
			Page<ProductModel> page= productRepository.findByCategoryAndStatusIn(categoryOpt.get(),STATUS_LIST,pg);
			return page.getContent();
		}
		return null;
		
		
	}

	@Override
	public long getAllCountOfNewProduct() {
		return productRepository.countByStatus("NEW");
	}

	@Override
	public List<ProductModel> getAllProductsOfSubCategory(int limit, int offset, int catCode) {
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"modifiedDate"));
		Optional<SubCategoryModel> subcategoryOpt=subCategoryRepository.findById(catCode);
		if(subcategoryOpt.isPresent())
		{
			Page<ProductModel> page= productRepository.findBySubCategoryAndStatusIn(subcategoryOpt.get(),STATUS_LIST,pg);
			return page.getContent();
		}
		return null;

	}

	@Override
	public void updateCategorySubCategoryInUse(ProductModel product) {
		if(product.getCategory()!=null) {
		Optional<CategoryModel> categoryOpt=categoryRepository.findById(product.getCategory().getCatCode());
		if(categoryOpt.isPresent())
		{
			CategoryModel cat=categoryOpt.get();
			cat.setInUse(true);
			categoryRepository.save(cat);
		}
		}
		
		
		if(product.getSubCategory()!=null) {
			Optional<SubCategoryModel> subcategoryOpt=subCategoryRepository.findById(product.getSubCategory().getCatCode());
			if(subcategoryOpt.isPresent())
			{
				SubCategoryModel subcat=subcategoryOpt.get();
				subcat.setInUse(true);
				subCategoryRepository.save(subcat);
			}
			}
	}

	@Override
	public long getCountByStatus(List<String> statusList) {
		return productRepository.countByStatusIn(statusList);
	}

	@Override
	public long getCountOfCategory(int catCode) {
		Optional<CategoryModel> categoryOpt=categoryRepository.findById(catCode);
		if(categoryOpt.isPresent())
		{
			return  productRepository.countByCategoryAndStatusIn(categoryOpt.get(),STATUS_LIST);
			
		}
		return 0;
	}

	@Override
	public long getCounOfSubCategory(int catCode) {
		Optional<SubCategoryModel> subcategoryOpt=subCategoryRepository.findById(catCode);
		if(subcategoryOpt.isPresent())
		{
			return productRepository.countBySubCategoryAndStatusIn(subcategoryOpt.get(),STATUS_LIST);
			
		}
		return 0;
	}

	@Override
	public List<ProductModel> getAllProductsIrrespectiveOfStatus(int limit, int offset) {
		ArrayList<ProductModel> list= new ArrayList<>();
		Pageable pg=PageRequest.of(offset,limit, new Sort(Direction.ASC,"modifiedDate"));
		Page<ProductModel> page=productRepository.findAll(pg);
		if(page.hasContent())
		{
			return page.getContent();
		}
		return list;
	}

	@Override
	public long getCountOfAllProduct() {
		return productRepository.count();
	}


}
