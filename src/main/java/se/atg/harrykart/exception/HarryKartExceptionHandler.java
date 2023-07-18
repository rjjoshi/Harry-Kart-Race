package se.atg.harrykart.exception;

import javax.xml.bind.JAXBException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HarryKartExceptionHandler {

	@ExceptionHandler(XMLValidationException.class)
	public ResponseEntity<String> handleXMLValidationException(XMLValidationException ex) {
		String errorMessage = "XML validation error: " + ex.getMessage();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}

	@ExceptionHandler(HorseNotMovingException.class)
	public ResponseEntity<String> handleHorseNotMovingException(HorseNotMovingException ex) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
		String errorMessage = "Illegal argument error: " + ex.getMessage();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
	}

	@ExceptionHandler(JAXBException.class)
	public ResponseEntity<String> handleJAXBException(JAXBException ex) {
		String errorMessage = "Error While unmarshling payload: " + ex.getMessage();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	}
	
	@ExceptionHandler(TieConditionException.class)
	public ResponseEntity<String> handleTieConditionException(TieConditionException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
}
