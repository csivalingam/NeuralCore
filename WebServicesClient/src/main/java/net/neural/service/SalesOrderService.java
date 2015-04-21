package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.form.SalesOrderForm;
import net.zfp.view.OrderHistoryViews;
import net.zfp.view.ProductView;
import net.zfp.view.ResultView;
import net.zfp.view.OrderHistoryView;
import net.zfp.view.SalesOrderView;

/**
 * Provide any information about sales order.
 * 
 * @author Youngwook Yoo
 * @since 2014-09-06
 *
 */
@Path("/order")
public interface SalesOrderService {
	
	/**
	 * Sends confirmation email about its detail information.
	 * 
	 * @param orderNumber
	 * @return
	 */
	@POST
	@Path("confirmationemail")
	@Produces("application/json")
	ResultView sendConfirmationEmail(@FormParam("ordernumber") Long orderNumber);
	
	/**
	 * Add given detailed sales order
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param firstName
	 * @param lastName
	 * @param address1
	 * @param address2
	 * @param city
	 * @param postalcode
	 * @param province
	 * @param country
	 * @param subTotal
	 * @param tax
	 * @param taxType
	 * @param totalcost
	 * @param totalPoints
	 * @param quantity
	 * @param referenceType
	 * 
	 * @return true if sales order is added, false otherwise
	 */
	@POST
	@Path("addorder")
	@Produces("application/json")
	ResultView addOrder(@FormParam("memberid") Long memberId, @FormParam("firstname") String firstName, @FormParam("lastname") String lastName,
			@FormParam("address1") String address1, @FormParam("address2") String address2, @FormParam("city") String city, @FormParam("postalcode") String postalcode,
			@FormParam("province") String province, @FormParam("country") String country, @FormParam("subtotal") Double subTotal,
			@FormParam("tax") Double tax, @FormParam("taxtype") String taxType, @FormParam("totalcost") Double totalcost, @FormParam("totalpoints") Integer totalPoints, @FormParam("quantity") Integer quantity,
			@FormParam("referencetype") String referenceType);
	
	
	/**
	 * Set sales order detail products
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param transactionId
	 * @param orderType
	 * @param UPC
	 * @param quantity
	 * @param unitPrice
	 * @param unitPoints
	 * 
	 * @return true if sales order detail is saved, false otherwise
	 */
	@POST
	@Path("addorderdetail")
	@Produces("application/json")
	ResultView addOrderDetail(@FormParam("orderid") Long transactionId, @FormParam("ordertype") String orderType, @FormParam("UPC") String UPC, @FormParam("quantity") Integer quantity, 
			@FormParam("unitprice") Double unitPrice, @FormParam("unitpoints") Integer unitPoints);
	
	/**
	 * Set sales order billing for the order
	 * 
	 * @param salesOrderNumber
	 * @param firstName
	 * @param lastName
	 * @param city
	 * @param postalCode
	 * @param province
	 * @param country
	 * @param email
	 * @param address1
	 * @param address2
	 * @return true if sales order billing is saved, false otherwise
	 */
	@POST
	@Path("addorderbilling")
	@Produces("application/json")
	ResultView addOrderBilling(@FormParam("ordernumber") Long salesOrderNumber, @FormParam("firstname") String firstName, @FormParam("lastname") String lastName, @FormParam("city") String city,
			@FormParam("postalcode") String postalCode,@FormParam("province") String province, @FormParam("country") String country, @FormParam("email")String email, @FormParam("address1")String address1, @FormParam("address2")String address2);
	
	/**
	 * Set sales order shipping for the order
	 * 
	 * @param salesOrderNumber
	 * @param firstName
	 * @param lastName
	 * @param city
	 * @param postalCode
	 * @param province
	 * @param country
	 * @param email
	 * @param address1
	 * @param address2
	 * @return true if sales order shipping is saved, false otherwise
	 */
	@POST
	@Path("addordershipping")
	@Produces("application/json")
	ResultView addOrderShipping(@FormParam("ordernumber") Long salesOrderNumber, @FormParam("firstname") String firstName, @FormParam("lastname") String lastName, @FormParam("city") String city,
			@FormParam("postalcode") String postalCode,@FormParam("province") String province, @FormParam("country") String country, @FormParam("email")String email, @FormParam("address1")String address1, @FormParam("address2")String address2, @FormParam("instruction")String instruction);
	
	/**
	 * Get member's order histories based on period
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param month
	 * @param year
	 * 
	 * @return order histories
	 */
	@GET
	@Path("getorderhistory")
	@Produces("application/json")
	OrderHistoryViews getTransactionsHistory(@QueryParam("memberid") Long memberId, @QueryParam("month") Integer month, @QueryParam("year") Integer year);
	
	/**
	 * Get detail of given sales order
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param transactionId
	 * @param locale
	 * 
	 * @return given sales order detail
	 */
	@GET
	@Path("getorderdetail")
	@Produces("application/json")
	SalesOrderView getTransactionDetails(@QueryParam("orderid") Long transactionId, @QueryParam("locale") String locale);
	
	/**
	 * Redeem points and store points transactions
	 * 
	 * @param memberid
	 * @param totaldefaultredeem
	 * @param totalcharityredeem
	 * @param memberactivityid
	 * @param reference
	 * @param offercode
	 * 
	 * @return true if points are redeemed, false otherwise
	 */
	@POST
	@Path("redeempoints")
	@Produces("application/json")
	ResultView redeemPoints(@FormParam("memberid") Long accountId, @FormParam("totaldefaultredeem") Integer totalDefaultRedeem, @FormParam("totalcharityredeem") Integer totalCharityRedeem, @FormParam("memberactivityid") Long memberActivityId, @FormParam("reference") String reference, @FormParam("offercode") Long offerCode);
	
	//------
	
	//Carbon
	@POST
	@Path("transactiontype")
	@Produces("text/xml")
	List<SalesOrderView> getTransactionsByType(@FormParam("accountId") Long accountId, @FormParam("type") String type );
	
	
	//Deprecated...
	@POST
	@Path("fundraiseTransaction")
	@Produces("text/xml")
	ResultView fundraiseTransaction(@FormParam("accountId") Long accountId, @FormParam("accountProgramId") Long accountProgramId, @FormParam("amount") Integer amount );
	
	

	@POST
	@Path("setSalesOrderStatus")
	@Produces("text/xml")
	ResultView setSalesOrderStatus(@FormParam("orderId") Long orderId, @FormParam("orderStatus") String orderStatus, @FormParam("orderPaymentStatus") String orderPaymentStatus);
	
	@POST
	@Path("storePayment")
	@Produces("text/xml")
	ResultView storePayment(@FormParam("") SalesOrderForm transactionForm);
	
	
	@POST
	@Path("storeTransactionDetail")
	@Produces("text/xml")
	ResultView storeTransactionDetail(@FormParam("transactionId") Long transactionId, @FormParam("") ProductView productView);
	
	@POST
	@Path("storeTransaction")
	@Produces("text/xml")
	ResultView storeTransaction(@FormParam("") SalesOrderForm transactionForm);
	
}
