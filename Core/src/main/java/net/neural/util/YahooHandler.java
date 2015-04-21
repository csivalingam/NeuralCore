/**
 * 
 */
package net.zfp.util;
import org.apache.log4j.*;
//import org.apache.commons.lang.StringUtils;
//import org.mortbay.log.Log;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

/**
* For more information, please visit: http://www.crackj2ee.com
* 
*/

public class YahooHandler extends DefaultHandler {
//	 private final Log log = LogFactory.getLog(YahooHandler.class);
	     static  Logger logger  =  Logger.getLogger(YahooHandler.class );    
//	    PropertyConfigurator.configure("log4j.properties " );    
		public ArrayList <WeatherInfoBean> listWeatherInfo = new ArrayList<WeatherInfoBean>();
		WeatherInfoBean weatherInfoBean = new WeatherInfoBean();
		public void startElement(String uri, String localName,
				String qName, Attributes attributes)
				throws SAXException {
				if("yweather:condition".equals(qName)) {
					String s_date = attributes.getValue(3);
					System.out.println("String s_date:"+s_date);
					weatherInfoBean=new WeatherInfoBean();
					weatherInfoBean.setCurrentTemp(attributes.getValue(2));
					weatherInfoBean.setLowTemp(attributes.getValue(2)); //in Current using low field in stead of current 
					weatherInfoBean.setWeatherDes(attributes.getValue(0));
					String strCode=attributes.getValue(1);
					weatherInfoBean.setCode(attributes.getValue(1));
					weatherInfoBean.setWeatherDate("Current");
				//	weatherInfoBean.setWeatherDate(s_date);
					StringBuffer strBuffer = new StringBuffer();
					strBuffer.append("./images/yahooweatherpic/").append(strCode).append(".gif");
					weatherInfoBean.setPictureUrl(strBuffer.toString());
					this.listWeatherInfo.add(weatherInfoBean);
					weatherInfoBean=new WeatherInfoBean();
					try {
						Date publish = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm a z", Locale.CANADA).parse(s_date);
						//new SimpleDateFormat("EEE, dd MMM yyyy hh:mm a z", Locale.US).parse(s_date);
					
					}
					catch (Exception e) {
					e.printStackTrace();
					throw new SAXException("Cannot parse date: " + s_date);
					}
				}
				else if("yweather:forecast".equals(qName)) {
					String s_date = attributes.getValue(1);
					Date date = null;
					try {
//					date = new SimpleDateFormat("dd MMM yyyy", Locale.US).parse(s_date);
					date = new SimpleDateFormat("dd MMM yyyy", Locale.US).parse(s_date);
					}
					catch (Exception e) {
					e.printStackTrace();
					throw new SAXException("Cannot parse date: " + s_date);
					}
					int low = Integer.parseInt(attributes.getValue(2));
					int high = Integer.parseInt(attributes.getValue(3));
					String text = attributes.getValue(4);
					String strDate = attributes.getValue(1);
					String strCode = attributes.getValue(5);
					StringBuffer strBuffer = new StringBuffer();
//					int code = Integer.parseInt(strCode);
//					weatherInfoBean.setCity("Toronto");
					weatherInfoBean.setHighTemp(Integer.toString(high));
					weatherInfoBean.setLowTemp(Integer.toString(low));
					weatherInfoBean.setWeatherDes(text);
					weatherInfoBean.setWeatherDate(strDate);
					//weatherInfoBean.setWeatherDate("Forecast");//Weather 
					strBuffer.append("./images/yahooweatherpic/").append(strCode).append(".gif");
					weatherInfoBean.setPictureUrl(strBuffer.toString());
					this.listWeatherInfo.add(weatherInfoBean);
					weatherInfoBean=new WeatherInfoBean();
				}
		}
}