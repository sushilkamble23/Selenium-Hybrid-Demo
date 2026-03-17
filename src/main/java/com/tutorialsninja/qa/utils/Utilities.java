package com.tutorialsninja.qa.utils;

import java.util.Date;

public class Utilities {
	
	public static String generateEmailWithTimeStamp() {
		Date date = new Date();
		return date.toString().replace(" ", "_").replace(":", "_")+"@gmail.com";	
	}

}
