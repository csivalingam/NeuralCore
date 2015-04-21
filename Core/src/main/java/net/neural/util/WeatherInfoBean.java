package net.zfp.util;

import java.io.Serializable;

public class WeatherInfoBean implements Serializable {

	private static final long serialVersionUID = -8068477860131056043L;

	private String city;
	private String code;
	private String weatherDate;
	private String weatherDes;
	private String lowTemp;
	private String highTemp;
	private String currentTemp;

	public String getCurrentTemp() {
		return currentTemp;
	}

	public void setCurrentTemp(String currentTemp) {
		this.currentTemp = currentTemp;
	}

	private String pictureUrl;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWeatherDate() {
		return weatherDate;
	}

	public void setWeatherDate(String weatherDate) {
		this.weatherDate = weatherDate;
	}

	public String getWeatherDes() {
		return weatherDes;
	}

	public void setWeatherDes(String weatherDes) {
		this.weatherDes = weatherDes;
	}

	public String getLowTemp() {
		return lowTemp;
	}

	public void setLowTemp(String lowTemp) {
		this.lowTemp = lowTemp;
	}

	public String getHighTemp() {
		return highTemp;
	}

	public void setHighTemp(String highTemp) {
		this.highTemp = highTemp;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
