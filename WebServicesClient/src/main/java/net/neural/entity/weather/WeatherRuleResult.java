package net.zfp.entity.weather;

public class WeatherRuleResult {
	private boolean heatAlert;
	private boolean coldAlert;
	
	public boolean isHeatAlert() {
		return heatAlert;
	}
	public void setHeatAlert(boolean heatAlert) {
		this.heatAlert = heatAlert;
	}
	public boolean isColdAlert() {
		return coldAlert;
	}
	public void setColdAlert(boolean coldAlert) {
		this.coldAlert = coldAlert;
	}
	
	
}
