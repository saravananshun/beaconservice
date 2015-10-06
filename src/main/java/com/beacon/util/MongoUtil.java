package com.beacon.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class MongoUtil {

	public static void main(String[] args) {
		try {
			Connection connect = null;
			 connect = DriverManager
			          .getConnection("jdbc:mysql://ec2-52-11-6-45.us-west-2.compute.amazonaws.com:3306/beacondb?"
			              + "user=beaconpass&password=beaconpass");
			 connect.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
