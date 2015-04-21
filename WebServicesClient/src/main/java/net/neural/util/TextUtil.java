package net.zfp.util;

public class TextUtil {
	
	public static String parseString(String s){
		
		String parsed = "";
		
		if (s == null) return parsed;
		
		parsed = s.replaceAll("\"","\\\\\"");
		parsed = parsed.trim().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>");
		
		return parsed;
		
	}
	
	public static String parseHTMLString(String s){
		
		String parsed = "";
		
		if (s == null) return parsed;
		
		parsed = s.replaceAll("\"","\\\"");
		parsed = parsed.trim().replaceAll("(\r\n|\n\r|\r|\n)", "<br/>");
		
		return parsed;
		
	}
}
