package net.zfp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.AccountSource;
import net.zfp.entity.Source;
import net.zfp.entity.User;
import net.zfp.entity.category.Category;
import net.zfp.entity.community.Community;
import net.zfp.entity.community.CommunityProduct;
import net.zfp.entity.connector.Connector;
import net.zfp.entity.electricity.ElectricityDataMonth;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.offer.OfferBehavior;
import net.zfp.entity.offer.OfferBehaviorAttribute;
import net.zfp.entity.product.Product;
import net.zfp.entity.product.ProductSKU;
import net.zfp.entity.provider.Provider;
import net.zfp.entity.provider.ProviderDevice;
import net.zfp.entity.provider.ProviderDeviceMobileReference;
import net.zfp.entity.provider.ProviderSource;
import net.zfp.entity.segment.Segment;
import net.zfp.service.ProductService;
import net.zfp.service.UtilityService;
import net.zfp.util.AppConstants;
import net.zfp.util.ImageUtil;
import net.zfp.util.TextUtil;
import net.zfp.view.DeviceView;
import net.zfp.view.MobileDeviceView;
import net.zfp.view.ProductView;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Resource
public class ProviderServiceImpl implements ProviderService {
	
	@Resource
    private CommunityService communityService;
	
	@Resource
    private SegmentService segmentService;
	
	@Resource
	private EntityDao<Community> communityDao;
	
	@Resource
	private EntityDao<Provider> providerDao;
	
	@Resource
	private EntityDao<AccountSource> accountSourceDao;
	
	@Resource
	private EntityDao<Connector> connectorDao;
	
	@Resource
	private EntityDao<Product> productDao;
	
	@Resource
	private EntityDao<Offer> offerDao;
	
	@Resource
	private EntityDao<User> userDao;
	/**
	 * (non-Javadoc)
	 * @see net.zfp.service.ProviderService#checkProviderSource(java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView checkProviderSource(Long accountId, String providerCode){
		ResultView rv = new ResultView();
		
		Provider provider = providerDao.getProvider(providerCode);
		
		List<Source> providerSources = providerDao.getProviderSources(accountId, provider.getId());
		
		if (providerSources.isEmpty()){
			rv.fill(AppConstants.FAILURE, "No Provider Sources");
		}else{
			rv.fill(AppConstants.SUCCESS, "Provider exists");
		}
		return rv;
	}
	
	/**
	 * (non-Javadoc)
	 * @see net.zfp.service.ProviderService#getProviderSourceName(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView getProviderSourceName(Long accountId, String sourceCode, String providerCode, String providerType){
		ResultView rv = new ResultView();
		
		List<Source> providerSources = providerDao.getProviderSources(accountId, sourceCode, providerCode, providerType);
		
		if (providerSources.isEmpty()){
			rv.fill(AppConstants.FAILURE, "No Provider Sources");
		}else{
			rv.fill(AppConstants.SUCCESS, providerSources.get(0).getName());
		}
		return rv;
	}
	
	/**
	 * (non-Javadoc)
	 * @see net.zfp.service.ProviderService#getAllProviderDevices(java.lang.Long, java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<DeviceView> getAllProviderDevices(Long accountId, String serverName){
		
		List<Community> communities = new ArrayList<Community>();
		if (accountId != null){
			 communities = communityService.getMyCommunitiesById(accountId);
		}else{
			 communities = communityService.getMyCommunities(serverName);
		}
				
		List<Provider> providers = providerDao.getAllProviders(communities);
		
		if (communities.isEmpty()){
			return new ArrayList<DeviceView>();
		}else{
			return getProviderDevicesHelper(providers, accountId, serverName);
		}
		
	}
	
	private List<DeviceView> getProviderDevicesHelper(List<Provider> providers, Long accountId, String serverName){
		List<DeviceView> dvs = new ArrayList<DeviceView>();
		
		Long communityId = null;
		//Segment
		List<Segment> segments = null;
		
		try{
			User user = userDao.findById(accountId);
			communityId = user.getDefaultCommunity();
			segments = segmentService.getMemberSegment(accountId);
		}catch(Exception e){
			communityId = communityDao.getCommunitId(serverName);
			segments = segmentService.getDefaultSegment(communityId);
		}
		
		
		//For each provider get provider devices
		for (Provider provider: providers){
			List<ProviderDevice> providerDevices = providerDao.getProviderDevicesByProviderId(provider.getId(), segments);
			
			if (providerDevices != null && providerDevices.size() > 0){
				
				DeviceView dv = null;
				for (ProviderDevice providerDevice : providerDevices){
					//Based on deviceAttribute "mobileapp", "connector", "products" get devices
					if (providerDevice.getDeviceAttribute().getAttribute().equals(AppConstants.DEVICEATTRIBUTE_MOBILE_APP)){
						dv = addingMobileAppDevices(accountId, providerDevice, serverName);
						if (dv != null){
							dv.setRegistered(hasSourceRegistered(accountId, provider.getId()));
							dv.setType(1);
							dv.setProviderCode(provider.getCode());
							dvs.add(dv);
						}
					}else if (providerDevice.getDeviceAttribute().getAttribute().equals(AppConstants.DEVICEATTRIBUTE_CONNECTOR)){
						dv = addingConnectorDevices(accountId, providerDevice);
						if (dv != null){
							dv.setType(2);
							dv.setRegistered(hasSourceRegistered(accountId, provider.getId()));
							dv.setProviderCode(provider.getCode());
							dvs.add(dv);
						}
					}else if (providerDevice.getDeviceAttribute().getAttribute().equals(AppConstants.DEVICEATTRIBUTE_PRODUCT)){
						dv = addingProductDevices(accountId, providerDevice);
						if (dv != null){
							dv.setType(3);
							dv.setRegistered(hasSourceRegistered(accountId, provider.getId()));
							dv.setProviderCode(provider.getCode());
							dvs.add(dv);
						}
					}
					
				}
			}
			
		}
		
		//Comparator device views rank
		Comparator<DeviceView> comparator = new Comparator<DeviceView>(){
			public int compare(DeviceView deviceView1, DeviceView deviceView2){
				return deviceView1.getRank().compareTo(deviceView2.getRank());
			}
		};
		Collections.sort(dvs, comparator);
		
		//Here we want the community first
		List<DeviceView> mycommunityDeviceView = new ArrayList<DeviceView>();
		List<DeviceView> nonmycommunityDeviceView = new ArrayList<DeviceView>();
		
		for (DeviceView dv : dvs){
			if (dv.getCommunityId().equals(communityId)) mycommunityDeviceView.add(dv);
			else nonmycommunityDeviceView.add(dv);
		}
		
		dvs.clear();
		dvs.addAll(mycommunityDeviceView);
		dvs.addAll(nonmycommunityDeviceView);
		
		//Comparator device views alphabetical order
		Comparator<DeviceView> RegisteredComparator = new Comparator<DeviceView>(){
			public int compare(DeviceView deviceView1, DeviceView deviceView2){
				return deviceView1.getRegistered().compareTo(deviceView2.getRegistered());
			}
		};
		Collections.sort(dvs, RegisteredComparator);
		
		return dvs;
	}
	
	/**
	 * (non-Javadoc)
	 * @see net.zfp.service.ProviderService#getProviderDevices(java.lang.Long, java.lang.String, java.lang.String)
	 */
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<DeviceView> getProviderDevices(Long accountId, String providerType, String serverName){
		List<DeviceView> dvs = new ArrayList<DeviceView>();
		
		//Get Providers based on provider Type
		List<Provider> providers = providerDao.getProvidersByType(providerType);
		if (providers != null && providers.size() > 0){
			
		}
		
		dvs.addAll(getProviderDevicesHelper(providers, accountId, serverName));
		return dvs;
	}
	
	private Boolean hasSourceRegistered(Long accountId, Long providerId){
		
		//AccountSource status is active accountId, providerousrce
		
		if (accountId == null) return false;
		
		List<Source> sources = providerDao.getProviderActiveSources(accountId, providerId);
		
		Boolean registered = false;
		if (!sources.isEmpty()){
			registered = true;
		}
		
		return registered;
	}
	private DeviceView addingConnectorDevices(Long accountId, ProviderDevice pd){
		
		
		//This is mobile app so the go in to product and find upc
		
		if ( pd != null){
			DeviceView dv = new DeviceView();
			if (pd.getRank() != null) dv.setRank(pd.getRank());
			else dv.setRank(Integer.MAX_VALUE);
			if (pd.getProvider().getCommunity() != null) dv.setCommunityId(pd.getProvider().getCommunity().getId());
			dv.setName(TextUtil.parseString(pd.getName()));
			dv.setCode(pd.getDeviceAttributeValue());
			dv.setShortDescription(TextUtil.parseString(pd.getShortDescription()));
			dv.setLongDescription(TextUtil.parseString(pd.getLongDescription()));
			dv.setSmallImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pd.getSmallImageUrl()));
			dv.setLargeImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pd.getLargeImageUrl()));
			
			dv.setExternalReference(pd.getReference());
			//Check if there is an offer associated with
			
			OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_CONNECT, AppConstants.BEHAVIOR_TRACKERTYPE_CONNECTOR_CODE);
			dv.setOfferPoints(0);
			
			/*
			if (ob != null){
				 Offer offer = offerDao.getOfferByBehaviorAttribute(ob.getId(), dv.getCode());
				
				 if (offer != null){
					 System.out.println("OFFER :: " + offer.getId() + ":: " + dv.getCode());
					 dv.setOfferPoints(offer.getValue().intValue());
				 }
			 }
			 */
			return dv;
		}else{
			return null;
		}
		
	}

	private DeviceView addingProductDevices(Long accountId, ProviderDevice pd){
		
		System.out.println("Adding product devices...");
		if ( pd != null){
			DeviceView dv = new DeviceView();
			if (pd.getRank() != null) dv.setRank(pd.getRank());
			else dv.setRank(Integer.MAX_VALUE);
			if (pd.getProvider().getCommunity() != null) dv.setCommunityId(pd.getProvider().getCommunity().getId());
			dv.setId(pd.getId());
			dv.setName(TextUtil.parseString(pd.getName()));
			dv.setUPC(pd.getDeviceAttributeValue());
			dv.setShortDescription(TextUtil.parseString(pd.getShortDescription()));
			dv.setLongDescription(TextUtil.parseString(pd.getLongDescription()));
			dv.setSmallImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pd.getSmallImageUrl()));
			dv.setLargeImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pd.getLargeImageUrl()));
			dv.setAmount(pd.getUnitPrice());
			
			System.out.println(pd.getProvider().getCode() + " : " + AppConstants.PROVIDER_JAWBONE);
			
			if (pd.getProvider().getCode().equals(AppConstants.PROVIDER_JAWBONE)){
				String externalReference = AppConstants.PRODUCT_JAWBONE_OAUTH;
				externalReference = externalReference.replace("SITEURL", "https://global.goodcoins.ca");
				externalReference = externalReference.replace("CLIENTID", AppConstants.PRODUCT_JAWBONE_CLIENT_ID);
				externalReference = externalReference.replace("ACCOUNTID", accountId+"");
				dv.setExternalReference(externalReference);
			}else{
				dv.setExternalReference(pd.getReference());
			}
			
			System.out.println(dv.getExternalReference());
			//Check if there is an offer associated with 
			OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_BUY, AppConstants.BEHAVIOR_TRACKERTYPE_UPC);
			dv.setOfferPoints(0);
			/*
			if ( ob != null){
				 Offer offer = offerDao.getOfferByBehaviorAttribute(ob.getId(), pd.getDeviceAttributeValue());
				 if (offer != null) dv.setOfferPoints(offer.getValue().intValue());
			 }
			*/		 
			return dv;
		}else{
			return null;
		}
		
	}

	private DeviceView addingMobileAppDevices(Long accountId, ProviderDevice pd, String  serverName){
		
		//This is mobile app so the go in to product and find upc
		if ( pd != null){
			DeviceView dv = new DeviceView();
			if (pd.getRank() != null) dv.setRank(pd.getRank());
			else dv.setRank(Integer.MAX_VALUE);
			if (pd.getProvider().getCommunity() != null) dv.setCommunityId(pd.getProvider().getCommunity().getId());
			dv.setId(pd.getId());
			dv.setName(TextUtil.parseString(pd.getName()));
			dv.setUPC(pd.getDeviceAttributeValue());
			dv.setShortDescription(TextUtil.parseString(pd.getShortDescription()));
			dv.setLongDescription(TextUtil.parseString(pd.getLongDescription()));
			dv.setSmallImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pd.getSmallImageUrl()));
			dv.setLargeImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pd.getLargeImageUrl()));
			dv.setAmount(pd.getUnitPrice());
			dv.setReference(TextUtil.parseString(pd.getReference()));
			
			/*
			 * Check to see if mobile reference table 
			 */
			List<ProviderDeviceMobileReference> providerDeviceMobileReferences = providerDao.getProviderDeviceMobileReferences(pd.getId());
			
			if (!providerDeviceMobileReferences.isEmpty()){
				List<MobileDeviceView> mobileDeviceViews = new ArrayList<MobileDeviceView>();
				
				for (ProviderDeviceMobileReference pdmr : providerDeviceMobileReferences){
					MobileDeviceView mobileDeviceView = new MobileDeviceView();
					mobileDeviceView.setImage(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(pdmr.getImageUrl()));
					mobileDeviceView.setReference(pdmr.getReference());
					
					mobileDeviceViews.add(mobileDeviceView);
				}
				
				dv.setMobileDeviceViews(mobileDeviceViews);
			}
			
			
			if (pd.getProvider().getCode().equals(AppConstants.PROVIDER_MOVES)){
				String externalReference = AppConstants.MOBILE_MOVES_EXTERNAL;
				externalReference = externalReference.replace("SITEURL", "https://global.goodcoins.ca");
				externalReference = externalReference.replace("CLIENTID", AppConstants.MOBILE_MOVES_CLIENT_ID);
				externalReference = externalReference.replace("ACCOUNTID", accountId+"");
				dv.setExternalReference(externalReference);
			}else{
				dv.setExternalReference(pd.getReference());
			}
			
			
			//Check if there is an offer associated with 
			OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_DOWNLOAD, AppConstants.BEHAVIOR_TRACKERTYPE_UPC);
			dv.setOfferPoints(0);
			/*
			if ( ob != null){
				 Offer offer = offerDao.getOfferByBehaviorAttribute(ob.getId(), pd.getDeviceAttributeValue());
				 if (offer != null) dv.setOfferPoints(offer.getValue().intValue());
			 }
			*/
			return dv;
		}else{
			return null;
		}
		
	}
}


