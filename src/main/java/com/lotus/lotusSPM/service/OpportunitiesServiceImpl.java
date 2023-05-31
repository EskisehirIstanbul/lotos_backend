package com.lotus.lotusSPM.service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.lotus.lotusSPM.dao.OpportunitiesDao;
import com.lotus.lotusSPM.model.Opportunities;
import com.lotus.lotusSPM.service.base.OpportunitiesService;

@Service
@Transactional(rollbackFor = Exception.class)
public class OpportunitiesServiceImpl implements OpportunitiesService {

	private OpportunitiesDao opportunitiesDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setOpportunitiesDao(OpportunitiesDao opportunitiesDao) {
		this.opportunitiesDao = opportunitiesDao;
	}

	@Override
	public void deleteOpportunities(String opp_id) {
		opportunitiesDao.deleteById(opp_id);
		;
	}

	@Override
	public List<Opportunities> getOpportunities() {
		return opportunitiesDao.findAll();
	}

	@Override
	public Opportunities updateOpportunities(Opportunities opportunities) {
		Opportunities opp = opportunitiesDao.findByUsername(opportunities.getUsername());
		opp.setCompanyName(opportunities.getCompanyName());
		opp.setDeadline(opportunities.getDeadline());
		opp.setLocation(opportunities.getLocation());
		opp.setTitle(opportunities.getTitle());
		opp.setUrl(opportunities.getUrl());
		return opportunitiesDao.save(opp);
	}

	@Override
	public Opportunities storeLogo(MultipartFile file) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Opportunities FileDB = new Opportunities(file.getBytes(), fileName, file.getContentType());
		FileDB.setUsername(random());
		return opportunitiesDao.save(FileDB);
	}

	@Override
	public Opportunities findByUsername(String username) {
		return opportunitiesDao.findByUsername(username);
	}

	public String random() {

		// create a string of uppercase and lowercase characters and numbers
		String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		String numbers = "0123456789";

		String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;

		StringBuilder sb = new StringBuilder();

		Random random = new Random();

		int length = 10;

		for (int i = 0; i < length; i++) {

			int index = random.nextInt(alphaNumeric.length());

			char randomChar = alphaNumeric.charAt(index);

			sb.append(randomChar);
		}

		String randomString = sb.toString();
		return randomString;
	}

}
