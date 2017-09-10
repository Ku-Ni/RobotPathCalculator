package co.uk.coenie.robot_path_calculator.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.uk.coenie.robot_path_calculator.bo.CalculateResults;
import co.uk.coenie.robot_path_calculator.dto.ErrorResponse;
import co.uk.coenie.robot_path_calculator.dto.Request;
import co.uk.coenie.robot_path_calculator.dto.Response;

@Controller
public class ServiceController {
	private Logger logger = LogManager.getLogger(ServiceController.class);
	
	@Autowired
	private CalculateResults calculateResults;
	
	@RequestMapping(value="/calculate-path",method=RequestMethod.POST)
	public @ResponseBody Response calculatePath(@RequestBody String jsonString) {
			Request input;
			try {
				input = new ObjectMapper().readValue(jsonString, Request.class);
				input.validate();

				return calculateResults.processInput(input);
			} catch (Exception e) {
				logger.error("{} thrown: {}.\n{}",e.getClass(), e.getMessage(), e.getStackTrace());
				return new ErrorResponse(e);
			}
	}
}
