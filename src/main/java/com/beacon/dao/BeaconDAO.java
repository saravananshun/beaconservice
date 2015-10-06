package com.beacon.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.w3c.dom.UserDataHandler;

import com.beacon.model.BankService;
import com.beacon.model.UserProfile;
import com.beacon.model.UserWelcomeData;
import com.beacon.springconfig.SpringMongoConfig;

@Component
public class BeaconDAO {
	private static final String USER_PROFILE_COLLECTION = "userprofile";
	private static final String BANKING_SERVICE_COLLECTION = "bankingservice";

	@Autowired
	private SpringMongoConfig config;
	
	public UserWelcomeData getUserData(){
		UserWelcomeData userData = new UserWelcomeData();
		userData.setUserProfile(getUserProfile());
		userData.setBankService(getBankingService());
		return userData;
	}

	private UserProfile getUserProfile() {
		UserProfile userProfile = null;
		try {
			userProfile = config.mongoTemplate().findOne(
					new Query(Criteria.where("deviceId").is("dev-10000")),
					UserProfile.class, USER_PROFILE_COLLECTION);
			System.out.println("User Profile " + userProfile);

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

	/*
	 * private byte[] getUserProfileImage()throws Exception{ DB db =
	 * config.mongoTemplate().getDb(); GridFS gfsPhoto = new GridFS(db,
	 * "photo"); GridFSDBFile imageForOutput = gfsPhoto.findOne("my-image");
	 * InputStream inputStream = imageForOutput.getInputStream(); BufferedImage
	 * originalImage = ImageIO.read(inputStream); ByteArrayOutputStream baos =
	 * new ByteArrayOutputStream(); ImageIO.write( originalImage, "jpg", baos );
	 * baos.flush(); byte[] imageInByte = baos.toByteArray(); baos.close();
	 * return imageInByte; }
	 */

}
