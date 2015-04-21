package net.zfp.notify.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.ws.rs.WebApplicationException;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.PdfWriter;

import net.zfp.entity.User;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.group.Groups;
import net.zfp.notify.NotificationService;
import net.zfp.util.DateParserUtil;
import net.zfp.util.DateUtil;
import net.zfp.util.LocaleUtil;
import net.zfp.util.SystemProperties;

import java.util.List;

import net.zfp.view.CarbonTableView;
@Named
@net.bull.javamelody.MonitoredWithSpring
public class NotificationServiceImpl implements NotificationService {

	private transient static final Logger LOG = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	@Resource
//	@Inject
	private JavaMailSender mailSender;

	@Inject
	private VelocityEngine velocityEngine;

//	@PostConstruct
//	public void init() throws Exception {
//		try {
//			System.out.println("Parsing the mail notification events configuration file - ");
//		} catch (Exception e) {
//			throw new Exception(e.getMessage(), e);
//		}
//	}
	
	private String sendFromZerofootprint = "The GOODcoins Team <it_support@goodcoins.ca>";
	private String sendZerofootprint = "it_support@goodcoins.ca";
	
	@Override
	public void sendBasicEmail(final String toEmail, final String fromEmail, final String fromName, final String subject, final String content, final String domainName){
		System.out.println("Sending emails");
		ResourceBundle rb =  ResourceBundle.getBundle("net/zfp/velo/velo-application");
		String http = rb.getString("rewards.redirect.http");
		String server = rb.getString("rewards.redirect.server");
		
		final String GOODcoinsLink = http + domainName + server;
		
				this.getSpringMailSender().send(new MimeMessagePreparator() {
					public void prepare(MimeMessage mimeMessage) throws Exception {
						//MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
						MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
						
						message.setTo(toEmail);
						message.setFrom(fromName +" via Zerofootprint <"+sendZerofootprint+">");
						
						message.setSubject(MimeUtility.encodeText(subject, "utf-8", "B"));
						
						Map model = new HashMap();
						
						model.put("GOODcoinsLink", GOODcoinsLink);
						model.put("context", content);
						
						initVelocityEngine();
						String templateLocation = "net/zfp/velo/templates/basic-GOODcoins-en.vm";
						
						String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", model);
						message.setText(text, true);
						
						
					}
				});
				
	}
	
	@Override
	public void notifyCarbonSignup(final User user, final String locale) {

		System.out.println("~~~~~~~~~~~~~~~~~~Sending notify email for signup.");
		
		this.getSpringMailSender().send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				//MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				
				System.out.println("--------- Carbon Payment Thanks ----------");
				message.setTo(user.getEmail());
				message.setFrom("Zerofootprint Carbon <carbon@zerofootprint.net>");
				message.setSubject("Zerofootprint Carbon Offsets Account Registration Confirmation");
				if(locale != null && locale.equals("fr_CA")){
					message.setSubject("Confirmation de création d’un compte de crédits compensatoires Zerofootprint");
				}
				Map model = new HashMap();
				
				model.put("name", user.getEmail());
				model.put("url", "http://www.zerofootprintcarbon.com");
				model.put("companyName", "Zero Footprint");

				initVelocityEngine();
				String templateLocation = "net/zfp/velo/templates/carbon-registration-confirm-email_en.vm";
				if(locale != null && locale.equals("fr_CA")){
					templateLocation = "net/zfp/velo/templates/carbon-registration-confirm-email_fr.vm";
				}
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", model);
				message.setText(text, true);
				
			}
		});
		
	}
	
	@Override
	public void notifySignup(final User user, final Locale locale, final String domainName) {
		
		ResourceBundle rb =  ResourceBundle.getBundle("net/zfp/velo/velo-application");
		String http = rb.getString("rewards.redirect.http");
		String server = rb.getString("rewards.redirect.server");
		
		final String GOODcoinsLink = http + domainName + server;
		
		this.getSpringMailSender().send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				//MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				
				message.setTo(user.getEmail());
				message.setFrom(sendFromZerofootprint);
				message.setSubject("Welcome " + user.getFirstName() + " to GOODcoins!");
				Map model = new HashMap();
				
				if (user.getFirstName()==null | user.getFirstName().trim().equals("")){
					model.put("name", user.getEmail());
				}else{
					model.put("name", user.getFirstName());
				}
				
				model.put("GOODcoinsLink", GOODcoinsLink);
				
				model.put("companyName", "Zerofootprint");

				initVelocityEngine();
				String templateLocation = "net/zfp/velo/templates/registration-confirm-email_en.vm";
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
				message.setText(text, true);
				
			}
		});
		
	}
	
	@Override
	public void emailNotification(final String from, final String to, final String firstName, final String lastName, 
			final String subject, final String issuedDate, final String carbon) {
		
		System.out.println("~~~~~~~~~~~~~~~~~~Here, I am sending email.");

//		Map model = new HashMap();
//		model.put("firstName", firstName);
//		model.put("lastName", lastName);
//		String templateLocation = "net/zfp/velo/templates/carbon-payment-confirm-email_en.vm";
//		String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
//		System.out.println("~~~~~~~~~~~~~~~~~~Here, Body: " + text);
		
		//JavaMailSender  mailSender = new JavaMailSender();
		
		mailSender.send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage){
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				try {
					//message.setTo("henry.hou@zerofootprint.net");
					message.setFrom(from);
					message.setSubject(subject);
					
					Map model = new HashMap();
					model.put("firstName", firstName);
					model.put("lastName", lastName);
					String templateLocation = "net/zfp/velo/templates/carbon-payment-confirm-email_en.vm";
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
					
					
					FileSystemResource file = new FileSystemResource("/Users/oliver/Downloads/SOP_Certificate.pdf");
					message.addAttachment(file.getFilename(), file);
					
					message.setText(text, true);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					
				}
				
			}
		});
		
	}

	@Override
	public void sendSalesOrderConfirmationEmail(final User user, final String productNames, final Long orderNumer){
		this.getSpringMailSender().send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				message.setTo(user.getEmail());
				message.setFrom(sendFromZerofootprint);
				message.setSubject("Thank you for your recent purchase at the GOODshop");
				 
				Map model = new HashMap();
				model.put("name", user.getFirstName());
				model.put("productNames", productNames);
				model.put("orderNumber", orderNumer);
				
				initVelocityEngine();
				String templateLocation = "net/zfp/velo/templates/sales-order-confirm-email.vm";
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
				message.setText(text, true);
			}
		});
	}
	
	@Override
	public void notifyPasswordResetRequest(final User user, final String url, final String portalUrl, final String locale, final String domainName) {
		final String email = user.getEmail();
		
		ResourceBundle rb =  ResourceBundle.getBundle("net/zfp/velo/velo-application");
		String http = rb.getString("rewards.redirect.http");
		String server = rb.getString("rewards.redirect.server");
		
		final String GOODcoinsLink = http + domainName + server;
		
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
		
		final String expiryDate = format.format(user.getPasswordResetCodeExpiresOn());
		
		this.getSpringMailSender().send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				//MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				
				System.out.println("--------- Carbon Payment Thanks ----------");
				message.setTo(email);
				
				String french = "";
				
				/*
				message.setFrom("Support Zero <support@zerofootprint.net>");
				message.setSubject("Password Reset Request Confirmation");
				
				
				if (locale != null && locale.equals("fr_CA")){
					message.setSubject("Confirmation de demande de modification de mot de passe");
					french += "&language=fr";
				}
				*/
				message.setFrom(sendFromZerofootprint);
				message.setSubject("Password reset request");
				Map model = new HashMap();
				
				final String link = url + "?email=" + email + "&code=" + user.getPasswordResetCode()+"&link="+portalUrl + french;
				
				//user.getFirstName()==null ? model.put("name", user.getEmail()) : model.put("name", user.getEmail());
				if (user.getFirstName()==null | user.getFirstName().trim().equals("")){
					model.put("name", user.getEmail());
				}else{
					model.put("name", user.getFirstName());
				}
				model.put("url", link);
				//model.put("resetExpiryDate", LocaleUtil.getDateTimeFormat(DateFormat.LONG, DateFormat.LONG, locale, user.getPasswordResetCodeExpiresOn()));
				
				model.put("resetExpiryDate", expiryDate);
				model.put("GOODcoinsLink", GOODcoinsLink);

				System.out.println("---------Url: " + link);
				System.out.println("---------ExpiryDate: " + expiryDate);
				
				initVelocityEngine();
				String templateLocation = "net/zfp/velo/templates/password-reset-code-email_en.vm";
				
				/*
				String templateLocation = "net/zfp/velo/templates/carbon-password-reset-code-email_en.vm";
				if (locale != null && locale.equals("fr_CA")){
					templateLocation = "net/zfp/velo/templates/carbon-password-reset-code-email_fr.vm";
				}
				*/
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", model);

				message.setText(text, true);
				
			}
		});
		
	}

	@Override
	public void notifyShareLink(final User inviter, final User invitee, final String link, final String domainName){
		
		ResourceBundle rb =  ResourceBundle.getBundle("net/zfp/velo/velo-application");
		String http = rb.getString("rewards.redirect.http");
		String server = rb.getString("rewards.redirect.server");
		
		final String GOODcoinsLink = http + domainName + server;
		final String ShareLink = http + domainName + server + link;
		
		this.getSpringMailSender().send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				message.setTo(invitee.getEmail());
				message.setFrom(sendFromZerofootprint);
				message.setSubject("Check out the link " + inviter.getFirstName() + " " + inviter.getLastName() +  " has shared with you");
				 
				Map model = new HashMap();
				model.put("inviteeName", invitee.getFirstName());
				model.put("inviterName", inviter.getFirstName());
				model.put("GOODcoinsLink", GOODcoinsLink);
				model.put("url", ShareLink);
				
				initVelocityEngine();
				String templateLocation = "net/zfp/velo/templates/share-link-email_en.vm";
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
				message.setText(text, true);
			}
		});
	}
	
	@Override
	public void notifyPasswordResetSuccess(final User user, final String locale, final String domainName) {
		
		ResourceBundle rb =  ResourceBundle.getBundle("net/zfp/velo/velo-application");
		String http = rb.getString("rewards.redirect.http");
		String server = rb.getString("rewards.redirect.server");
		
		final String GOODcoinsLink = http + domainName + server;
		
		this.getSpringMailSender().send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				message.setTo(user.getEmail());
				
				/*
				message.setFrom("Support Zero <support@zerofootprint.net>");
				message.setSubject("Passsword Reset Success");
				if (locale != null && locale.equals("fr_CA")){
					message.setSubject("Modification de mot de passe réussi");
				}
				*/
				
				message.setFrom(sendFromZerofootprint);
				message.setSubject("Reset password confirmation");
				Map model = new HashMap();
				model.put("resetDate", LocaleUtil.getDateTimeFormat(DateFormat.LONG, DateFormat.LONG, LocaleUtil.getLocale(locale), new Date()));
				model.put("GOODcoinsLink", GOODcoinsLink);
				
				initVelocityEngine();
				String templateLocation = "net/zfp/velo/templates/password-reset-success-email_en.vm";
				/*
				String templateLocation = "net/zfp/velo/templates/carbon-password-reset-success-email_en.vm";
				if (locale != null && locale.equals("fr_CA")){
					templateLocation = "net/zfp/velo/templates/carbon-password-reset-success-email_fr.vm";
				}
				*/
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", model);
				message.setText(text, true);
			}
		});
		
	}

	@Override
	public void notifyReferJoin(final User invitee, final User inviter, Long accountGroupId, final String domainName){
		ResourceBundle rb =  ResourceBundle.getBundle("net/zfp/velo/velo-application");
		String http = rb.getString("rewards.redirect.http");
		String server = rb.getString("rewards.redirect.server");
		String path = rb.getString("rewards.redirect.path");
		
		final String GOODcoinsLink = http + domainName + server;
		
			final String link = http + domainName + server + path + "?operation=1&accountgroupid=" + accountGroupId +"&stamp=" + new Date().getTime()+"&inviteeemail=" + invitee.getEmail() + "&inviterid="+inviter.getId();
			this.getSpringMailSender().send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
					message.setTo(invitee.getEmail());
					message.setFrom(sendFromZerofootprint);
					message.setSubject(inviter.getFirstName() + " " + inviter.getLastName() + " has invited you to join the GOODcoins social rewards program");
					Map model = new HashMap();
					if (invitee.getFirstName()==null | invitee.getFirstName().trim().equals("")){
						model.put("name", invitee.getEmail());
					}else{
						model.put("name", invitee.getFirstName());
					}
					model.put("url", link);
					model.put("GOODcoinsLink", GOODcoinsLink);
					model.put("invitername", inviter.getFirstName());
					
					initVelocityEngine();
					String templateLocation = "net/zfp/velo/templates/refer-GOODcoins_en.vm";
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
					message.setText(text, true);
				}
			});
	}
	
	@Override
	public void notifyGroupJoin(final User invitee, final User inviter, Long accountGroupId,Boolean isGuest, final String domainName){
		
		ResourceBundle rb =  ResourceBundle.getBundle("net/zfp/velo/velo-application");
		String http = rb.getString("rewards.redirect.http");
		String server = rb.getString("rewards.redirect.server");
		String path = rb.getString("rewards.redirect.path");
		
		final String GOODcoinsLink = http + domainName + server;
		
		if (isGuest){
			final String link = http + domainName + server + path + "?operation=1&accountgroupid=" + accountGroupId +"&stamp=" + new Date().getTime()+"&inviteeemail=" + invitee.getEmail() + "&inviterid="+inviter.getId();
			this.getSpringMailSender().send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
					message.setTo(invitee.getEmail());
					message.setFrom(sendFromZerofootprint);
					message.setSubject(inviter.getFirstName() + " " + inviter.getLastName() + " added you as a friend on GOODcoins");
					Map model = new HashMap();
					if (invitee.getFirstName()==null | invitee.getFirstName().trim().equals("")){
						model.put("name", invitee.getEmail());
					}else{
						model.put("name", invitee.getFirstName());
					}
					model.put("url", link);
					model.put("GOODcoinsLink", GOODcoinsLink);
					model.put("invitername", inviter.getFirstName() + " " + inviter.getLastName());
					
					initVelocityEngine();
					String templateLocation = "net/zfp/velo/templates/invitation-GOODcoins-new-user-group_en.vm";
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
					message.setText(text, true);
				}
			});
			
		}else{
			final String link = http + domainName + server + path + "?operation=3&accountgroupid=" + accountGroupId +"&stamp=" + new Date().getTime() + "&inviteeemail=" + invitee.getEmail() + "&inviterId="+inviter.getId();
			this.getSpringMailSender().send(new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
					message.setTo(invitee.getEmail());
					message.setFrom(sendFromZerofootprint);
					message.setSubject(inviter.getFirstName() + " " + inviter.getLastName() + " added you as a friend on GOODcoins");
					Map model = new HashMap();
					if (invitee.getFirstName()==null | invitee.getFirstName().trim().equals("")){
						model.put("name", invitee.getEmail());
					}else{
						model.put("name", invitee.getFirstName());
					}
					model.put("url", link);
					model.put("GOODcoinsLink", GOODcoinsLink);
					model.put("invitername", inviter.getFirstName() + " " + inviter.getLastName());
					
					initVelocityEngine();
					String templateLocation = "net/zfp/velo/templates/invitation-GOODcoins-existing-user-group_en.vm";
					String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
					message.setText(text, true);
				}
			});
		}
		
	}
	
	@Override
	public void notifyAutoCampaignJoin(final User user, final Campaign campaign, final String domainName, final Integer coins){
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
		
		final String startDate = format.format(campaign.getStartDate());
		final String endDate = format.format(campaign.getEndDate());
		
		ResourceBundle rb =  ResourceBundle.getBundle("net/zfp/velo/velo-application");
		String http = rb.getString("rewards.redirect.http");
		String server = rb.getString("rewards.redirect.server");
		
		final String GOODcoinsLink = http + domainName + server + "/campaigns";
		
		this.getSpringMailSender().send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				message.setTo(user.getEmail());
				message.setFrom(sendFromZerofootprint);
				message.setSubject("Congratulations - you are now part of the " + campaign.getName() + " campaign!");
				Map model = new HashMap();
				if (user.getFirstName()==null | user.getFirstName().trim().equals("")){
					model.put("name", user.getEmail());
				}else{
					model.put("name", user.getFirstName());
				}
				
				model.put("coins", coins );
				model.put("campaignName", campaign.getName());
				model.put("campaignDescription", campaign.getDescription());
				model.put("period", campaign.getPeriod());
				model.put("GOODcoinsLink", GOODcoinsLink);
				model.put("periodType", campaign.getPeriodType().toLowerCase());
				
				initVelocityEngine();
				String templateLocation = "net/zfp/velo/templates/auto-join-campaign-email_en.vm";
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
				message.setText(text, true);
			}
		});
	}
	
	@Override
	public void notifyCampaignJoin(final User user, final Campaign campaign, final String domainName){
		SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
		
		final String startDate = format.format(campaign.getStartDate());
		final String endDate = format.format(campaign.getEndDate());
		
		ResourceBundle rb =  ResourceBundle.getBundle("net/zfp/velo/velo-application");
		String http = rb.getString("rewards.redirect.http");
		String server = rb.getString("rewards.redirect.server");
		
		final String GOODcoinsLink = http + domainName +  server + "/campaigns";
		
		this.getSpringMailSender().send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				message.setTo(user.getEmail());
				message.setFrom(sendFromZerofootprint);
				message.setSubject("You have joined the " + campaign.getName() + " campaign");
				Map model = new HashMap();
				if (user.getFirstName()==null | user.getFirstName().trim().equals("")){
					model.put("name", user.getEmail());
				}else{
					model.put("name", user.getFirstName());
				}
				model.put("campaignName", campaign.getName());
				model.put("startDate", startDate);
				model.put("endDate",endDate);
				model.put("GOODcoinsLink", GOODcoinsLink);
				
				initVelocityEngine();
				String templateLocation = "net/zfp/velo/templates/join-campaign-email_en.vm";
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, model);
				message.setText(text, true);
			}
		});
	}
	
	@Override
	public String sendCarbonPaymentConfirmEmail(final String from, final String to, 
			final String name, final String address, final List<CarbonTableView> ctvs, final String subTotal, 
			final String tax, final String taxType, final String carbon, final String total, final String locale) {

		String dateFormat = DateUtil.printCalendar2(new Date());
		if (locale !=null && locale.equals("fr_CA")){
			dateFormat = DateUtil.printCalendar2French(new Date());
		}
		
		final String filename = createCertificate(name, carbon, dateFormat, locale);
		
		final String dates  = dateFormat;
		this.getSpringMailSender().send(new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED, "UTF-8");
				//MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
				
				System.out.println("--------- Carbon Payment Thanks ----------" + " " + locale);
				System.out.println("TaxType::"+ taxType);
				message.setTo(to);
				message.setFrom(from);
				message.setSubject("Zerofootprint Carbon Offsets Purchase Confirmation");
				
				if (locale !=null && locale.equals("fr_CA")){
					message.setSubject("Confirmation d’achat de crédits compensatoires Zerofootprint");
				}
				Map model = new HashMap();
				model.put("name", name);
				model.put("address", address);
				model.put("ctvs", ctvs);
				model.put("subTotal", subTotal);
				model.put("tax", tax);
				model.put("taxType", taxType);
				model.put("carbon", carbon);
				model.put("total", total);
				model.put("date", dates);

				initVelocityEngine();
				//String templateLocation = "----" + messageSource.getMessage(REGISTRATION_CONFIRM_TEMPLATE, null, locale);
				
				String templateLocation = "";
				if (locale != null && locale.equals("fr_CA")){
					templateLocation = "net/zfp/velo/templates/carbon-payment-confirm-email_fr.vm";
				}else{
					templateLocation = "net/zfp/velo/templates/carbon-payment-confirm-email_en.vm";
				}
				String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateLocation, "UTF-8", model);

				FileSystemResource file = new FileSystemResource(SystemProperties.SERVER_REAL_PATH + "certificate/" + filename);
				message.addAttachment("SOP_Certificate.pdf", file);

				message.setText(text, true);
				
			}
		});
		
		return filename;
		
	}
	
	@Override
	public String createCertificate(String accountName, String value, String date, String locale){
		
		//File certificateDir = new File(SystemProperties.SERVER_PATH + "WEB-INF/certificate/");
		File certificateDir = new File(SystemProperties.SERVER_REAL_PATH + "certificate/");
		if ( !certificateDir.exists()) certificateDir.mkdir();
		
		
		String pdfName = "certificate_for_"+ accountName.replace(" ", "_")+"_"+System.currentTimeMillis()+".pdf";
		String filename = SystemProperties.SERVER_REAL_PATH + "certificate/" + pdfName;
		
		try{

			String l1 = "Carbon Offset Certificate";
			String l2 = "presented to";
			String l3 = "for an offset of " + value + " tonnes of CO";
			String l8 = "2";
			String l9 = "e\n\n";
			String l4 = "Zerofootprint thanks you for your voluntary commitment to the environment by " +
					"offsetting greenhouse gas emissions. Managing our carbon footprint is an important " +
					"element of a sustainability action plan to combat climate change.";
			String l5 = "Zerofootprint offsets comply with ISO 14064. Learn more at www.zerofootprintcarbon.com.";
			String l6 = "Issued: " + date;
			String l7= "__________________________\n\tRon Dembo\n\tFounder and CEO, Zerofootprint\n\tCarbon Inc.";
			
			if (locale != null && locale.equals("fr_CA")){
				l1 = "Certificat de compensation carbone";
				l2 = "présenté à";
				l3 = "pour avoir compensé " + value + " tonnes de CO";
				l8 = "2";
				l9 = "e\n\n";
				l4 = "Zerofootprint vous remercie de votre contribution à la protection de l’environnement en compensant des gaz à effet de serre. "+
						"Réduisant notre empreinte carbone est un élément nécessaire à un plan d’action pour combattre le changement climatique.";
				l5 = "Les crédits compensatoires Zerofootprint sont conformes à la norme ISO 14064. Voir plus à www.zerofootprintcarbon.com.";
				l6 = "Émis: " + date;
				l7 = "__________________________\n\tRon Dembo\n\tFondateur, Zerofootprint\n\tCarbon Inc.";
			}
			
			Document doc = new Document(new RectangleReadOnly(842, 595, 90), 50, 50, 50, 0);
			//String filename = "/Users/oliver/henry/SOP_CERTIFICATE/SOP_test.pdf";
			PdfWriter.getInstance(doc, new FileOutputStream(filename));

			doc.open();
			
			Image logo = Image.getInstance(SystemProperties.SERVER_REAL_PATH + "/images/zfp_logo.png");
			Image carbon = Image.getInstance(SystemProperties.SERVER_REAL_PATH + "/images/zfp_carbon.png");
			Image ron = Image.getInstance(SystemProperties.SERVER_REAL_PATH + "/images/zfp_ron.png");
			
			logo.setAlignment(Image.ALIGN_CENTER);
			//logo.setAbsolutePosition(10,10);
			logo.scalePercent(45);
			carbon.scalePercent(30);
			carbon.setAbsolutePosition(120, 70);
			ron.scalePercent(30);
			ron.setAbsolutePosition(500, 135);
			
			Paragraph p1 = new Paragraph(l1, FontFactory.getFont ( FontFactory.COURIER, 26, Font.BOLD, new BaseColor(0, 0, 0) ) );
			Paragraph p2 = new Paragraph(l2, FontFactory.getFont ( FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0) ) );
			Paragraph name = new Paragraph(accountName, FontFactory.getFont ( FontFactory.COURIER, 26, Font.BOLD, new BaseColor(0, 0, 0) ) );
			Paragraph p3 = new Paragraph(l3, FontFactory.getFont ( FontFactory.COURIER, 14, Font.BOLD, new BaseColor(0, 0, 0) ) );
			Paragraph p8 = new Paragraph();
			Paragraph p9 = new Paragraph(l9, FontFactory.getFont ( FontFactory.COURIER, 14, Font.BOLD, new BaseColor(0, 0, 0) ) );
			Paragraph p4 = new Paragraph(l4, FontFactory.getFont ( FontFactory.HELVETICA, 13, Font.NORMAL, new BaseColor(0, 0, 0) ) );
			Paragraph p5 = new Paragraph(l5, FontFactory.getFont ( FontFactory.HELVETICA, 13, Font.NORMAL, new BaseColor(0, 0, 0) ) );
			Paragraph p6 = new Paragraph();
			
			Paragraph p7 = new Paragraph(l7, FontFactory.getFont ( FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0) ) );
			p1.setAlignment(Paragraph.ALIGN_CENTER);
			p2.setAlignment(Paragraph.ALIGN_CENTER);
			name.setAlignment(Paragraph.ALIGN_CENTER);
			p3.setAlignment(Paragraph.ALIGN_CENTER);
			p9.setAlignment(Paragraph.ALIGN_CENTER);
			p4.setAlignment(Paragraph.ALIGN_CENTER);
			p5.setAlignment(Paragraph.ALIGN_CENTER);
			p7.setAlignment(Paragraph.ALIGN_LEFT);
			
			Chunk c8 = new Chunk(l8, FontFactory.getFont ( FontFactory.COURIER, 14, Font.BOLD, new BaseColor(0, 0, 0) ) );
			c8.setTextRise(-5f);
			p3.add(c8);
			
			Chunk c9 = new Chunk(l9, FontFactory.getFont ( FontFactory.COURIER, 14, Font.BOLD, new BaseColor(0, 0, 0) ) );
			p3.add(c9);
			
			Chunk c6 = new Chunk(l6,FontFactory.getFont ( FontFactory.COURIER, 14, Font.NORMAL, new BaseColor(0, 0, 0) ));
			c6.setTextRise(-120f);
			p6.add(c6);
			p6.setIndentationLeft(170f);

			Phrase ph = new Phrase();
			//p6.setIndentationLeft(200f);
			p7.setIndentationLeft(450f);
			
			//doc.add(chapter);
			doc.add(logo);
			doc.add(p1);
			doc.add(p2);
			doc.add(new Paragraph(" ", FontFactory.getFont ( FontFactory.COURIER, 10 ) ));
			doc.add(name);
			doc.add(new Paragraph(" ", FontFactory.getFont ( FontFactory.COURIER, 15 ) ));
			doc.add(p3);
			doc.add(p4);
			doc.add(p5);
			doc.add(carbon);
			doc.add(ron);
			doc.add(p6);
			//doc.add(c6);
			doc.add(new Paragraph(" ", FontFactory.getFont ( FontFactory.COURIER, 58 ) ));
			doc.add(p7);
			doc.addCreationDate();

			doc.close();
			
		}catch(WebApplicationException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return pdfName;
		
	}
	
	public static final Document getCertificatePdf(String name) throws DocumentException, MalformedURLException, IOException{
		
		Document doc = new Document();
		String filename = "/Users/oliver/henry/SOP_Certificate_" + name + ".pdf";
		PdfWriter.getInstance(doc, new FileOutputStream(filename));

		Image logo = Image.getInstance("/Users/oliver/henry/SOP_CERTIFICATE/zfp_logo.png");
		Image carbon = Image.getInstance("/Users/oliver/henry/SOP_CERTIFICATE/zfp_carbon.png");
		Image ron = Image.getInstance("/Users/oliver/henry/SOP_CERTIFICATE/zfp_ron.png");
		
		logo.setAlignment(Image.ALIGN_CENTER);
		
		doc.add(logo);
		return doc;
	}
	
	private void initVelocityEngine(){
		Properties props = System.getProperties();
		props.put("resource.loader", "class");
		props.put(	"class.resource.loader.class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		VelocityEngineFactoryBean v = new VelocityEngineFactoryBean();
		v.setVelocityProperties(props);
		try {
	        velocityEngine = v.createVelocityEngine();
	    } catch (VelocityException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private JavaMailSenderImpl getSpringMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties prop = System.getProperties();
		mailSender.setProtocol("smtp");
		mailSender.setHost("SMTP.EMAILSRVR.COM");
		mailSender.setPort(587);
		mailSender.setUsername("support@zerofootprint.net");
		mailSender.setPassword("Footprint1");
		prop.put("mail.smtp.timeout", "5000");
		prop.put("mail.smtp.connectiontimeout", "5000");
		prop.put("mail.debug", "false");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.quitwait", "false");
		mailSender.setJavaMailProperties(prop);
		return mailSender;
	}
	
	public void sendEmail() throws MessagingException{
		
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^I am sending email by Gmail^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^.");

		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        //props.setProperty("proxySet", "true");
        //props.setProperty("socksProxyHost", "192.168.155.1");
        //props.setProperty("socksProxyPort", "1080");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");

        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        final String username = "henry.zfp@gamil.com";
        final String password = "zfRoot#11";

        Session session = Session.getDefaultInstance(props,
            new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // -- Create a new message --
        Message msg = new MimeMessage(session);

        // -- Set the FROM and TO fields --
        msg.setFrom(new InternetAddress("henry.zfp@gamil.com"));
        msg.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse("henry.hou@zerofootprint.net", false));
        msg.setSubject("Hello");
        msg.setText("How are you");
        msg.setSentDate(new Date());
        Transport.send(msg);
        System.out.println("Message sent.");
		
	}
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

}
