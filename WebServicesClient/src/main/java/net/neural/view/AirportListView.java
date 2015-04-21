package net.zfp.view;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import net.zfp.entity.Airport;

@XmlRootElement(name="airportList")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso( {Airport.class} )
public class AirportListView {

	@XmlElement(name="airports")
	private ArrayList<Airport> airportList = new ArrayList<Airport>();

	public ArrayList<Airport> getAirportList() {
		return airportList;
	}

	public void setAirportList(ArrayList<Airport> airportList) {
		this.airportList = airportList;
	}
	
}
