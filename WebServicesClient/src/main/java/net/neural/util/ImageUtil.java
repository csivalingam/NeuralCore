package net.zfp.util;

public class ImageUtil {
	
	public static String parseImageUrl(String s){
		
		String parsed = null;
		if (s != null && !s.equals("null") && s.length()>= 12 && !s.substring(0, 12).equals("/portal-core")){
			parsed = "/portal-core"+ s;
			parsed = parsed.replaceAll("\"","\\\\\"");
		}else{
			if (s != null) parsed = s.replaceAll("\"","\\\\\"");
		}
		
		return parsed;
		
	}
	
	public static String parseApcheImageUrl(String s){
		
		String parsed = null;
			if (s != null) parsed = s.replaceAll("\"","\\\\\"");
		
		
		return parsed;
		
	}
}
