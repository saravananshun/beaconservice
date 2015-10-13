package com.beacon.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.beacon.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.beacon.model.BankService;
import com.beacon.model.CustomerServiceQueue;
import com.beacon.model.UserProfile;
import com.beacon.model.UserProfileSetup;
import com.beacon.model.UserWelcomeData;
import com.beacon.springconfig.SpringMongoConfig;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

@Component
public class BeaconDAO {
	private static final String USER_PROFILE_COLLECTION = "userprofile";
	private static final String BANKING_SERVICE_COLLECTION = "bankingservice";
	private static final String CUSTOMER_SERVICE_QUEUE = "customerservicequeue";

	@Autowired
	private SpringMongoConfig config;

	public UserWelcomeData getUserData() {
		UserWelcomeData userData = new UserWelcomeData();
		userData.setUserProfile(getUserProfile());
		userData.setBankService(getBankingService());
		userData.setToken(getUserToken());
		return userData;
	}

	private Token getUserToken() {
		return new Token("1234", "100");
	}

	private UserProfile getUserProfile() {
		UserProfile userProfile = null;
		try {
			userProfile = config.mongoTemplate().findOne(
					new Query(Criteria.where("imeiNumber").is("IMEI000011")),
					UserProfile.class, USER_PROFILE_COLLECTION);
			System.out.println("User Profile " + userProfile);

			userProfile.setImageBytes(readUserProfileImage(userProfile.getAccountNumber()));
			getBankingService();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userProfile;
	}

	private BankService getBankingService() {
		BankService bankService = null;
		try {
			bankService = config.mongoTemplate().findOne(
					new Query(Criteria.where("bankCode").is("B1")),
					BankService.class, BANKING_SERVICE_COLLECTION);
			System.out.println("Banking Service " + bankService);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankService;
	}

	private byte[] readUserProfileImage(String accountNumber) throws Exception {
		GridFS fileStore = new GridFS(config.mongoTemplate().getDb(),
				"filestore");
		GridFSDBFile gridFile = fileStore.findOne(accountNumber + "-image");

		InputStream in = gridFile.getInputStream();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int data = in.read();
		while (data >= 0) {
			out.write((char) data);
			data = in.read();
		}
		out.flush();
		out.close();
		return out.toByteArray();
	}

	public void saveUserProfile(UserProfileSetup setup) throws Exception {
		String newFileName = setup.getAccountNumber() + "-image";
		UserProfile userProfile = new UserProfile();
		userProfile.setAccountNumber(setup.getAccountNumber());
		userProfile.setBankCode("B1");
		userProfile.setFirstName(setup.getFirstName());
		userProfile.setLastName(setup.getLastName());
		userProfile.setImeiNumber(setup.getImeiNumber());

		config.mongoTemplate().insert(userProfile, USER_PROFILE_COLLECTION);

		// Now let's store the binary file data using filestore GridFS
		GridFS fileStore = new GridFS(config.mongoTemplate().getDb(),
				"filestore");
		GridFSInputFile inputFile = fileStore.createFile(setup.getMultiPart()
				.getInputStream());
		inputFile.setId(setup.getAccountNumber());
		inputFile.setFilename(newFileName);
		inputFile.save();

	}

	public List<CustomerServiceQueue> findNewCustomersToServe() {
		List<CustomerServiceQueue> queue = new ArrayList<CustomerServiceQueue>();
		try {
			queue = config.mongoTemplate().find(
					new Query(Criteria.where("status").is("A")),
					CustomerServiceQueue.class, CUSTOMER_SERVICE_QUEUE);
			
			for(CustomerServiceQueue customer : queue){
				UserProfile userProfile = config.mongoTemplate().findOne(
						new Query(Criteria.where("accountNumber").is(customer.getAccountNumber())),
						UserProfile.class, USER_PROFILE_COLLECTION);
				customer.setFirstName(userProfile.getFirstName());
				customer.setLastName(userProfile.getLastName());
				customer.setImageBytes(readUserProfileImage(customer.getAccountNumber()));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return queue;
	}
}
