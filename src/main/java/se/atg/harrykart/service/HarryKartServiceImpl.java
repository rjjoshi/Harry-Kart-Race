package se.atg.harrykart.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.atg.harrykart.constants.HarryKartConstants;
import se.atg.harrykart.requestdto.HarryKart;
import se.atg.harrykart.requestdto.Lane;
import se.atg.harrykart.requestdto.Loop;
import se.atg.harrykart.requestdto.Participant;
import se.atg.harrykart.dto.PositionCalculator;
import se.atg.harrykart.dto.Ranking;
import se.atg.harrykart.transformer.XmltoHarrykartTransformer;
import se.atg.harrykart.util.HarrykartUtils;
import se.atg.harrykart.validator.HarryKartValidator;

@Service
public class HarryKartServiceImpl implements HarrykartService {
	@Autowired
	HarryKartValidator harryKartValidator;

	@Autowired
	XmltoHarrykartTransformer xmltoHarrykartTransformer;

	@Override
	public Ranking calculateRanking(String inputPayload) throws JAXBException {
		// Validating input payload against given xsd schema
		harryKartValidator.validateXml(inputPayload);
		// Transforming String xml input Payload to HarryKart Object
		HarryKart harryKart = xmltoHarrykartTransformer.convertXmlStringtoHarrykart(inputPayload);
		Map<Integer, PositionCalculator> timeMap = new HashMap<>();
		// calculating time for first loop with base speed
		initTimeMapForFirstLoop(harryKart, timeMap);
		// updating aggregated time and speed for each lane/horse in loops
		updateTimeMapAccToPowerUpDown(harryKart, timeMap);
		// fetching top 3 positions
		List<String> horses = getTopPositions(timeMap);

		return new Ranking(horses);
	}

	private void initTimeMapForFirstLoop(HarryKart harryKart, Map<Integer, PositionCalculator> timeMap) {
		List<Participant> participants = harryKart.getStartList().getParticipant();
		for (Participant participant : participants) {
			harryKartValidator.validateHorseSpeed(participant.getBaseSpeed(), participant.getLane());
			double time = HarrykartUtils.getTimeforLoop(participant.getBaseSpeed());
			timeMap.put(participant.getLane(),
					new PositionCalculator(participant.getName(), participant.getBaseSpeed(), time));
		}

	}

	private void updateTimeMapAccToPowerUpDown(HarryKart harryKart, Map<Integer, PositionCalculator> timeMap) {
		List<Loop> loops = harryKart.getPowerUps().getLoop();
		harryKartValidator.validateNumberOfLoops(harryKart.getNumberOfLoops(), loops.size());
		for (Loop loop : loops) {
			for (Lane lane : loop.getLane()) {
				PositionCalculator positionCalculator = timeMap.get(lane.getNumber());

				int netSpeed = positionCalculator.getCurrentSpeed() + lane.getValue();
				harryKartValidator.validateHorseSpeed(netSpeed, lane.getNumber());
				positionCalculator.setCurrentSpeed(netSpeed);

				double netTime = HarrykartUtils.getTimeforLoop(positionCalculator.getCurrentSpeed());
				positionCalculator.setTotalTime(positionCalculator.getTotalTime() + netTime);
			}
		}
	}


	private List<String> getTopPositions(Map<Integer, PositionCalculator> timeMap) {
		List<PositionCalculator> topTimingPosition = timeMap.values().stream()
				.sorted(Comparator.comparingDouble(PositionCalculator::getTotalTime)).limit(HarryKartConstants.TOP4_POSITIONS)
				.collect(Collectors.toList());
		harryKartValidator.checkIfTimesEqual(topTimingPosition);
		return topTimingPosition.stream().map(PositionCalculator::getHorseName).limit(HarryKartConstants.REQUIRED_TOP_POSITIONS).collect(Collectors.toList());
	}

	

}
