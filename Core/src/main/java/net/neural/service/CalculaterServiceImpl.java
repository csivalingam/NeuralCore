package net.zfp.service;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Airport;
import net.zfp.entity.CalculaterResult;
import net.zfp.entity.User;
import net.zfp.form.FlightForm;
import net.zfp.service.CalculaterService;
import net.zfp.util.CommonCalculater;
import net.zfp.util.CommonConstants;
import net.zfp.view.AirportListView;
import net.zfp.view.CalculaterResultView;
import net.zfp.view.UserView;

public class CalculaterServiceImpl implements CalculaterService {

	private static final String AIR_CANADA = "aircanada.zerofootprint.net";
	private static final String AIR_CANADA_VACATION = "acv.zerofootprint.net";
	private static final String ZEROFOOTPRINT ="portal.zerofootprint.net";
	
	@Resource
	private EntityDao<Airport>							airportDao;
	@Resource
	private EntityDao<CalculaterResult>					calculaterResultDao;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public
	AirportListView getAllAirportList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public AirportListView getAirportList(String keyword) {

		return null;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public CalculaterResultView getCarbonCalculater(FlightForm flight) {
		
		Airport aird = airportDao.findById(flight.getDepartureId());
		Airport aira = airportDao.findById(flight.getArrivalId());
		
		double dis = 1000;
		try{
			dis = CommonCalculater.getDistanceByCoordinate(aird.getLatitude(), aird.getLongitude(), aira.getLatitude(), aira.getLongitude());
		}catch(Exception e){
			System.out.println("We can get distance for " + aird.getCity() + " to " + aira.getCity() + ", because " + e);
		}
		
		System.out.println("---Air line departure from " + aird.getCity() + " to " + aira.getCity() + " dis is " + dis);
		System.out.println("---Air line position [" + aird.getLatitude() + " " + aird.getLongitude() + "] , [" + aira.getLatitude() + " " + aira.getLongitude() + "]");
		
		double EF = 0.0;
		if( dis<1108){
			EF = ( dis * CommonConstants.FACTOR_AIR_A + CommonConstants.FACTOR_AIR_B ) / dis ;
		}else{
			EF = ( dis * CommonConstants.FACTOR_AIR_C + CommonConstants.FACTOR_AIR_D ) / dis ;
		}
		
		if(flight.getCommunityName()!=null && (flight.getCommunityName().toLowerCase().equals(AIR_CANADA) || flight.getCommunityName().toLowerCase().equals(AIR_CANADA_VACATION))){
			EF = dis * EF * flight.getPassengers() * flight.getFlightType();
		}else{
			EF = dis * EF * flight.getPassengers() * flight.getFlightType() * (CommonConstants.FACTOR_AIR_RF + CommonConstants.FACTOR_AIR_UF);
		}
		
		CalculaterResultView crv = new CalculaterResultView();
		
		if(flight.getPayment()!=null){
			System.out.println("---Write flight payment info to DB. payment :  " + flight.getPayment());
			CalculaterResult cr = new CalculaterResult();
			cr.setAccountId(flight.getArrivalId());
			cr.setEmail(flight.getEmail());
			cr.setPayment(flight.getPayment());
			cr.setTripType("flight");
			
			calculaterResultDao.save(cr);
			
		}
		
		crv.setConfirmNumber( Integer.toString( (int)( 1000000 + Math.random()*100000 )) );
		crv.setUserId((int)( 100 + Math.random()*100 ));
		crv.setCarbon(EF);
		crv.setCarbonUnit("kg CO2e");
		crv.setCost(0);
		crv.setCostUnit("CAD");
		
		return crv;
	}

	
	
}
