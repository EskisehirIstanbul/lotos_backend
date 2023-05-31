package com.lotus.lotusSPM.web;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lotus.lotusSPM.message.ResponseMessage;
import com.lotus.lotusSPM.model.Opportunities;
import com.lotus.lotusSPM.service.base.OpportunitiesService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class OpportunitiesController {

	@Autowired
	private OpportunitiesService opportunitiesService;

	@PutMapping("/opportunitie/{id}")
	public ResponseEntity<URI> updateOpportunities(@RequestBody Opportunities opportunities,@PathVariable("id") String username) {
		try {
			//opportunities.setId(id);
			opportunities.setUsername(username);
			opportunitiesService.updateOpportunities(opportunities);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	@PostMapping("/uploadLogo")
	public ResponseEntity<ResponseMessage> uploadLogo(@RequestParam("logo") MultipartFile file) {
		String message = "";
	    try {
	    	opportunitiesService.storeLogo(file);

	      message = "Uploaded the file successfully: " + file.getOriginalFilename();
	      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }
	}

	@DeleteMapping("/opportunitie/{id}")
	public ResponseEntity<?> deleteOpportunities(@PathVariable("id") String id) {
		try {
			opportunitiesService.deleteOpportunities(id);
			return ResponseEntity.ok().build();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
			return ResponseEntity.notFound().build();
		}

	}

	@GetMapping("/opportunities")
	public ResponseEntity<Object> getOpportunities() {
		List<Opportunities> opportunities = opportunitiesService.getOpportunities();
		return ResponseEntity.ok(opportunities);
	}

}
