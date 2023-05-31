package com.lotus.lotusSPM.service.base;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.lotus.lotusSPM.model.Opportunities;

public interface OpportunitiesService {

	void deleteOpportunities(String opp_id);
	List<Opportunities> getOpportunities();
	Opportunities updateOpportunities(Opportunities opportunities);
	Opportunities storeLogo(MultipartFile file) throws IOException;
	//Opportunities findById(Long opp_id);
	Opportunities findByUsername(String username);
}
