package net.zfp.util;

public class CommonCalculater {

	
	public static final double getDistanceByCoordinate(
			//Double longitude1, Double latitude1, Double longitude2, Double latitude2 ){
			Double lat1, Double lng1, Double lat2, Double lng2 ){
		
		double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = CommonConstants.RARTH_RADIUS * c;

	    int meterConversion = 1609;

	    return dist * meterConversion / 1000;		
		
	}
	
}
