package com.anaadih.aclassdeal.Service;

import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.anaadih.aclassdeal.Model.ReportedAdsModel;

@Service
public interface ReportedAidService {

	ReportedAdsModel saveAdds(@Valid ReportedAdsModel rAds);

	ReportedAdsModel blockAdds(int i);

	List<ReportedAdsModel> getallAds(int limit,int offset);

	HashMap<String, Object>  saveAddsWithCheckPrevExistOrNot(@Valid ReportedAdsModel rAds);

	long getCountOfAllReportedAds();
}
