package com.beacon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.beacon.dao.BeaconDAO;
import com.beacon.model.UserProfile;
import com.beacon.model.UserWelcomeData;

@Controller
@RequestMapping("/")
public class BeaconController {
	
	@Autowired
	private BeaconDAO beaconDAO;

	@RequestMapping(value="/hello", method = RequestMethod.GET)
	public @ResponseBody String getMovie(ModelMap model) {
      return "hello world";
	}
	
	@RequestMapping(value="/getuserprofile", method = RequestMethod.GET)
	public @ResponseBody UserWelcomeData getUserProfile(ModelMap model) {
      return beaconDAO.getUserData();
	}
	
}