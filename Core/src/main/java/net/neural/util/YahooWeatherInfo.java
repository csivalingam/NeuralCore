package net.zfp.util;

import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;

public class YahooWeatherInfo implements Serializable{

	private static final long serialVersionUID = 5501433536359053610L;
	//	public static void main(String args[]){
	static  Logger logger  =  Logger.getLogger(YahooWeatherInfo.class );  
	private String yahooWeatherCode;
	public YahooWeatherInfo(String yahooWeatherCode){
		super();
		this.yahooWeatherCode=yahooWeatherCode;
		
	}
	public ArrayList<WeatherInfoBean> fetchData() {
	//public static void main(String sar[]) {
		YahooHandler handler = new YahooHandler();
				try{
				//    String yahooWeatherURL="http://xml.weather.yahoo.com/forecastrss?u=c&w="+this.yahooWeatherCode;
				URL url = new URL("http://xml.weather.yahoo.com/forecastrss?u=c&w=24073387");
				    
				 //   URL url = new URL(yahooWeatherURL);
					
					InputStream input = url.openStream();
					
					SAXParserFactory factory = SAXParserFactory.newInstance();
					
					factory.setNamespaceAware(false);
					
					SAXParser parser = factory.newSAXParser();
					
					parser.parse(input, handler);
	//				System.out.println("Main try listWeatherInfo length: "+listWeatherInfo.size());
	//				System.out.println("listWeatherInfo length:"+listWeatherInfo.size());
	//				System.out.println(listWeatherInfo.get(1).getCity());
	//				System.out.println(listWeatherInfo.get(0).getHighTemp());
	//				System.out.println(listWeatherInfo.get(1).getHighTemp());
	//				System.out.println(listWeatherInfo.get(0).getLowTemp());
	//				System.out.println(listWeatherInfo.get(1).getLowTemp());
	//				System.out.println(listWeatherInfo.get(0).getWeatherDes());
	//				System.out.println(listWeatherInfo.get(1).getWeatherDes());
					//return listWeatherInfo;
				}catch(Exception e){
				
					System.out.println(e.toString());
				
				}
	//			System.out.println("Here Before Return" + handler.listWeatherInfo.size());
	//			logger.fatal("Fatal error");
					return handler.listWeatherInfo;
				
	}		
				
			
	//		}
	public void setYahooWeatherCode(String yahooWeatherCode) {
		this.yahooWeatherCode = yahooWeatherCode;
	}
	public String getYahooWeatherCode() {
		return yahooWeatherCode;
	}

}
