package com.beacon.springconfig;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class SpringMongoConfig {
	private String mongoUser = "beacon";
	private String mongoPass = "beacon";
	private String databaseName = "beacondb";
	private String mongoHost = "ds041581.mongolab.com";
	private int mongoPort = 41581;
	
	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		/*return new SimpleMongoDbFactory(new MongoClient("ds041581.mongolab.com", 41581), 
		"beacondb", new UserCredentials("beacon", "beacon"));*/
		
		/*return new SimpleMongoDbFactory(new MongoClient("localhost", 27017), 
				"payment-system");*/
		
		
	    MongoCredential credential = MongoCredential.createCredential(mongoUser, databaseName, mongoPass.toCharArray());
	    ServerAddress serverAddress = new ServerAddress(mongoHost, mongoPort);
	    MongoClient mongoClient = new MongoClient(serverAddress,Arrays.asList(credential)); 
	    SimpleMongoDbFactory simpleMongoDbFactory = new SimpleMongoDbFactory(
	            mongoClient, databaseName);
	    return simpleMongoDbFactory;
	}

	public @Bean MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}
}
