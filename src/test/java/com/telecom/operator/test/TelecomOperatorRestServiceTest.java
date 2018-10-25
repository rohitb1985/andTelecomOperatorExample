package com.telecom.operator.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.operator.telecom.TelecomOperatorApplication;
import com.operator.telecom.rest.controller.TelecomOperatorController;
import com.operator.telecom.service.TelecomOperatorService;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@ContextConfiguration(classes={TelecomOperatorApplication.class})
@WebMvcTest(value = TelecomOperatorController.class)
public class TelecomOperatorRestServiceTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	TelecomOperatorService telecomOperatorService;
	
	String phonesList = "[{\"phoneId\": 0, \"phoneNumber\": 7774586319,\"status\": \"InActive\"}, {\"phoneId\": 1,\"phoneNumber\": 7774597802,\"status\": \"Active\"}]";
	String customerPhoneList = "{ \"customerId\": 1, \"customerName\": \"Customer2\", \"phoneList\": { \"7774586271\": { \"phoneId\": 3, \"phoneNumber\": 7774586271, \"status\": \"Active\" }, "
			+ "\"7774531006\": { \"phoneId\": 2, \"phoneNumber\": 7774531006, \"status\": \"InActive\" } } }";
	
	
	/**
	 * JUnit test case to verify the response using Mockito.
	 * This will verify if the rest endpoint is getting invoked and returning data in the expected format
	 */
	@Test
	public void getAllPhoneNumbersTest(){
		Mockito.when(telecomOperatorService.getAllPhoneNumbers()).thenReturn(phonesList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/v1/telecom/org/api/allPhoneNumbers").accept(
				MediaType.APPLICATION_JSON);

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			System.out.println(result.getResponse().getContentAsString());
			String expected = "[{\"phoneId\": 0, \"phoneNumber\": 7774586319,\"status\": \"InActive\"}, {\"phoneId\": 1,\"phoneNumber\": 7774597802,\"status\": \"Active\"}]";
			JSONAssert.assertEquals(expected, result.getResponse()
					.getContentAsString(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * JUnit test case to verify the response using Mockito.
	 * This will verify if the rest endpoint is getting invoked and returning data in the expected format
	 */
	@Test
	public void getCustomerPhoneNumbersTest(){
		String customerId = "1";
		Mockito.when(telecomOperatorService.getCustomerPhoneNumbers(customerId)).thenReturn(customerPhoneList);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				"/v1/telecom/org/api/customerPhoneNumbers/"+customerId).accept(
				MediaType.APPLICATION_JSON);

		try {
			MvcResult result = mockMvc.perform(requestBuilder).andReturn();
			System.out.println(result.getResponse().getContentAsString());
			String expected = "{ \"customerId\": 1, \"customerName\": \"Customer2\", \"phoneList\": { \"7774586271\": { \"phoneId\": 3, \"phoneNumber\": 7774586271, \"status\": \"Active\" }, "
					+ "\"7774531006\": { \"phoneId\": 2, \"phoneNumber\": 7774531006, \"status\": \"InActive\" } } }";
			JSONAssert.assertEquals(expected, result.getResponse()
					.getContentAsString(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * JUnit test case to verify the response using Mockito.
	 * This will verify if the rest endpoint is getting invoked and returning data in the expected format
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void activateNumberTest() throws Exception {
		String customerId = "1";
		String phoneNumber = "7774586271";
		String requestBody = "{\"customerId\":\"1\", \"phoneNumber\":\"7774586271\"}";
		
		Mockito.when(telecomOperatorService.activateNumber(customerId, phoneNumber)).thenReturn("Phone Number activated successfully");

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/v1/telecom/org/api/activateNumber/"+customerId+"/"+phoneNumber)
				.accept(MediaType.APPLICATION_JSON).content(requestBody)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		String expected = "Phone Number activated successfully";
		Assert.assertEquals(expected, response.getContentAsString());
	}
	
}
