package net.zfp.service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.FormParam;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.zfp.dao.EntityDao;
import net.zfp.entity.EmailTemplate;
import net.zfp.entity.Fundraise;
import net.zfp.entity.PaymentTransaction;
import net.zfp.entity.PointsAccount;
import net.zfp.entity.Source;
import net.zfp.entity.Status;
import net.zfp.entity.Translation;
import net.zfp.entity.User;
import net.zfp.entity.account.AccountGroupCommunicationStatus;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.carbon.CarbonTransactionDetail;
import net.zfp.entity.community.Community;
import net.zfp.entity.memberactivity.MemberActivity;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.product.Product;
import net.zfp.entity.salesorder.PointsTransaction;
import net.zfp.entity.salesorder.PointsTransactionType;
import net.zfp.entity.salesorder.SalesOrder;
import net.zfp.entity.salesorder.SalesOrderBilling;
import net.zfp.entity.salesorder.SalesOrderDetail;
import net.zfp.entity.salesorder.SalesOrderReferenceType;
import net.zfp.entity.salesorder.SalesOrderShipping;
import net.zfp.entity.salesorder.SalesOrderType;
import net.zfp.entity.wallet.PointsType;
import net.zfp.form.SalesOrderForm;
import net.zfp.notify.NotificationService;
import net.zfp.notify.impl.NotificationServiceImpl;
import net.zfp.service.SalesOrderService;
import net.zfp.util.AppConstants;
import net.zfp.util.DateParserUtil;
import net.zfp.util.DateUtil;
import net.zfp.util.ImageUtil;
import net.zfp.util.TextUtil;
import net.zfp.view.OrderHistoryViews;
import net.zfp.view.ProductView;
import net.zfp.view.ResultView;
import net.zfp.view.OrderHistoryView;
import net.zfp.view.SalesOrderView;

public class SalesOrderServiceImpl implements SalesOrderService {
	
	@Resource
	private EntityDao<Community> communityDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<Source> sourceDao;
	@Resource
	private EntityDao<SalesOrder> salesOrderDao;
	@Resource
	private EntityDao<SalesOrderBilling> salesOrderBillingDao;
	@Resource
	private EntityDao<SalesOrderShipping> salesOrderShippingDao;
	@Resource
	private EntityDao<SalesOrderDetail> salesOrderDetailDao;
	@Resource
	private EntityDao<Fundraise> fundraiseDao;
	@Resource
	private EntityDao<Product> productDao;
	@Resource
	private EntityDao<Status> statusDao;
	@Resource
	private EntityDao<AccountCampaign> accountCampaignDao;
	@Resource
	private EntityDao<PointsAccount> pointsAccountDao;
	@Resource
	private EntityDao<Translation> translationDao;
	@Resource
	private EntityDao<PointsTransaction> pointsTransactionDao;
	@Resource
	private EntityDao<MemberActivity> memberActivityDao;
	@Resource
	private EntityDao<Offer> offerDao;
	@Resource
	private EntityDao<AccountGroupCommunicationStatus> accountGroupCommunicationStatusDao;
	@Inject
    private NotificationService notificationService;
	@Resource
    private UtilityService utilityService;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView sendConfirmationEmail(Long orderNumber){
		ResultView rv = new ResultView();
		
		if (orderNumber == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		SalesOrder salesOrder = salesOrderDao.findTransactionByOrderNumber(orderNumber);
		if (salesOrder == null){
			rv.fill(AppConstants.FAILURE, "Invalid sales order number.");
			return rv;
		}
		
		/*
		 * In order to send confirmation email, the template needs member's first name, product names, order number
		 */
		List<SalesOrderDetail> salesOrderDetails = salesOrderDao.getTransactionDetails(orderNumber);
		
		String productNames = "";
		if (!salesOrderDetails.isEmpty()){
			for (int i = 0; i < salesOrderDetails.size(); i++){
				SalesOrderDetail salesOrderDetail = salesOrderDetails.get(i);
				if (salesOrderDetail.getUPC() != null){
					/* Get product name for each detail */
					Product product = productDao.getProductByUPC(salesOrderDetail.getUPC());
					productNames += product.getName();
					if (i < salesOrderDetails.size() -1){
						productNames += ", ";
					}
				}
				
			}
		}
		
		/*
		 * Call notification service to get template
		 */
		notificationService = new NotificationServiceImpl();
		notificationService.sendSalesOrderConfirmationEmail(salesOrder.getUser(), productNames, salesOrder.getOrderNumber());
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView addOrder(Long memberId, String firstName, String lastName,
			String address1, String address2, String city, String postalCode,
			String province, String country, Double subTotal,
			Double tax, String taxType, Double totalCost, Integer totalPoints, Integer quantity,
			String referenceType){
		
		ResultView rv = new ResultView();
		
		if (memberId == null || firstName == null || lastName == null || address1 == null || address2 == null || city == null || subTotal == null || tax == null 
				|| (totalCost == null && totalPoints == null) || quantity == null || referenceType == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
			
		}
		
		SalesOrder t = new SalesOrder();
		
		User user = userDao.findById(memberId);
		if (user == null){
			rv.fill(AppConstants.FAILURE, "Member does not exist");
			return rv;
		}
		
		if (totalCost == null) totalCost = 0.0;
		if (totalPoints == null) totalPoints = 0;
		t.setUser(user);
		t.setSubTotalCost(subTotal);
		t.setTax(tax);
		t.setTaxType(taxType);
		t.setTotalCost(totalCost);
		t.setTotalPoints(totalPoints);
		
		SalesOrderReferenceType trt = salesOrderDao.findTransactionReferenceType(referenceType);
		
		t.setQuantity((double)(int)quantity);
		t.setSalesOrderReferenceType(trt);
		
		t.setCostUnit("$");
		
		Community community = communityDao.findById(user.getDefaultCommunity());
		
		t.setCommunity(community);
		try{
			Long uid = System.currentTimeMillis();
			t.setOrderNumber(uid);
			t = salesOrderDao.save(t);
			
			t = salesOrderDao.findTransactionByOrderNumber(uid);
			
			//System.out.println("Sales Order ID " + t.getId());
			
			SalesOrderBilling sob = new SalesOrderBilling();
			sob.setSalesOrder(t);
			sob.setFirstName(firstName);
			sob.setLastName(lastName);
			sob.setCity(city);
			sob.setPostalCode(postalCode);
			sob.setProvince(province);
			sob.setCountry(country);
			sob.setEmail(user.getEmail());
			sob.setAddress1(address1);
			sob.setAddress2(address2);
			salesOrderBillingDao.save(sob);
			rv.setResultCode(AppConstants.SUCCESS);
			rv.setResultMessage(t.getOrderNumber() + "");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView addOrderShipping(Long salesOrderNumber, String firstName, String lastName, String city,
			String postalCode, String province, String country, String email, String address1, String address2, String instruction){
		ResultView rv = new ResultView();
		
		if (salesOrderNumber == null || firstName == null || lastName == null || city == null || postalCode == null || province == null || country == null || email == null || address1 == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		SalesOrder salesOrder = salesOrderDao.findTransactionByOrderNumber(salesOrderNumber);
		if (salesOrder == null){
			rv.fill(AppConstants.FAILURE, "Invalid sales order number.");
			return rv;
		}
		
		try{
			
			SalesOrderShipping sos = new SalesOrderShipping();
			sos.setSalesOrder(salesOrder);
			sos.setFirstName(firstName);
			sos.setLastName(lastName);
			sos.setCity(city);
			sos.setPostalCode(postalCode);
			sos.setProvince(province);
			sos.setCountry(country);
			sos.setEmail(email);
			sos.setAddress1(address1);
			sos.setAddress2(address2);
			sos.setInstruction(instruction);
			salesOrderShippingDao.save(sos);
			rv.setResultCode(AppConstants.SUCCESS);
			rv.setResultMessage(salesOrder.getOrderNumber() + "");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView addOrderBilling(Long salesOrderNumber, String firstName, String lastName, String city,
			String postalCode, String province, String country, String email, String address1, String address2){
		ResultView rv = new ResultView();
		
		if (salesOrderNumber == null || firstName == null || lastName == null || city == null || postalCode == null || province == null || country == null || email == null || address1 == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		SalesOrder salesOrder = salesOrderDao.findTransactionByOrderNumber(salesOrderNumber);
		if (salesOrder == null){
			rv.fill(AppConstants.FAILURE, "Invalid sales order number.");
			return rv;
		}
		
		try{
			
			SalesOrderBilling sob = new SalesOrderBilling();
			sob.setSalesOrder(salesOrder);
			sob.setFirstName(firstName);
			sob.setLastName(lastName);
			sob.setCity(city);
			sob.setPostalCode(postalCode);
			sob.setProvince(province);
			sob.setCountry(country);
			sob.setEmail(email);
			sob.setAddress1(address1);
			sob.setAddress2(address2);
			salesOrderBillingDao.save(sob);
			rv.setResultCode(AppConstants.SUCCESS);
			rv.setResultMessage(salesOrder.getOrderNumber() + "");
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView addOrderDetail(Long transactionId, String orderType,String UPC, Integer quantity, Double unitPrice, Integer unitPoints){
		ResultView rv = new ResultView();
		
		if (transactionId == null || UPC == null || quantity == null || (unitPrice == null && unitPoints == null)){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		SalesOrder t = salesOrderDao.findTransactionByOrderNumber(transactionId);
		
		if (t == null){
			rv.fill(AppConstants.FAILURE, "Invalid order ID");
			return rv;
		}
		
		SalesOrderDetail td = new SalesOrderDetail();
		SalesOrderType tt = salesOrderDao.findTransactionType(orderType);
		
		if (tt == null){
			rv.fill(AppConstants.FAILURE, "Invalid order type");
			return rv;
		}
		
		td.setSalesOrderType(tt);
		
		if (unitPrice == null) unitPrice = 0.0;
		if (unitPoints == null) unitPoints = 0;
		
		Product product = productDao.getProductByUPC(UPC);
		if (product == null){
			rv.fill(AppConstants.FAILURE, "Invalid UPC");
			return rv;
		}
		td.setUPC(product.getUPC());
		td.setQuantity((double)quantity);
		td.setUnitListPrice(unitPrice);
		td.setUnitSalePrice(unitPrice);
		td.setTotalSalePrice(quantity * unitPrice);
		td.setSalesOrder(t);
		td.setUnitListPoints(unitPoints);
		td.setUnitSalePoints(unitPoints);
		td.setTotalSalePoints(quantity * unitPoints);
				
				
		salesOrderDetailDao.save(td);
		
		rv.fill(AppConstants.SUCCESS, "Successfully added order detail");
		return rv;
	
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public OrderHistoryViews getTransactionsHistory(Long accountId, Integer requestedMonth, Integer requestedYear){
		
		OrderHistoryViews orderHistoryViews = new OrderHistoryViews();
		
		List<OrderHistoryView> thvs = new ArrayList<OrderHistoryView>();
		
		ResultView rv = new ResultView();
		
		if (accountId == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			orderHistoryViews.setResult(rv);
			return orderHistoryViews;
			
		}
		
		User user = userDao.findById(accountId);
		if (user == null){
			rv.fill(AppConstants.FAILURE, "Member does not exist");
			orderHistoryViews.setResult(rv);
			return orderHistoryViews;
		}
		//First fetch points redeemed;
		//type == REDEMPTION, purchase
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -365);
		Date d = c.getTime();
		
		List<SalesOrderDetail> tds = new ArrayList<SalesOrderDetail>();
		List<PointsTransaction> pts = new ArrayList<PointsTransaction>();
		
		if (requestedMonth == null && requestedYear == null){
			tds.addAll(salesOrderDetailDao.getTransactionDetails(accountId, 2, d));
			tds.addAll(salesOrderDetailDao.getTransactionDetails(accountId, 3, d));
			
			pts.addAll(salesOrderDetailDao.getPointsTransaction(accountId, 1, d));
		}else{
			tds.addAll(salesOrderDetailDao.getTransactionDetails(accountId, 2, requestedMonth+1, requestedYear));
			tds.addAll(salesOrderDetailDao.getTransactionDetails(accountId, 3, requestedMonth+1, requestedYear));
			
			pts.addAll(salesOrderDetailDao.getPointsTransaction(accountId, 1, requestedMonth+1, requestedYear));
		}
		
		DateFormat dt = new SimpleDateFormat("MM/dd/yy");
		if (tds != null && tds.size() > 0){
			for (SalesOrderDetail td : tds){
				OrderHistoryView thv = new OrderHistoryView();
				if (td.getSalesOrderType().getId() == 2){
					thv.setType(2);
					thv.setValue(td.getTotalSalePoints().toString());
				}
				else if (td.getSalesOrderType().getId() == 3){
					thv.setType(3);
					DecimalFormat format = new DecimalFormat("#.00");
					thv.setValue(format.format(td.getTotalSalePrice()));
				}
				
				Product pr = productDao.getProductByUPC(td.getUPC());
				if (pr != null){
					thv.setDescription(TextUtil.parseString(pr.getShortDescription()));
				}
				Date transactionDate = td.getCreated();
				Calendar cal = Calendar.getInstance();
				cal.setTime(transactionDate);
				int month = cal.get(Calendar.MONTH);
				int year = cal.get(Calendar.YEAR);
				
				thv.setMonth(month);
				thv.setYear(year);
				thv.setOrderNumber(td.getSalesOrder().getOrderNumber());
				
				//Find the product using sku
				Product product = productDao.getProductBySKU(td.getSKU());
				if ( product == null) product = productDao.getProductByUPC(td.getUPC());
				
				if (product != null){
					thv.setId(product.getId());
					thv.setImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(product.getSmallImageUrl()));
					thv.setItem(product.getName());
					thv.setDate(dt.format(transactionDate));
				}
				
				thvs.add(thv);
			}
		}
		if (pts != null && pts.size() > 0){
			for(PointsTransaction pt : pts){
				OrderHistoryView thv = new OrderHistoryView();
				thv.setType(1);
				thv.setValue(pt.getAmount().toString());
				Date transactionDate = pt.getDate();
				Calendar cal = Calendar.getInstance();
				cal.setTime(transactionDate);
				int month = cal.get(Calendar.MONTH);
				int year = cal.get(Calendar.YEAR);
				
				thv.setMonth(month);
				thv.setYear(year);
				if (pt.getReference() == null) thv.setOrderNumber(null);
				else thv.setOrderNumber(Long.parseLong(pt.getReference()));
				
				thv.setWalletType(pt.getPointsAccount().getPointsType().getType());
				
				Offer offer = pt.getOffer();
				if (offer != null){
					thv.setId(offer.getId());
					thv.setImageUrl(AppConstants.APACHE_IMAGE_LINK + ImageUtil.parseApcheImageUrl(offer.getSmallImageUrl()));
					thv.setItem(offer.getName());
					thv.setDescription(TextUtil.parseString(offer.getDescription()));
					thv.setDate(dt.format(transactionDate));
					thvs.add(thv);
				}
				
			}
		}
		
		if (thvs.isEmpty()){
			rv.fill(AppConstants.FAILURE, "Currently there are not history");
			orderHistoryViews.setResult(rv);
			return orderHistoryViews;
		}else{
			orderHistoryViews.setOrderHistoryView(thvs);
		}
		
		return orderHistoryViews;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView redeemPoints(Long accountId, Integer totalDefaultRedeem, Integer totalCharityRedeem, Long memberActivityId, String reference, Long offerCode){
		ResultView rv = new ResultView();
		
		if (accountId == null && (totalDefaultRedeem == null && totalCharityRedeem == null) && memberActivityId == null && reference == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			return rv;
		}
		
		PointsTransactionType ptt = pointsTransactionDao.getPointsTransactionType(AppConstants.POINTS_TRANSACTION_TYPE_REDEMPTION);
		MemberActivity ma = memberActivityDao.findById(memberActivityId);
		Offer offer = offerDao.getOfferByCode(offerCode);
		
		//Case where points are too much compared to what a user has
		boolean isDefaultSuccess = true;
		
		PointsType defaultPT = pointsAccountDao.getPointsType(AppConstants.POINTS_TYPE_DEFAULT);
		PointsType charityPT = pointsAccountDao.getPointsType(AppConstants.POINTS_TYPE_CHARITY);
		
		PointsAccount charityPA = pointsAccountDao.getPointsAccount(accountId, charityPT.getId());
		PointsAccount defaultPA = pointsAccountDao.getPointsAccount(accountId, defaultPT.getId());
		
		Integer balance = 0;
		
		if (totalDefaultRedeem > 0){
			
			//Get user's points
			if (defaultPA != null){
				balance = defaultPA.getBalance();
				
				if (defaultPA.getBalance() - totalDefaultRedeem >= 0){
					defaultPA.setBalance(balance-totalDefaultRedeem);
					pointsAccountDao.save(defaultPA, true);
					//Add points transactions accordingly
					setPointsTransaction(ptt, defaultPA, ma, totalDefaultRedeem, reference, offer);
					
					rv.setResultCode(AppConstants.SUCCESS);
					rv.setResultMessage((balance-totalDefaultRedeem) + "");
				}else{
					rv.setResultCode(AppConstants.FAILURE);
					rv.setResultMessage("Balance is empty");
					isDefaultSuccess = false;
				}
			}
				
		}else{
			rv.setResultCode(AppConstants.FAILURE);
			rv.setResultMessage("Balance is empty");
			
		}
		
		if (isDefaultSuccess && totalCharityRedeem > 0){
			
			//Get user's points
			if (charityPA != null){
				if (charityPA.getBalance() - totalCharityRedeem >= 0){
					charityPA.setBalance(charityPA.getBalance()-totalCharityRedeem);
					pointsAccountDao.save(charityPA, true);
					//Add points transactions accordingly
					setPointsTransaction(ptt, charityPA, ma, totalCharityRedeem, reference, offer);
					
					rv.setResultCode(AppConstants.SUCCESS);
					rv.setResultMessage((balance-totalDefaultRedeem) + "");
				}else{
					if (defaultPA != null){
						
						if (defaultPA.getBalance() + charityPA.getBalance() - totalCharityRedeem >=0){
							balance = totalCharityRedeem - charityPA.getBalance();
							
							charityPA.setBalance(0);
							pointsAccountDao.save(charityPA, true);
							//Add points transactions accordingly
							setPointsTransaction(ptt, charityPA, ma, balance, reference, offer);
							
							defaultPA.setBalance(defaultPA.getBalance() - balance);
							pointsAccountDao.save(defaultPA, true);
							//Add points transactions accordingly
							setPointsTransaction(ptt, defaultPA, ma, totalCharityRedeem, reference, offer);
							
							rv.setResultCode(AppConstants.SUCCESS);
							rv.setResultMessage(defaultPA.getBalance() + "");
							
						}else{
							rv.setResultCode(AppConstants.FAILURE);
							rv.setResultMessage("Balance is empty");
							isDefaultSuccess = false;
						}
					}else{
						rv.setResultCode(AppConstants.FAILURE);
						rv.setResultMessage("Balance is empty");
						isDefaultSuccess = false;
					}
				}
			}else{
				
				balance = defaultPA.getBalance();
				
				if (defaultPA.getBalance() - totalCharityRedeem >= 0){
					defaultPA.setBalance(balance-totalCharityRedeem);
					pointsAccountDao.save(defaultPA, true);
					//Add points transactions accordinly
					setPointsTransaction(ptt, defaultPA, ma, totalCharityRedeem, reference, offer);
					
					rv.setResultCode(AppConstants.SUCCESS);
					rv.setResultMessage((balance-totalCharityRedeem) + "");
				}else{
					rv.setResultCode(AppConstants.FAILURE);
					rv.setResultMessage("Balance is empty");
					isDefaultSuccess = false;
				}
			}
		}
		
		return rv;
	}
	
	private void setPointsTransaction(PointsTransactionType ptt, PointsAccount pa, MemberActivity ma, Integer amount, String reference, Offer offer){
		PointsTransaction pt = new PointsTransaction();
		pt.setPointsTransactionType(ptt);
		pt.setPointsAccount(pa);
		pt.setMemberActivity(ma);
		pt.setOffer(offer);
		pt.setAmount(amount);
		pt.setDate(new Date());
		if (reference == null) pt.setReference(System.currentTimeMillis() + "");
		else pt.setReference(reference);
		
		pointsTransactionDao.save(pt);
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView setSalesOrderStatus(Long orderId, String orderStatus, String orderPaymentStatus){
		
		ResultView rv = new ResultView();
		
		System.out.println("ORDER STATUS " + orderStatus);
		System.out.println("ORDER ID :: " + orderId);
		//Fetch Sales Order Id
		SalesOrder sales = salesOrderDao.findTransactionByOrderNumber(orderId);
		
		if (sales != null){
			//Status code
			Status oStatus = statusDao.getStatusByCode(orderStatus);
			Status oPaymentStatus = statusDao.getStatusByCode(orderPaymentStatus);
			
			sales.setOrderStatus(oStatus);
			sales.setPaymentStatus(oPaymentStatus);
			
			salesOrderDao.save(sales, true);
			
			rv.fill(AppConstants.SUCCESS, "Updated");
			
		}else{
			rv.fill(AppConstants.FAILURE, "Can't find sales order");
		}
		
		return rv;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView storePayment(SalesOrderForm transactionForm){
		ResultView rv = new ResultView();
		
		PaymentTransaction pt = new PaymentTransaction();
		
		pt.setPaymentTimestamp(transactionForm.getPaymentTimeestamp());
		pt.setCardAuthNo(transactionForm.getCardAuthNo());
		pt.setCardReferenceId(transactionForm.getCardReferenceId());
		pt.setPaymentAmount(transactionForm.getPaymentAmount());
		pt.setPaymentReturnCode(transactionForm.getPaymentReturnCode());
		pt.setPaymentStatus(transactionForm.getPaymentStatus());
		pt.setPaymentOrderNumber(transactionForm.getPaymentOrderNumber());
		pt.setTransactionReferenceId(transactionForm.getTransactionReferenceId());
		pt.setCardType(transactionForm.getCardType());
		pt.setCardNumber(transactionForm.getCardNumber());
		
		SalesOrder sales = salesOrderDao.findTransactionByOrderNumber(transactionForm.getOrderNumber());
		if (sales != null){
			sales.setPayment(pt);
		}
		try{
			salesOrderDao.save(sales, true);
			rv.fill(AppConstants.SUCCESS, "success");
		}catch(Exception e){
			rv.fill(AppConstants.FAILURE, "Failed");
		}
		
		return rv;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView storeTransaction(SalesOrderForm transactionForm){
		ResultView rv = new ResultView();
		
		SalesOrder t = new SalesOrder();
		
		if (transactionForm.getPaymentTimeestamp() != null){
			PaymentTransaction pt = new PaymentTransaction();
			
			pt.setPaymentTimestamp(transactionForm.getPaymentTimeestamp());
			pt.setCardAuthNo(transactionForm.getCardAuthNo());
			pt.setCardReferenceId(transactionForm.getCardReferenceId());
			pt.setPaymentAmount(transactionForm.getPaymentAmount());
			pt.setPaymentReturnCode(transactionForm.getPaymentReturnCode());
			pt.setPaymentStatus(transactionForm.getPaymentStatus());
			pt.setPaymentOrderNumber(transactionForm.getPaymentOrderNumber());
			pt.setTransactionReferenceId(transactionForm.getTransactionReferenceId());
			pt.setCardType(transactionForm.getCardType());
			pt.setCardNumber(transactionForm.getCardNumber());
			
			t.setPayment(pt);
		}else{
			
			t.setPayment(null);
		}
		User user = null;
		Community community = communityDao.getCommunity(transactionForm.getDomainName());
		if (transactionForm.getAccountId() != null) user = userDao.findById(transactionForm.getAccountId());
		else{
			user = userDao.findByName(transactionForm.getEmail(), community.getId(), 1);
		}
		if (user != null) t.setUser(user);
		t.setSubTotalCost(transactionForm.getSubTotalCost());
		t.setTax(transactionForm.getTax());
		t.setTaxType(transactionForm.getTaxType());
		t.setTotalCost(transactionForm.getTotalCost());
		t.setTotalPoints(transactionForm.getTotalPoints());
		
		SalesOrderReferenceType trt = salesOrderDao.findTransactionReferenceType(transactionForm.getReferenceType());
		
		t.setQuantity(transactionForm.getQuantity());
		t.setSalesOrderReferenceType(trt);
		
		System.out.println("COST UNIT :: " + transactionForm.getCostUnit());
		t.setCostUnit(transactionForm.getCostUnit());
		
		t.setCommunity(community);
		try{
			Long uid = System.currentTimeMillis();
			t.setOrderNumber(uid);
			t = salesOrderDao.save(t);
			
			t = salesOrderDao.findTransactionByOrderNumber(uid);
			
			System.out.println("Sales Order ID " + t.getId());
			
			SalesOrderBilling sob = new SalesOrderBilling();
			sob.setSalesOrder(t);
			sob.setFirstName(transactionForm.getFirstName());
			sob.setLastName(transactionForm.getLastName());
			sob.setCity(transactionForm.getCity());
			sob.setPostalCode(transactionForm.getPostalCode());
			sob.setProvince(transactionForm.getProvince());
			sob.setCountry(transactionForm.getCountry());
			sob.setEmail(transactionForm.getEmail());
			sob.setAddress1(transactionForm.getAddress1());
			sob.setAddress2(transactionForm.getAddress2());
			salesOrderBillingDao.save(sob);

			rv.setResultCode(AppConstants.SUCCESS);
			rv.setResultMessage(t.getOrderNumber() + "");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView storeTransactionDetail(Long transactionId, ProductView pv){
		ResultView rv = new ResultView();
		
		SalesOrder t = salesOrderDao.findTransactionByOrderNumber(transactionId);
		SalesOrderDetail td = new SalesOrderDetail();
		SalesOrderType tt = salesOrderDao.findTransactionType(pv.getType());
		td.setSalesOrderType(tt);
		Product product = productDao.findById(pv.getId());
		td.setUPC(product.getUPC());
		td.setQuantity(pv.getQuantity());
		td.setUnitListPrice(pv.getUnitPrice());
		td.setUnitSalePrice(pv.getUnitPrice());
		td.setTotalSalePrice(pv.getPriceValue());
		td.setSalesOrder(t);
		td.setUnitListPoints(pv.getUnitPoints());
		td.setUnitSalePoints(pv.getUnitPoints());
		td.setTotalSalePoints(pv.getTotalPoints());
				
				
		salesOrderDetailDao.save(td);
		
		return rv;
	
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<SalesOrderView> getTransactionsByType(Long accountId, String type){
		List<SalesOrderView> tvs = new ArrayList<SalesOrderView>();
		
		//get accountId
		User user = userDao.findById(accountId);
		//get Transaction Type Id
		SalesOrderType transactionType = salesOrderDao.findTransactionType(type);
		
		List<SalesOrder> transactions = salesOrderDao.findTransactions(user.getId(), transactionType.getId());
		
		if (transactions != null && transactions.size() >0){
			for (SalesOrder t : transactions){
				SalesOrderView tv = new SalesOrderView(t);
				tv.setOrderNumber(t.getOrderNumber());
				tv.setTimeStamp(t.getCreated());
				tv.setQuantity(t.getQuantity());
				tv.setCardNumber(t.getPayment().getCardNumber());
				tv.setCardType(t.getPayment().getCardType());
				tv.setPaymentReferenceId(t.getPayment().getTransactionReferenceId());
				tvs.add(tv);
			}
		}
		
		return tvs;
	}
	
	private String checkNull(String words){
		if (words == null || words.equals("null")){
			return "";
		}
		return words;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public SalesOrderView getTransactionDetails( Long transactionId, String locale){
		SalesOrderView tv = new SalesOrderView();
		ResultView rv = new ResultView();
		if (transactionId == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			tv.setResult(rv);
			return tv;
		}
		
		if (locale == null) locale = "en_CA";
		
		List<ProductView> pvs = new ArrayList<ProductView>();
		//Fetch SalesOrder
		//Fetch SalesType
		SalesOrder ct = salesOrderDao.findTransactionByOrderNumber(transactionId);
		
		if (ct == null){
			rv.fill(AppConstants.FAILURE, "Invalid order ID");
			tv.setResult(rv);
			return tv;
		}
		
		tv.setName(ct.getUser().getFirstName() + " " + ct.getUser().getLastName());
		//Get Sales order billing
		SalesOrderBilling sob = salesOrderDao.findBillingByOrderNumber(ct.getOrderNumber());
		tv.setAddress(checkNull(sob.getCity())+ ", " + checkNull(sob.getProvince()) + " " + checkNull(sob.getPostalCode()) + " " + checkNull(sob.getCountry()));
		/*
		if (ct.getUser().getCity() == null || ct.getUser().getCity().equals("null") || ct.getUser().getCity().equals("")){
			tv.setAddress(checkNull(ct.getUser().getProvince()) + " " + checkNull(ct.getUser().getPostalCode()) + " " + checkNull(ct.getUser().getCountry()));
		}else{
			tv.setAddress(ct.getUser().getCity() + ", " + checkNull(ct.getUser().getProvince()) + " " + checkNull(ct.getUser().getPostalCode()) + " " + checkNull(ct.getUser().getCountry()));
		}
		*/
		System.out.println(ct.getUser().getFirstName() + " " + ct.getUser().getLastName() + " :: " + ct.getQuantity().toString() + " :: " + DateUtil.printCalendar2(ct.getCreated()));
		if (ct.getReceiptUrl() != null) tv.setPdfURL(ct.getReceiptUrl());
		else{
			tv.setPdfURL(utilityService.generateCarbonCertificate(ct.getId(), ct.getUser().getFirstName() + " " + ct.getUser().getLastName(), ct.getQuantity().toString(), DateUtil.printCalendar2(ct.getCreated())));
		}
		
		tv.setTax(ct.getTax());
		tv.setTaxType(ct.getTaxType());
		
		System.out.println(tv.getPdfURL());
		List<SalesOrderDetail> ctds = salesOrderDetailDao.getTransactionDetails(transactionId);
		System.out.println("CarbonTransacitonDetail :: " + ctds.size());
		if (ctds != null && ctds.size() > 0){
			for (SalesOrderDetail ctd : ctds){
				ProductView pv = new ProductView();
				Product product = productDao.getProductByUPC(ctd.getUPC());
				if (product != null){
					if (product.getTranslationKey() != null) pv.setName(translationDao.findTranslation(product.getTranslationKey(), ct.getCommunity().getId(), 2l, locale, 3l).getTranslation());
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
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ResultView fundraiseTransaction(Long accountId, Long accountProgramId, Integer amount ){
		ResultView rv = new ResultView();
		//Add it to fundraising table
		Fundraise fundraise = new Fundraise();
		AccountCampaign ap = accountCampaignDao.getAccountCampaign(accountProgramId);
		User sponsor = userDao.findById(accountId);
		fundraise.setAccountCampaign(ap);
		fundraise.setUser(sponsor);
		fundraise.setAmount((double)amount);
		fundraise.setTransactionDate(new Date());
		
		fundraiseDao.save(fundraise);
		
		//Change status
		AccountGroupCommunicationStatus ag = null;
		ag = accountGroupCommunicationStatusDao.getAccountGroupCommunicationStatus(sponsor.getId(), ap.getGroups().getId(), ap.getCampaign().getId());
		Status status = accountGroupCommunicationStatusDao.getStatusByCode(AppConstants.STATUS_CODE_SPONSORED);
		if (ag != null){
			ag.setStatus(status);
			accountGroupCommunicationStatusDao.save(ag, true);
		}else{
			ag = new AccountGroupCommunicationStatus();
			ag.setGroups(ap.getGroups());
			ag.setUser(sponsor);
			ag.setCampaign(ap.getCampaign());
			ag.setStatus(status);
			accountGroupCommunicationStatusDao.save(ag);
		}
		
		//Put it into transaction table/
		//XXX: to do
		//Send email to a user
		String sponsorName ="";
		if (sponsor.getFirstName() != null && sponsor.getLastName() != null){
			sponsorName = sponsor.getFirstName() + " " + sponsor.getLastName();
		}else{
			sponsorName = "membership";
			
		}
		
		String userName ="";
		User user = ap.getUser();
		if (user.getFirstName() != null && user.getLastName() != null){
			userName = user.getFirstName() + " " + user.getLastName();
		}else{
			userName = "membership";
			
		}
			EmailTemplate et = fundraiseDao.getEmailTemplateByType(4l, "THANKS_FOR_YOUR_SUPPORT");
			String content = et.getContent();
			String subject = et.getSubject();
			
			subject = subject.replaceAll("<campaign name>", ap.getCampaign().getName());
			content = content.replaceAll("<campaign name>", ap.getCampaign().getName());
			content = content.replaceAll("<sponsor's first name>", sponsorName);
			content = content.replaceAll("<user name>", userName);
			
			content = TextUtil.parseString(content);
			
			try{
				
				notificationService = new NotificationServiceImpl();
				//notificationService.sendBasicEmail(sponsor.getEmail(), "membership@zerofootprint.net", userName, subject, content);
				rv.setResultCode(AppConstants.SUCCESS);
			}catch(Exception e){
				rv.setResultCode(AppConstants.FAILURE);
			}
		
		return rv;
	}
}
