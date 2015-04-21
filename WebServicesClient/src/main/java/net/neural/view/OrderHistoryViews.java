package net.zfp.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name="OrderHistoryViews")
@XmlAccessorType(XmlAccessType.FIELD)
public class OrderHistoryViews {

	@XmlElement
	private List<OrderHistoryView> orderHistoryView;
	
	@XmlElement
	private ResultView result;
	
	public OrderHistoryViews() {

	}
	
	public List<OrderHistoryView> getOrderHistoryView() {
		return orderHistoryView;
	}

	public void setOrderHistoryView(List<OrderHistoryView> orderHistoryView) {
		this.orderHistoryView = orderHistoryView;
	}

	public ResultView getResult() {
		return result;
	}

	public void setResult(ResultView result) {
		this.result = result;
	}
	
}
