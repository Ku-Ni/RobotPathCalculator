package co.uk.coenie.robot_path_calculator;


import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.uk.coenie.robot_path_calculator.dto.ErrorResponse;
import co.uk.coenie.robot_path_calculator.dto.Request;
import co.uk.coenie.robot_path_calculator.dto.Response;
import co.uk.coenie.robot_path_calculator.dto.SuccessfulResponse;

import static org.assertj.core.api.BDDAssertions.then;

/**
 * Based on tests from Spring website
 * 
 * @author Coenie
 * @see <a href="https://spring.io/guides/gs/actuator-service/">Building a RESTful Web Service with Spring Boot Actuator</>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RobotPathCalculatorConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class RobotPathCalculatorTests {

	@LocalServerPort
	private int port;

	@Value("${local.management.port}")
	private int mgt;

	@Autowired
	private TestRestTemplate testRestTemplate;
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.mgt + "/info", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	
	@Test
	public void shouldReturn200WhenSendingRequestToController() throws Exception {
		String input = "{\"roomSize\":[5,5],\"coords\":[1,2],\"patches\":[[1,0],[2,2],[2,3]],\"instructions\":\"NNESEESWNWW\"}";
		Request request = objectMapper.readValue(input, Request.class);
		
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.postForEntity("http://localhost:" + this.port + "/calculate-path", request, Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}
	
	@Test
	public void testInitialInstructions() throws Exception {
		String input = "{\"roomSize\":[5,5],\"coords\":[1,2],\"patches\":[[1,0],[2,2],[2,3]],\"instructions\":\"NNESEESWNWW\"}";
		Request request = objectMapper.readValue(input, Request.class);
		String expectedResponse = "{\"coords\":[1,3],\"patches\":1}";
		
		Response response = testRestTemplate.postForObject("http://localhost:" + this.port + "/calculate-path", request, SuccessfulResponse.class);
		
		then(objectMapper.writeValueAsString(response)).isEqualTo(expectedResponse);
	}

	@Test
	public void zeroRoomSize() throws Exception {
		String input = "{\"roomSize\":[0,0],\"coords\":[1,2],\"patches\":[],\"instructions\":\"NNESEESWNWW\"}";
		Request request = objectMapper.readValue(input, Request.class);
		
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = testRestTemplate.postForEntity("http://localhost:" + this.port + "/calculate-path", request, Map.class);

		then(entity.getBody().get("ExceptionClass").equals("java.lang.IllegalArgumentException"));
	}

	@Test
	public void negativeRoomSize() throws Exception {
		String input = "{\"roomSize\":[-5,-4],\"coords\":[1,2],\"patches\":[],\"instructions\":\"NNESEESWNWW\"}";
		Request request = objectMapper.readValue(input, Request.class);
		
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = testRestTemplate.postForEntity("http://localhost:" + this.port + "/calculate-path", request, Map.class);

		then(entity.getBody().get("ExceptionClass").equals("java.lang.IllegalArgumentException"));
	}
	
	
	

}
