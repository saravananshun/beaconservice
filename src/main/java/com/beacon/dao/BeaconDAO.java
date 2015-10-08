package com.beacon.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.beacon.model.BankService;
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

	@Autowired
	private SpringMongoConfig config;

	public UserWelcomeData getUserData() {
		UserWelcomeData userData = new UserWelcomeData();
		userData.setUserProfile(getUserProfile());
		userData.setBankService(getBankingService());
		return userData;
	}

	private UserProfile getUserProfile() {
		UserProfile userProfile = null;
		try {
			userProfile = config.mongoTemplate().findOne(
					new Query(Criteria.where("imeiNumber").is("IMEI000011")),
					UserProfile.class, USER_PROFILE_COLLECTION);
			System.out.println("User Profile " + userProfile);
				
			
			GridFS fileStore = new GridFS(config.mongoTemplate().getDb(), "filestore");
		    GridFSDBFile gridFile = fileStore.findOne(userProfile.getAccountNumber() + "-image");
		 
		    InputStream in = gridFile.getInputStream();
		         
		    ByteArrayOutputStream out = new ByteArrayOutputStream();
		    int data = in.read();
		    while (data >= 0) {
		      out.write((char) data);
		      data = in.read();
		    }
		    out.flush();
			
			// convert byte array back to BufferedImage
			InputStream imageByteIn = new ByteArrayInputStream(
					out.toByteArray());
			BufferedImage bImageFromConvert = ImageIO.read(imageByteIn);

			ImageIO.write(bImageFromConvert, "jpg", new File(
					"C:/Saro/new-photo.jpg"));
			out.close();
			in.close();
			imageByteIn.close();

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
	    GridFS fileStore = new GridFS(config.mongoTemplate().getDb(), "filestore");
	    GridFSInputFile inputFile = fileStore.createFile(setup.getMultiPart().getInputStream());
	    inputFile.setId(setup.getAccountNumber());
	    inputFile.setFilename(newFileName);
	    inputFile.save();
		
		
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
