package co.uk.coenie.robot_path_calculator.bo;
import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.uk.coenie.robot_path_calculator.dto.ErrorResponse;
import co.uk.coenie.robot_path_calculator.dto.Request;
import co.uk.coenie.robot_path_calculator.dto.Response;
import co.uk.coenie.robot_path_calculator.service.ServiceController;

public class CalculateResultsTests {
	ObjectMapper objectMapper;
	CalculateResults calculateResults;
	ServiceController service;
	@Before
	public void setUp() throws Exception {
		objectMapper = new ObjectMapper();
		calculateResults = new CalculateResultsImpl();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInitialInstructions() throws IOException {
		String input = "{\"roomSize\":[5,5],\"coords\":[1,2],\"patches\":[[1,0],[2,2],[2,3]],\"instructions\":\"NNESEESWNWW\"}";
		String expectedResponse = "{\"coords\":[1,3],\"patches\":1}";

		Request request = objectMapper.readValue(input, Request.class);
		Response response = calculateResults.processInput(request);

		assertEquals(expectedResponse, objectMapper.writeValueAsString(response));
	}

	@Test
	public void testStartOnPatch() throws IOException {
		String input = "{\"roomSize\":[5,5],\"coords\":[1,2],\"patches\":[[1,0],[1,2],[2,2],[2,3]],\"instructions\":\"NNESEESWNWW\"}";
		String expectedResponse = "{\"coords\":[1,3],\"patches\":2}";

		Request req = objectMapper.readValue(input, Request.class);
		Response res = calculateResults.processInput(req);

		assertEquals(expectedResponse, objectMapper.writeValueAsString(res));
	}

	@Test
	public void testEndOnPatch() throws IOException {
		String input = "{\"roomSize\":[5,5],\"coords\":[1,2],\"patches\":[[1,3]],\"instructions\":\"NNESEESWNWW\"}";
		String expectedResponse = "{\"coords\":[1,3],\"patches\":1}";

		Request req = objectMapper.readValue(input, Request.class);
		Response res = calculateResults.processInput(req);

		assertEquals(expectedResponse, objectMapper.writeValueAsString(res));
	}

	@Test
	public void testMissPatches() throws IOException {
		String input = "{\"roomSize\":[5,5],\"coords\":[1,2],\"patches\":[[1,0],[2,2]],\"instructions\":\"NNESEESWNWW\"}";
		String expectedResponse = "{\"coords\":[1,3],\"patches\":0}";

		Request req = objectMapper.readValue(input, Request.class);
		Response res = calculateResults.processInput(req);

		assertEquals(expectedResponse, objectMapper.writeValueAsString(res));
	}

	@Test
	public void testNoPatches() throws IOException {
		String input = "{\"roomSize\":[5,5],\"coords\":[1,2],\"patches\":[],\"instructions\":\"NNESEESWNWW\"}";
		String expectedResponse = "{\"coords\":[1,3],\"patches\":0}";

		Request req = objectMapper.readValue(input, Request.class);
		Response res = calculateResults.processInput(req);

		assertEquals(expectedResponse, objectMapper.writeValueAsString(res));
	}

}
