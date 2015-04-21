package net.zfp.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class SystemProperties {

	public static String SERVER_REAL_PATH = "/mnt/connection.properties";
	public static String SERVER_CONTEXT_NAME = "http://localhost/";
	
	public static String MAILING_LIST_FILE;
	public static String PDF_JOB_FILE;

	public static String SMTP_HOST;
	public static String SMTP_PORT;
	public static String MAIL_FRM_ADDR;
	public static String SMTP_USR_NAME;
	public static String SMTP_USR_PSWD;
	public static String MAIL_CC_ADDR;

	public static String TMP_FILE_PATH;
	public static String IMAGE_PATH;
	public static String PREV_TIME;

	private static Properties instance = null;

	private SystemProperties() {

	}

	public static Properties getInstance(){
		if(instance==null){
			initInstance(SERVER_REAL_PATH);
		}
		return instance;
	}
	
	
	public static String getProp(String prop){
		return instance.getProperty(prop);
	}
	
	public static Properties initInstance(String confile) {
		if (instance == null) {
			instance = new Properties();
			try {

				instance.load(new FileInputStream(confile));
				// prop.load(new FileInputStream(SERVER_PATH);
				MAILING_LIST_FILE = instance.getProperty("MailingListFile");
				PDF_JOB_FILE = instance.getProperty("pdfJobFile");
				
				TMP_FILE_PATH = instance.getProperty("tempFilePath");
				IMAGE_PATH = instance.getProperty("imagePath");
				//PREV_TIME = prop.getProperty("revTime");

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
}
