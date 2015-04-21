package net.zfp.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.alert.AlertNews;
import net.zfp.util.AppConstants;

@XmlRootElement(name="Feed")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso(
		{ResultView.class}
)
public class WhatsUpView {

	@XmlElement
	private Integer actual;
	
	@XmlElement
	private Integer total;
	
	@XmlElement
	private Integer not_viewed_count;
	
	@XmlElement(name="news")
	private List<NewsContentView> news = new ArrayList<NewsContentView>();
	
	@XmlElement(name="alerts")
	private List<NewsContentView> alerts = new ArrayList<NewsContentView>();
	
	@XmlElement
	private ResultView result;
	
	public WhatsUpView() {
	}

	public Integer getActual() {
		return actual;
	}

	public void setActual(Integer actual) {
		this.actual = actual;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<NewsContentView> getNews() {
		return news;
	}

	public void setNews(List<NewsContentView> news) {
		this.news = news;
	}

	public Integer getNot_viewed_count() {
		return not_viewed_count;
	}

	public void setNot_viewed_count(Integer not_viewed_count) {
		this.not_viewed_count = not_viewed_count;
	}

	public List<NewsContentView> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<NewsContentView> alerts) {
		this.alerts = alerts;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
	
	
}
