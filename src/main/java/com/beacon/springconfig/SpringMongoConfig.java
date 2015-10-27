package com.beacon.springconfig;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class SpringMongoConfig {
	private String mongoUser = "beacon";
	private String mongoPass = "beacon";
	private String databaseName = "beacondb";
	private String mongoHost = "ds041581.mongolab.com";
	private int mongoPort = 41581;
	private String mongoHostLocal = "localhost";
	private int mongoPortLocal = 27017;
	
	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		/*return new SimpleMongoDbFactory(new MongoClient("ds041581.mongolab.com", 41581), 
		"beacondb", new UserCredentials("beacon", "beacon"));*/
		
		/*return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), 
				"payment-system");*/
		
		
	    MongoCredential credential = MongoCredential.createCredential(mongoUser, databaseName, mongoPass.toCharArray());
	    ServerAddress serverAddress = new ServerAddress(mongoHostLocal, mongoPortLocal);
	    /*MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential),
	    		MongoClientOptions.builder().serverSelectionTimeout(1000).build());*/
	    MongoClient mongoClient = new MongoClient(serverAddress,
	    		MongoClientOptions.builder().serverSelectionTimeout(1000).build()); 
	    SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(
	            mongoClient, databaseName);
	    return simpleMongoDbFactory;
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}
}
