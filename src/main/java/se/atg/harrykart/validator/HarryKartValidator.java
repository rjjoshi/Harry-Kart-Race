package se.atg.harrykart.validator;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import se.atg.harrykart.dto.PositionCalculator;
import se.atg.harrykart.exception.HorseNotMovingException;
import se.atg.harrykart.exception.TieConditionException;
import se.atg.harrykart.exception.XMLValidationException;

@Component
public class HarryKartValidator {

	@Value("${xsd.file.path}")
	private String xsdPath;

	public void validateXml(String xmlString) {
		try {
			File xsdFile = new File(xsdPath);
			SchemaFactory factory = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(xsdFile);
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(xmlString)));
		} catch (SAXException | IOException e) {
			throw new XMLValidationException("XML validation error: " + e.getMessage());
		}
	}

	public void validateHorseSpeed(int speed, int laneNumber) {
		if (speed < 1) {
			throw new HorseNotMovingException("The Horse is not moving in lane number :" + laneNumber);

		}

	}

	public void validateNumberOfLoops(int givenNumberOfLoops, int actualSizeofLoops) {
		if (actualSizeofLoops != givenNumberOfLoops - 1) {
			throw new IllegalArgumentException("Input number of loops should be equal to number of loops under PowerUp");
		}

	}
	// This method is checking if time is equal for 2 horses
		public void checkIfTimesEqual(List<PositionCalculator> positionCalculators) {
			for (int i = 0; i < positionCalculators.size() - 1; i++) {
				PositionCalculator current = positionCalculators.get(i);
				PositionCalculator next = positionCalculators.get(i + 1);

				if (current.getTotalTime() == next.getTotalTime()) {
					throw new TieConditionException("There is Tie condition in Top 3 horse ranking");
				}
			}
		}
}
