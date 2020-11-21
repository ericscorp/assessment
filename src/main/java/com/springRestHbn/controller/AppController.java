package com.springRestHbn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.springRestHbn.model.RequestDTO;
import com.springRestHbn.model.ResponseDTO;
import com.springRestHbn.service.SubscriptionService;


@Controller
@RequestMapping("/ezpay")
public class AppController {

	@Autowired
	SubscriptionService service;
	
	@Autowired
	MessageSource messageSource;

	/*
	 * This method will list all existing employees.
	 */
	@RequestMapping(value = { "/subscription" }, method = RequestMethod.POST)
	public @ResponseBody ResponseDTO doSubscription(@RequestBody final String input) {
		
		System.out.println("Start");
		RequestDTO request = (RequestDTO) new Gson().fromJson(input, RequestDTO.class);

		ResponseDTO response = service.subscription(request);
		System.out.println("End");
		return response;
	}

}
