package net.zfp.notify;

import java.util.List;
import java.util.Locale;

import net.zfp.entity.User;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.group.Groups;
import net.zfp.view.CarbonTableView;

/**
 * 
 * @author Youngwook Yoo
 * @since 2014-10-01
 */
public interface NotificationService {
	
	public void emailNotification(
			String from, String to, 
			String firstName, String lastName, 
			String subject,
			String issuedDate, String carbon);

    public String sendCarbonPaymentConfirmEmail(
    		String from, String to, 
    		String name, String address, 
    		List<CarbonTableView> ctvs, 
    		String subTotal, String tax, String taxType,String carbon, String total, String locale);
    
    
    
    public void sendBasicEmail(
    		String toEmail, String fromEmail, String fromName,
    		String subject, String content, String domainName);
    
	public void notifySignup(User user, Locale locale, String domainName) ;
	
	public void notifyReferJoin(User invitee, User inviter, Long accountGroupId, String domainName);
	
	public void notifyGroupJoin(User invitee, User inviter, Long accountGroupId, Boolean isGuest, String domainName);
	
	/**
	 * Sends confirmation email to a user for its order detail
	 * 
	 * @param user
	 * @param productName
	 * @param orderNumer
	 * @return
	 */
	public void sendSalesOrderConfirmationEmail(User user, String productName, Long orderNumer);
	
	public void notifyCampaignJoin(User user, Campaign program, String domainName);
	
	public void notifyAutoCampaignJoin(User user, Campaign program, String domainName, Integer coins);
	
	public void notifyCarbonSignup(User user, String locale) ;
	
	public void notifyPasswordResetRequest(User user, String url, String portalUrl, String locale, String domainName) ;

	public void notifyPasswordResetSuccess(User user, String locale, String domainName) ;

	public void notifyShareLink(User inviter, User invitee, String link, String domainName);
	
	public String createCertificate(String accountName, String value, String date, String locale);
	
}
