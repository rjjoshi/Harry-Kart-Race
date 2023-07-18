package se.atg.harrykart.service;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Service;

import se.atg.harrykart.dto.Ranking;

@Service
public interface HarrykartService {

	Ranking calculateRanking(String inputPayload) throws JAXBException;

}
