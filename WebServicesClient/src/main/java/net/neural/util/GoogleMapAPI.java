package net.zfp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Provides any activity with Google map API
 * @author Youngwook Yoo
 * @since 5.1
 */
public class GoogleMapAPI {

	public GoogleMapAPI(){}
	
	/**
	 * Based on postal code, calls Google map API and get GPS coordinates
	 * @param postalCode
	 * @return GPS coordinates of postal code
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static Double[] getGPS(String postalCode) throws ClientProtocolException, IOException{
		Double[] coordinates = new Double[2];
		
		if (postalCode != null){
			postalCode = postalCode.replace(" ", "");
			HttpClient client = HttpClientBuilder.create().build();
			
			HttpGet get = new HttpGet("http://maps.googleapis.com/maps/api/geocode/json?address=" + postalCode + "&sensor=false");
			HttpResponse response = client.execute(get);
			
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatusLine().getStatusCode());
			}
	 
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
	 
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			
			String jsonText = result.toString();
			
			try{
				JSONObject json = (JSONObject) JSONSerializer.toJSON(jsonText);
				//Check status is 
				if (json.getString("status").equals("OK")){
					JSONArray results = json.getJSONArray("results");
					JSONObject sample = results.getJSONObject(0);
					JSONObject geometry = sample.getJSONObject("geometry");
					JSONObject location = geometry.getJSONObject("location");
					coordinates[0] = location.getDouble("lat");
					coordinates[1] = location.getDouble("lng");
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return coordinates;
	}
}
