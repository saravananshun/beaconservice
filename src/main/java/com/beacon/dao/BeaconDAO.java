package com.beacon.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.beacon.model.UserProfile;
import com.beacon.springconfig.SpringMongoConfig;
import com.mongodb.DB;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

@Component
public class BeaconDAO {
	
	@Autowired
	private SpringMongoConfig config;
	
	public UserProfile getUserProfile(){
		UserProfile userProfile = new UserProfile();
		try {
			byte[] imageInByte = getUserProfileImage();
			userProfile.setImage(imageInByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userProfile;
	}
	
	private byte[] getUserProfileImage()throws Exception{
		DB db =  config.mongoTemplate().getDb();
		GridFS gfsPhoto = new GridFS(db, "photo");
		GridFSDBFile imageForOutput = gfsPhoto.findOne("my-image");			
		InputStream inputStream = imageForOutput.getInputStream();			
		BufferedImage originalImage = ImageIO.read(inputStream);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write( originalImage, "jpg", baos );
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}

}
