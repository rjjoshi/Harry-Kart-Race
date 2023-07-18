package se.atg.harrykart.test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.net.URI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("java-test")
@Configuration
public class HarryKartAppTest {

	private final static URI harryKartApp = URI.create("http://localhost:8181/java/api/play");

	@BeforeAll
	void setUp() {
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}

	@Test
	@DisplayName("Trying to GET instead of POST should return 405 Method not allowed")
	void useGetOnPostEndpointShouldNotBePossible() {
		when().get(harryKartApp).then().assertThat().statusCode(405);
	}
	@Test
	@DisplayName("The Application is validating invalid PowerDown by Horse in loops")
	void invalidateInputAgainstPowerDown() throws IOException {
		
		        String inputXML = TestUtils.readXMLFileToString("./src/test/resources/invalid_powerDown.xml");
		        given()
		                .contentType(ContentType.XML)
		                .accept(ContentType.JSON)
		                .body(inputXML)
		        .when()
		                .post(harryKartApp)
		        .then()
		                .statusCode(400)
		                .contentType(ContentType.JSON)
		                .body(equalTo("The Horse is not moving in lane number :2"));
		    
	}
	
	@Test
	@DisplayName("The Application is validating invalid Schema")
	void invalidateInputAgainstSchema() throws IOException {
		
		        String inputXML = TestUtils.readXMLFileToString("./src/test/resources/invalid_participants.xml");
		        given()
		                .contentType(ContentType.XML)
		                .accept(ContentType.JSON)
		                .body(inputXML)
		        .when()
		                .post(harryKartApp)
		        .then()
		                .statusCode(400);
		    
	}
	@Test
	@DisplayName("The Application is validating Tie condition in game")
	void testingTieCondition() throws IOException {
		
		        String inputXML = TestUtils.readXMLFileToString("./src/test/resources/check_tie_condition.xml/");
		        given()
		                .contentType(ContentType.XML)
		                .accept(ContentType.JSON)
		                .body(inputXML)
		        .when()
		                .post(harryKartApp)
		        .then()
		                .statusCode(409).contentType(ContentType.JSON)
		                .body(equalTo("There is Tie condition in Top 3 horse ranking"));
		    
	}
	


	@Test
	@DisplayName("The Application is validating input_0.xml")
	void validateInput0() throws IOException {
		
		        String inputXML = TestUtils.readXMLFileToString("./src/main/resources/input_0.xml");
		        given()
		                .contentType(ContentType.XML)
		                .accept(ContentType.JSON)
		                .body(inputXML)
		        .when()
		                .post(harryKartApp)
		        .then()
		                .statusCode(200)
		                .contentType(ContentType.JSON)
		                .body(equalTo(TestUtils.expectedOutput(new String[]{"TIMETOBELUCKY", "HERCULES BOKO", "CARGO DOOR"})));
		    
	}
	@Test
	@DisplayName("The Application is validating input_1.xml")
	void validateInput1() throws IOException {
		
		        String inputXML = TestUtils.readXMLFileToString("./src/main/resources/input_1.xml");
		        given()
		                .contentType(ContentType.XML)
		                .accept(ContentType.JSON)
		                .body(inputXML)
		        .when()
		                .post(harryKartApp)
		        .then()
		                .statusCode(200)
		                .contentType(ContentType.JSON)
		                .body(equalTo(TestUtils.expectedOutput(new String[]{"WAIKIKI SILVIO","TIMETOBELUCKY", "HERCULES BOKO"})));
		    
	}
	@Test
	@DisplayName("The Application is validating input_2.xml")
	void validateInput2()  throws IOException {
		
		        String inputXML = TestUtils.readXMLFileToString("./src/main/resources/input_2.xml");
		        given()
		                .contentType(ContentType.XML)
		                .accept(ContentType.JSON)
		                .body(inputXML)
		        .when()
		                .post(harryKartApp)
		        .then()
		                .statusCode(200)
		                .contentType(ContentType.JSON)
		                .body(equalTo(TestUtils.expectedOutput(new String[]{ "HERCULES BOKO", "TIMETOBELUCKY","WAIKIKI SILVIO"})));
		    
	}
	
	
}
