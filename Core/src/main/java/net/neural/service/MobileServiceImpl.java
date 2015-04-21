package net.zfp.service;

import javax.annotation.Resource;

import net.zfp.dao.EntityDao;
import net.zfp.entity.User;
import net.zfp.entity.mobile.MobileDevice;
import net.zfp.form.MobileDeviceForm;
import net.zfp.util.AppConstants;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class MobileServiceImpl implements MobileService {

	
	@Resource
	private EntityDao<MobileDevice> mobileDeviceDao;
	@Resource
	private EntityDao<User> userDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setMobileDeviceInfo(MobileDeviceForm mobileDeviceForm){
		ResultView rv = new ResultView();
		
		//Get Alert using alert Id
		if (mobileDeviceForm != null){
			try{
				MobileDevice md = mobileDeviceDao.getMobileDeviceByIdentifier(mobileDeviceForm.getIdentifier());
				
				if (md == null){
					md = new MobileDevice();
					User user = userDao.findById(mobileDeviceForm.getMemberId());
					md.setUser(user);
					md.setCreated(mobileDeviceForm.getCreated());
					md.setOS(mobileDeviceForm.getOS());
					md.setHardwareModel(mobileDeviceForm.getHardwareModel());
					md.setPhoneName(mobileDeviceForm.getPhoneName());
					md.setCarrier(mobileDeviceForm.getCarrier());
					md.setIdentifier(mobileDeviceForm.getIdentifier());
					md.setAppversion(mobileDeviceForm.getAppversion());
					mobileDeviceDao.save(md);
					
				}else{
					User user = userDao.findById(mobileDeviceForm.getMemberId());
					md.setUser(user);
					md.setCreated(mobileDeviceForm.getCreated());
					md.setOS(mobileDeviceForm.getOS());
					md.setHardwareModel(mobileDeviceForm.getHardwareModel());
					md.setPhoneName(mobileDeviceForm.getPhoneName());
					md.setCarrier(mobileDeviceForm.getCarrier());
					md.setIdentifier(mobileDeviceForm.getIdentifier());
					md.setAppversion(mobileDeviceForm.getAppversion());
					mobileDeviceDao.save(md, true);
					
				}
				
				rv.fill(AppConstants.SUCCESS, "Mobile Device has been saved.");
			}catch(Exception e){
				rv.fill(AppConstants.FAILURE, e.getMessage());
			}
			
		}else{
			rv.fill(AppConstants.FAILURE, "Mobile Device is invalid.");
		}
		
		return rv;
		
	}
	
}
