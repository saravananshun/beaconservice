package com.beacon.dao;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.beacon.model.BankService;
import com.beacon.model.Banker;
import com.beacon.model.BankerSetup;
import com.beacon.model.CustomerServiceQueue;
import com.beacon.model.UserProfile;
import com.beacon.model.UserProfileSetup;
import com.beacon.model.UserToken;
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
	private static final String USER_TOKEN_COLLECTION = "usertoken";
	private static final String BANKERSETUP_COLLECTION = "bankersetup";

	@Autowired
	private SpringMongoConfig config;

	public UserWelcomeData getUserData(String accountNumber) {
		UserWelcomeData userData = new UserWelcomeData();
		userData.setUserProfile(getUserProfile(accountNumber));
		userData.setBankService(getBankingService());
		return userData;
	}

	private UserProfile getUserProfile(String accountNumber) {
		UserProfile userProfile = null;
		try {
			userProfile = config.mongoTemplate().findOne(
					new Query(Criteria.where("accountNumber").is(accountNumber)),
					UserProfile.class, USER_PROFILE_COLLECTION);
			System.out.println("User Profile " + userProfile);

			userProfile.setImageBytes(readUserProfileImage(userProfile.getAccountNumber()));
			userProfile.setTokenNumber("REQ-" + generateUserToken().getTokenNumber());
			insertNewCustomersToServe(userProfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userProfile;
	}
	
	public List <UserProfile> getAllUsers() {
		List <UserProfile> allUsers = null;
		try {
			allUsers = config.mongoTemplate().findAll(UserProfile.class, USER_PROFILE_COLLECTION);
			System.out.println("User Profile " + allUsers);
			
			for(UserProfile user : allUsers){
				user.setImageBytes(readUserProfileImage(user.getAccountNumber()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allUsers;
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
	
	public void insertNewCustomersToServe(UserProfile userProfile) throws Exception {
		CustomerServiceQueue service = new CustomerServiceQueue();
		service.setAccountNumber(userProfile.getAccountNumber());
		service.setFirstName(userProfile.getFirstName());
		service.setImageBytes(userProfile.getImageBytes());
		service.setLastName(userProfile.getLastName());
		service.setStatus("A");
		service.setTokenNumber(userProfile.getTokenNumber());
		
		config.mongoTemplate().insert(service, CUSTOMER_SERVICE_QUEUE);
		
	}
	
	private UserToken generateUserToken()throws Exception{
		UserToken userToken = new UserToken();
	
		try{
			Query query = new Query();
			query.addCriteria(Criteria.where("tokenId").is(100));
			
			Update update = new Update();
			update.set("tokenNumber", 101);
			
			userToken = config.mongoTemplate().findAndModify(
					query, update, 
					new FindAndModifyOptions().returnNew(true), UserToken.class, USER_TOKEN_COLLECTION);
		}catch(Exception e){
				e.printStackTrace();
			}
		return userToken;
	}
	
	public void saveBankerSetup(BankerSetup setup) throws Exception {
		String newFileName = setup.getBranchCode() + "-" + setup.getServiceName() + "-image";
		Banker banker = new Banker();
		banker.setBranchCode(setup.getBranchCode());
		banker.setServiceName(setup.getServiceName());
		banker.setBay(setup.getBay());
		banker.setFirstName(setup.getFirstName());
		banker.setLastName(setup.getLastName());
		
		config.mongoTemplate().insert(banker, BANKERSETUP_COLLECTION);

		// Now let's store the binary file data using filestore GridFS
		GridFS fileStore = new GridFS(config.mongoTemplate().getDb(),
				"filestore");
		GridFSInputFile inputFile = fileStore.createFile(setup.getMultiPart()
				.getInputStream());
		inputFile.setId(newFileName);
		inputFile.setFilename(newFileName);
		inputFile.save();

	}
	
	public Banker getBankerData(String serviceName) {
		Banker bankerData = null;
		try {
			bankerData = config.mongoTemplate().findOne(
					new Query(Criteria.where("serviceName").is(serviceName)),
					Banker.class, BANKERSETUP_COLLECTION);
			System.out.println("Banker Data " + bankerData);

			bankerData.setImageBytes(readBankerImage(serviceName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankerData;
	}
	
	private byte[] readBankerImage(String serviceName) throws Exception {
		GridFS fileStore = new GridFS(config.mongoTemplate().getDb(),
				"filestore");
		GridFSDBFile gridFile = fileStore.findOne("B1-" + serviceName  + "-image");

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
	
	
}
