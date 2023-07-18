package se.atg.harrykart.controller;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import se.atg.harrykart.dto.Ranking;
import se.atg.harrykart.service.HarrykartService;
import se.atg.harrykart.transformer.XmltoHarrykartTransformer;

@RestController
@RequestMapping("/java/api")
public class HarryKartController {
	@Autowired
	private HarrykartService harrykartService;

	@PostMapping(path = "/play", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String playHarryKart(@RequestBody String inputPayload) throws JAXBException {
		Ranking outputRankings = harrykartService.calculateRanking(inputPayload);
		return outputRankings.toString();
	}

}
