package net.zfp.util;

import java.util.UUID;

import liquibase.util.MD5Util;

public class KeyGenerationUtil {
	public static String generate64CharacterKey(){
		return MD5Util.computeMD5(UUID.randomUUID().toString()) + MD5Util.computeMD5(UUID.randomUUID().toString());
	}
}
	
