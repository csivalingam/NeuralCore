package net.zfp.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.PaymentTransaction;
import net.zfp.entity.Translation;
import net.zfp.entity.carbon.CarbonTransaction;
import net.zfp.entity.carbon.CarbonTransactionDetail;
import net.zfp.entity.community.Community;
import net.zfp.entity.community.CommunityProduct;
import net.zfp.entity.product.Product;
import net.zfp.entity.salesorder.SalesOrder;
import net.zfp.entity.salesorder.SalesOrderBilling;
import net.zfp.entity.salesorder.SalesOrderDetail;
import net.zfp.form.CarbonDetailForm;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.service.CarbonService;
import net.zfp.service.UtilityService;
import net.zfp.util.AppConstants;
import net.zfp.util.DateParserUtil;
import net.zfp.util.DateUtil;
import net.zfp.util.ImageUtil;
import net.zfp.util.LocaleUtil;
import net.zfp.util.SystemProperties;
import net.zfp.view.CarbonTableView;
import net.zfp.view.ProductView;
import net.zfp.view.ResultView;
import net.zfp.view.SalesOrderView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Named
@net.bull.javamelody.MonitoredWithSpring
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
public class CarbonServiceImpl implements CarbonService {
	
	@Resource
	private EntityDao<Domain> domainDao;

	@Resource
	private EntityDao<Translation> translationDao;
	
	@Resource
	private EntityDao<SalesOrder> salesOrderDao;

	@Resource
	private EntityDao<PaymentTransaction> paymentTransactionDao;

	@Resource
	private EntityDao<SalesOrderDetail> salesOrderDetailDao;
	
	@Resource
	private EntityDao<Product> productDao;
	
    @Inject
    private NotificationService notificationService;

	@Resource
    private UtilityService utilityService;
    
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public List<ProductView> getCarbonProjects(String domainName, String language){
		List<ProductView> pvs = new ArrayList<ProductView>();
		//get community code
		Community community = domainDao.getCommunity(domainName);
		List<CommunityProduct> cps = domainDao.getCommunityProducts(community.getCode());
		
		if (cps != null && cps.size() > 0){
			for (CommunityProduct cp : cps){
				ProductView pv = new ProductView();
				Product product = productDao.getProductByUPC(cp.getUPC());
				if (product != null && product.getProductType().getId() == 1l){
					
					String translation = translationDao.findTranslation(product.getTranslationKey(), community.getId(), 2l, language, 3l).getTranslation();
					
					if (product.getTranslationKey() != null) pv.setName(translation);
					else pv.setName(product.getName());
					pv.setSmallImageUrl(ImageUtil.parseImageUrl(product.getSmallImageUrl()));
					pv.setUnitPrice(product.getUnitPrice());
					pv.setShortDescription(product.getShortDescription());
					pv.setId(product.getId());
					pv.setLinkUrl(product.getLinkUrl());
					pvs.add(pv);
				}
			}
		}
		
		return pvs;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public SalesOrderView getCarbonProjectsTransaction(Long transactionId,  String language){
		SalesOrderView tv = new SalesOrderView();
		List<ProductView> pvs = new ArrayList<ProductView>();
		//Get carbontransactionDetails
		
		SalesOrder ct = salesOrderDao.findById(transactionId);
		
		tv.setName(ct.getUser().getFirstName() + " " + ct.getUser().getLastName());
		tv.setAddress(ct.getUser().getCity() + ", " + ct.getUser().getProvince() + " " + ct.getUser().getPostalCode() + " " + ct.getUser().getCountry());
		System.out.println(ct.getUser().getFirstName() + " " + ct.getUser().getLastName() + " :: " + ct.getQuantity().toString() + " :: " + DateUtil.printCalendar2(ct.getCreated()));
		if (ct.getReceiptUrl() != null) tv.setPdfURL(ct.getReceiptUrl());
		else{
			tv.setPdfURL(utilityService.generateCarbonCertificate(ct.getId(), ct.getUser().getFirstName() + " " + ct.getUser().getLastName(), ct.getQuantity().toString(), DateUtil.printCalendar2(ct.getCreated())));
		}
			
		List<CarbonTransactionDetail> ctds = salesOrderDetailDao.findCarbonTransactionDetail(transactionId);
		if (ctds != null && ctds.size() > 0){
			for (CarbonTransactionDetail ctd : ctds){
				ProductView pv = new ProductView();
				Product product = productDao.getProductByUPC(ctd.getUPC());
				if (product != null){
					if (product.getTranslationKey() != null) pv.setName(translationDao.findTranslation(product.getTranslationKey(), ct.getCommunity().getId(), 2l, language, 3l).getTranslation());
					else pv.setName(product.getName());
				}
				else pv.setName(ctd.getUPC());
				pv.setCarbonValue(ctd.getQuantity());
				pv.setPriceValue(ctd.getTotalSalePrice());
				pv.setUnitPrice(ctd.getUnitSalePrice());
				pv.setId(ctd.getId());
				pvs.add(pv);
				
			}
			
		}
		tv.setProductViews(pvs);
		return tv;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public ResultView sendCarbonEmail(Long transactionId,  String language){
		
		System.out.println("****************************Entered getCarbonTableViews...");
		
		final DecimalFormat CARBON = new DecimalFormat("###,###,###,###.###"); //new DecimalFormat("###,###,###,###.###");
		final DecimalFormat COST   = new DecimalFormat("$###,###,###,##0.00"); //new DecimalFormat("$###,###,###,###.##");
		
		List<CarbonTableView> ctvs = new ArrayList<CarbonTableView>();
		//Get Latest of CarbonTransaction based on... email, totalCost, domainName
		SalesOrder ct = salesOrderDao.findTransactionByOrderNumber(transactionId);
		
		//From CarbonTransaction get details
		List<SalesOrderDetail> ctds = salesOrderDetailDao.getTransactionDetails(ct.getOrderNumber());
		//From details get carbonTable View
		
		System.out.println("CarbonTransactionDetails :: :: :: :: " + ctds.size());
		
		for (SalesOrderDetail ctd : ctds){
			//Product name based on sku
			CarbonTableView ctv = new CarbonTableView();
			ctv.setCarbonValue(CARBON.format(ctd.getQuantity()));
			ctv.setUnitPrice(COST.format(ctd.getUnitSalePrice()));
			ctv.setTotalPrice(COST.format(ctd.getTotalSalePrice()));
			Product product = productDao.getProduct(ctd.getUPC(), AppConstants.PRODUCT_TYPE_CARBON_PROJECTS);
			Translation projectTranslation = translationDao.findTranslation(product.getTranslationKey(), null, 2l, language, 3l);
			if (projectTranslation != null) ctv.setProjectName(projectTranslation.getTranslation());
			else ctv.setProjectName(product.getName());
			
			ctvs.add(ctv);
		}
		
		
		System.out.println("**********************carbontable views :: " + ctvs.size());
		//collatingData(ct);	
		String name = "";
        if (ct.getUser().getFirstName()!=null){
        	name = ct.getUser().getFirstName();
        	if (ct.getUser().getLastName()!=null){
        		name += " " + ct.getUser().getLastName();
        	}
        }else{
        	name = ct.getUser().getEmail();
        }
        
        String address = "";
        SalesOrderBilling sob = salesOrderDao.findBillingByOrderNumber(ct.getOrderNumber());
        if(sob.getCity()!=null) address += " " + sob.getCity();
        if(sob.getProvince()!=null) address += ", " + sob.getProvince();
        if(sob.getPostalCode()!=null) address += "   " + sob.getPostalCode();
        if(sob.getCountry()!=null) address += " " + sob.getCountry();
        
        
        String taxType = null;
        if (ct.getTaxType() != null && !ct.getTaxType().equals("")) taxType = ct.getTaxType();
        notificationService = new NotificationServiceImpl();
        
        String cFile = notificationService.sendCarbonPaymentConfirmEmail( 
        		"Zerofootprint Carbon <carbon@zerofootprint.net>", ct.getUser().getEmail(),
        		name, address,
        		ctvs, 
        		COST.format(ct.getSubTotalCost()), COST.format(ct.getTax()), taxType, 
        		CARBON.format(ct.getQuantity()), COST.format(ct.getTotalCost()), language
        		);
        
        ct.setReceiptUrl(SystemProperties.getProp("webServerUrl") + ":" + SystemProperties.getProp("webPort") 
                + SystemProperties.SERVER_CONTEXT_NAME + "/certificate/" + cFile);
        
        salesOrderDao.save(ct, true);
        
        ResultView rv = new ResultView();
		rv.setResultCode(AppConstants.SUCCESS);
		
		return rv;
		
	}
	
	private void collatingData (CarbonTransaction carbonTransaction){
    	//if (carbonTransaction.getEmail()==null) carbonTransaction.setEmail("support@zerofootprint.net");
        if (carbonTransaction.getFirstName()==null) carbonTransaction.setFirstName(carbonTransaction.getEmail());
        if (carbonTransaction.getAddress1()==null) carbonTransaction.setAddress1("unknown");
        
        if (carbonTransaction.getCarbon()==null) carbonTransaction.setCarbon(0d);
        if (carbonTransaction.getHst()==null) carbonTransaction.setHst(0d);
        if (carbonTransaction.getSubTotalCost()==null) carbonTransaction.setSubTotalCost(0d);
        if (carbonTransaction.getTotalCost()==null) carbonTransaction.setTotalCost(0d);
        
        /*
        if (carbonTransaction.getProjectForestCo2()==null) carbonTransaction.setProjectForestCo2(0d);
        if (carbonTransaction.getProjectForestTotal()==null) carbonTransaction.setProjectForestTotal(0d);
        if (carbonTransaction.getProjectForestPrice()==null) carbonTransaction.setProjectForestPrice(0d);
        
        if (carbonTransaction.getProjectLandfillCo2()==null) carbonTransaction.setProjectLandfillCo2(0d);
        if (carbonTransaction.getProjectLandfillPrice()==null) carbonTransaction.setProjectLandfillPrice(0d);
        if (carbonTransaction.getProjectLandfillTotal()==null) carbonTransaction.setProjectLandfillTotal(0d);
        
        if (carbonTransaction.getProjectTireCo2()==null) carbonTransaction.setProjectTireCo2(0d);
        if (carbonTransaction.getProjectTirePrice()==null) carbonTransaction.setProjectTirePrice(0d);
        if (carbonTransaction.getProjectTireTotal()==null) carbonTransaction.setProjectTireTotal(0d);
		
		*/
	}
	
	private void sendEmailByVelo(CarbonTransaction carbonTransaction){
		
        StringBuffer sb = null;
        HttpURLConnection conn = null;
        BufferedReader br = null;
        
        try {

            //String url = "http://23.23.178.36:9090/notify/carbonpaymentthankyou?"; 
            //String url = "http://localhost:9090/notify/carbonpaymentthankyou?"; 
            String url = "http://192.168.4.50:8080/notify/carbonpaymentthankyou?"; 
            url += "from=henry.zfp@gmail.com&to=" + carbonTransaction.getEmail();
            if (carbonTransaction.getFirstName()!=null) url += "&name=" + carbonTransaction.getFirstName();
            //if (carbonTransaction.getLastName()!=null) url += "&name=" + carbonTransaction.getLastName();
            url += "&address=" + carbonTransaction.getAddress1();
            url += "&subTotal=" + carbonTransaction.getSubTotalCost();
            url += "&carbon=" + carbonTransaction.getCarbon();
            url += "&hst=" + carbonTransaction.getHst();
            url += "&total=" + carbonTransaction.getTotalCost();
            /*
            url += "&report=" +carbonTransaction.getProjectForestCo2()+",";
            url += carbonTransaction.getProjectForestPrice()+",";
            url += carbonTransaction.getProjectForestTotal()+",";
            
            url += carbonTransaction.getProjectLandfillCo2()+",";
            url += carbonTransaction.getProjectLandfillPrice()+",";
            url += carbonTransaction.getProjectLandfillTotal()+",";
            
            url += carbonTransaction.getProjectTireCo2()+",";
            url += carbonTransaction.getProjectTirePrice()+",";
            url += carbonTransaction.getProjectTireTotal();
            */
            String type = "text/plain";
//            String data = URLEncoder.encode("user", "UTF-8") + "="
//                    + URLEncoder.encode("myUserName", "UTF-8") + "&"
//                    + URLEncoder.encode("submit", "UTF-8") + "="
//                    + URLEncoder.encode("Submit", "UTF-8");


            URL requestUrl = new URL(url);
            conn = (HttpURLConnection) requestUrl.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            //conn.setRequestMethod( "POST");
            conn.setRequestProperty( "Content-Type", type );

//            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());
//            osw.write(data);
//            osw.flush();

            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String in = "";
            sb = new StringBuffer();

            while ((in = br.readLine()) != null) {
                sb.append(in + "\n");
            }
//            osw.close();
            br.close();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
        	if (conn!=null) conn.disconnect();
        	if (br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
		}
        System.out.println("************ Send email : " + sb.toString());

		
	}
	
	/*
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<CarbonTransaction> getAllCarbonTransaction(String communityName) {

		Long communityId = null;
		try {
			communityId = domainDao.getCommunitId(communityName);
		}catch (NoResultException ne){
			System.out.println("************ Community no found.");
		}catch (Exception e) {
			System.out.println("************ Get error in UtilityService for Community. Error: " + e);
		}
		
		List<CarbonTransaction> ctl;
		try{
			System.out.println("************ getAllCarbonTransaction fot Community : " + communityId);
			ctl =  carbonTransactionDao.findAllCarbonTransaction(communityName, communityId);
		}catch (Exception e) {
			return new ArrayList<CarbonTransaction>();
		}
		
		return ctl;

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<CarbonTransaction> getCarbonTransaction(String email, String communityName) {

		Long communityId = null;
		try {
			communityId = domainDao.getCommunitId(communityName);
		}catch (NoResultException ne){
			System.out.println("************ Community no found.");
		}catch (Exception e) {
			System.out.println("************ Get error in UtilityService for Community. Error: " + e);
		}
		
		List<CarbonTransaction> ctl;
		try{
			System.out.println("************ getCarbonTransaction fot Community : " + communityId + " ; Email: " + email);
			ctl =  carbonTransactionDao.findCarbonTransaction(email, communityName, communityId);
		}catch (Exception e) {
			return new ArrayList<CarbonTransaction>();
		}
		
		return ctl;

	}
	*/
}
