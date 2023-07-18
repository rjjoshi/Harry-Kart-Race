package se.atg.harrykart.transformer;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.springframework.stereotype.Component;

import se.atg.harrykart.requestdto.HarryKart;


@Component
public class XmltoHarrykartTransformer {

	public HarryKart convertXmlStringtoHarrykart(String inputPayload) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance("se.atg.harrykart.requestdto");
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		JAXBElement<HarryKart> jaxb= (JAXBElement<HarryKart>) unmarshaller.unmarshal(new StringReader(inputPayload));
		return jaxb.getValue();
	}

}

