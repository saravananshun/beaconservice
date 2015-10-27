package com.beacon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.beacon.dao.BeaconDAO;
import com.beacon.model.CustomerServiceQueue;
import com.beacon.model.StallSetup;
import com.beacon.model.UserProfileSetup;
import com.beacon.model.UserWelcomeData;

@Controller
@RequestMapping("/")
public class BeaconController {

	@Autowired
	private BeaconDAO beaconDAO;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public @ResponseBody String getMovie(ModelMap model) {
		return "hello world";
	}

	@RequestMapping(value = "/getuserprofile", method = RequestMethod.GET)
	public @ResponseBody UserWelcomeData getUserProfile(@RequestParam("accountNumber") String accountNumber,
				ModelMap model) {
		return beaconDAO.getUserData(accountNumber);
	}

	@RequestMapping(value = "/setuserprofile", method = RequestMethod.GET)
	public ModelAndView setupUserProfile(ModelMap model) {
		ModelAndView modelAndView = new ModelAndView("userprofile", "userProfileSetup",
				new UserProfileSetup());
		modelAndView.addObject("userProfileList", beaconDAO.getAllUsers());
		return modelAndView;
	}
	
	@RequestMapping(value = "/setupstall", method = RequestMethod.GET)
	public ModelAndView setupStall(ModelMap model) {
		ModelAndView modelAndView = new ModelAndView("stallsetup", "stallSetup",
				new StallSetup());
		return modelAndView;
	}

	@RequestMapping(value = "/submituserprofile", method = RequestMethod.POST)
	public @ResponseBody String submitUserProfile(
			@ModelAttribute("userProfileSetup") UserProfileSetup setupData) {
		try {
			beaconDAO.saveUserProfile(setupData);
		} catch (Exception e) {			
			e.printStackTrace();
			return "Submitted Failed.... ";
		}
		return "Submitted Succesfully.... ";
	}
	
	@RequestMapping(value = "/submitstallsetup", method = RequestMethod.POST)
	public @ResponseBody String submitStall(
			@ModelAttribute("stallSetup") StallSetup setupData) {
		try {
			beaconDAO.saveStallDetails(setupData);
		} catch (Exception e) {			
			e.printStackTrace();
			return "Submitted Failed.... ";
		}
		return "Submitted Succesfully.... ";
	}
	
	@RequestMapping(value = "/checkforcustomers", method = RequestMethod.GET)
	public @ResponseBody List<CustomerServiceQueue> checkForNewCustomers(ModelMap model) {
		return beaconDAO.findNewCustomersToServe();
	}
	
	@RequestMapping(value = "/swftest", method = RequestMethod.GET)
	public ModelAndView swfTest(ModelMap model) {
		ModelAndView modelAndView = new ModelAndView("swftest");
		return modelAndView;
	}

}