package com.anaadih.aclassdeal.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.ProductModel;
import com.anaadih.aclassdeal.Model.ReportedAdsModel;
import com.anaadih.aclassdeal.Model.User;
import com.anaadih.aclassdeal.Model.WishlistModel;
import com.anaadih.aclassdeal.Repository.ProductRepository;
import com.anaadih.aclassdeal.Repository.ReportedAidsRepository;

@Service
public class ReportedAidsserviceImpl implements ReportedAidService {


	@Autowired 
	private ReportedAidsRepository reportedAids;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public ReportedAdsModel saveAdds(@Valid ReportedAdsModel rAds)
	{
		
		return reportedAids.save(rAds);
	}
	
	
	@Override
	public HashMap<String, Object> saveAddsWithCheckPrevExistOrNot(@Valid ReportedAdsModel rAds)
	{
		
		HashMap<String, Object> map = new HashMap<>();
		
		ReportedAdsModel previousAdded=reportedAids.findByProductId(rAds.getProductId());
		
		//Setting Reported By string
		User user = commonService.getLoggedInUser();
		if(user!=null) {
			rAds.setReportedBy(user.getName());
		}
		//Setting Uploaded By User (Taken from product instance)
		Optional<ProductModel> productOpt=productRepository.findById(rAds.getProductId().getProdId());
		if(productOpt.isPresent() && productOpt.get().getUserId()!=null) {
			//Might contan null pointer,need to check
			rAds.setUploadedBy(productOpt.get().getUserId().getName());
		}
		
		if(previousAdded!=null)
		{
			//count ++;
			int intCount=previousAdded.getReportCount();
			intCount++;			
			previousAdded.setReportCount(intCount);			
			reportedAids.save(previousAdded);
			
			map.put("msg", "Already Added to wishlist");
			map.put("type", "Warning!");
			return map;
			
			
		}
		
		 map.put("msg", "Product Added to wishlist");
		 map.put("type", "Success");
		 rAds.setReportCount(1);
		reportedAids.save(rAds);
		
	return map;
	}
	

	
	@Override
	public ReportedAdsModel blockAdds(int reportedId) {
		ReportedAdsModel model = null;
		Optional<ReportedAdsModel> obj=reportedAids.findById(reportedId);
		if(obj.isPresent()) {
			 model = obj.get();
			model.setStatus(false);
			reportedAids.save(model);
		}
				
		return model;
	}
	
	@Override
	public List<ReportedAdsModel> getallAds(int limit,int offset) {
		System.out.println("OFFSET HERE"+offset);
		Pageable pg=PageRequest.of(offset-1,limit);
		Page<ReportedAdsModel> page = reportedAids.findByStatusOrderByReportCountDesc(true, pg);
		return page.getContent();
	//return	reportedAids.findByStatusAndOrderByReportedCountDesc(true);	
	}


	@Override
	public long getCountOfAllReportedAds() {
		return reportedAids.countByStatus(true);
	}

}
