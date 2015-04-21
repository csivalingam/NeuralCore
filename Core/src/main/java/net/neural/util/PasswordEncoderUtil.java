package net.zfp.util;

import java.util.Date;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordEncoderUtil {

	  private transient static final Logger LOG = LoggerFactory.getLogger(PasswordEncoderUtil.class);

	  public static String encodePassword(String rawPass, Object salt){

	    String combination = rawPass + salt;
	    String hash = DigestUtils.shaHex(combination);

	    String shaHexHash = DigestUtils.shaHex(hash);
			for(int i=0; i<4 ; i++){
	      shaHexHash = DigestUtils.shaHex(shaHexHash);
			}

	    return shaHexHash;
	  }

	  public static String generatePasswordResetCode(String email, String salt){

	    String combination = new Date() + email + salt;
	    String resetHash = DigestUtils.md5Hex(combination);

	    return resetHash;
	  }

}
