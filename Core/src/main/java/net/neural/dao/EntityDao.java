package net.zfp.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.zfp.entity.AccountSource;
import net.zfp.entity.banner.MarketingBanner;
import net.zfp.entity.banner.MarketingBannerType;
import net.zfp.entity.banner.ProgramBanner;
import net.zfp.entity.category.Category;
import net.zfp.entity.category.MerchandisingCategory;
import net.zfp.entity.AccountSegment;
import net.zfp.entity.Account_Authority;
import net.zfp.entity.Domain;
import net.zfp.entity.EmailTemplate;
import net.zfp.entity.Factor;
import net.zfp.entity.FootprintType;
import net.zfp.entity.Fundraise;
import net.zfp.entity.Image;
import net.zfp.entity.Location;
import net.zfp.entity.News;
import net.zfp.entity.PersonalFootprintSurvey;
import net.zfp.entity.PointsAccount;
import net.zfp.entity.PortalType;
import net.zfp.entity.Source;
import net.zfp.entity.SourceHierarchy;
import net.zfp.entity.SourceType;
import net.zfp.entity.Status;
import net.zfp.entity.Translation;
import net.zfp.entity.Tweet;
import net.zfp.entity.Unit;
import net.zfp.entity.User;
import net.zfp.entity.UserRole;
import net.zfp.entity.VehicleType;
import net.zfp.entity.account.AccountGroup;
import net.zfp.entity.account.AccountGroupCommunicationStatus;
import net.zfp.entity.account.AccountCampaign;
import net.zfp.entity.account.MemberExtendedProfile;
import net.zfp.entity.account.MemberHouseholdProfile;
import net.zfp.entity.account.MemberProfileValue;
import net.zfp.entity.alert.AlertNews;
import net.zfp.entity.campaign.Campaign;
import net.zfp.entity.campaign.CampaignActionList;
import net.zfp.entity.campaign.CampaignFactList;
import net.zfp.entity.campaign.CampaignGroupProgress;
import net.zfp.entity.campaign.CampaignGroupProgressActivity;
import net.zfp.entity.campaign.CampaignProgress;
import net.zfp.entity.campaign.CampaignProgressActivity;
import net.zfp.entity.campaign.CampaignRule;
import net.zfp.entity.campaign.CampaignTarget;
import net.zfp.entity.carbon.CarbonFootprint;
import net.zfp.entity.carbon.CarbonTransaction;
import net.zfp.entity.carbon.CarbonTransactionDetail;
import net.zfp.entity.category.CategoryType;
import net.zfp.entity.channel.ChannelType;
import net.zfp.entity.charity.Fundraiser;
import net.zfp.entity.communication.CommunicationPreference;
import net.zfp.entity.community.Community;
import net.zfp.entity.community.CommunityFeature;
import net.zfp.entity.community.CommunityHierarchy;
import net.zfp.entity.community.CommunityPortal;
import net.zfp.entity.community.CommunityPortalTheme;
import net.zfp.entity.community.CommunityProduct;
import net.zfp.entity.community.CommunityNews;
import net.zfp.entity.community.CorporatePreference;
import net.zfp.entity.connector.Connector;
import net.zfp.entity.device.DeviceSourceType;
import net.zfp.entity.driving.DrivingDay;
import net.zfp.entity.driving.DrivingHour;
import net.zfp.entity.driving.DrivingMonth;
import net.zfp.entity.electricity.ElectricityAverage;
import net.zfp.entity.electricity.ElectricityBaseline;
import net.zfp.entity.electricity.ElectricityDataDay;
import net.zfp.entity.electricity.ElectricityDataHour;
import net.zfp.entity.electricity.ElectricityDataMinute;
import net.zfp.entity.electricity.ElectricityDataMonth;
import net.zfp.entity.electricity.ElectricityRate;
import net.zfp.entity.electricity.ElectricitySummary;
import net.zfp.entity.feature.FeatureTravelType;
import net.zfp.entity.group.GroupCampaign;
import net.zfp.entity.group.Groups;
import net.zfp.entity.group.GroupsType;
import net.zfp.entity.media.MediaImage;
import net.zfp.entity.media.MediaScreen;
import net.zfp.entity.media.MediaScreenAttributeValue;
import net.zfp.entity.media.MediaScreenList;
import net.zfp.entity.media.MediaStream;
import net.zfp.entity.memberactivity.MemberActivity;
import net.zfp.entity.memberactivity.MemberActivityType;
import net.zfp.entity.membership.MembershipNews;
import net.zfp.entity.mobile.MobileDevice;
import net.zfp.entity.newscontent.NewsContentType;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.offer.OfferBehavior;
import net.zfp.entity.offer.OfferBehaviorAttribute;
import net.zfp.entity.offer.OfferBonus;
import net.zfp.entity.offer.OfferPromoCode;
import net.zfp.entity.offer.OfferValue;
import net.zfp.entity.partner.BusinessPartner;
import net.zfp.entity.partner.BusinessPartnerType;
import net.zfp.entity.partner.PartnerActivityType;
import net.zfp.entity.partner.PartnerPointsAccount;
import net.zfp.entity.personalmotion.CyclingDataDay;
import net.zfp.entity.personalmotion.CyclingDataDayAverage;
import net.zfp.entity.personalmotion.CyclingDataHour;
import net.zfp.entity.personalmotion.CyclingDataMonth;
import net.zfp.entity.personalmotion.PersonalMotion;
import net.zfp.entity.personalmotion.PersonalMotionInterval;
import net.zfp.entity.personalmotion.RunningDataDay;
import net.zfp.entity.personalmotion.RunningDataDayAverage;
import net.zfp.entity.personalmotion.RunningDataHour;
import net.zfp.entity.personalmotion.RunningDataMonth;
import net.zfp.entity.personalmotion.WalkingDay;
import net.zfp.entity.personalmotion.WalkingDayAverage;
import net.zfp.entity.personalmotion.WalkingHour;
import net.zfp.entity.personalmotion.WalkingBoundary;
import net.zfp.entity.personalmotion.WalkingMonth;
import net.zfp.entity.portal.PortalLayout;
import net.zfp.entity.print.PrintDataMinute;
import net.zfp.entity.product.Product;
import net.zfp.entity.product.ProductBanner;
import net.zfp.entity.product.ProductSKU;
import net.zfp.entity.provider.Provider;
import net.zfp.entity.provider.ProviderAccount;
import net.zfp.entity.provider.ProviderDevice;
import net.zfp.entity.provider.ProviderDeviceMobileReference;
import net.zfp.entity.provider.ProviderSource;
import net.zfp.entity.provider.ProviderType;
import net.zfp.entity.salesorder.PointsTransaction;
import net.zfp.entity.salesorder.PointsTransactionType;
import net.zfp.entity.salesorder.SalesOrder;
import net.zfp.entity.salesorder.SalesOrderBilling;
import net.zfp.entity.salesorder.SalesOrderDetail;
import net.zfp.entity.salesorder.SalesOrderReferenceType;
import net.zfp.entity.salesorder.SalesOrderType;
import net.zfp.entity.segment.Segment;
import net.zfp.entity.segment.SegmentType;
import net.zfp.entity.source.SourceMode;
import net.zfp.entity.source.SourceUnit;
import net.zfp.entity.survey.Survey;
import net.zfp.entity.survey.SurveyAnswerChoices;
import net.zfp.entity.survey.SurveyAnswerRanges;
import net.zfp.entity.survey.SurveyAnswerValueType;
import net.zfp.entity.survey.SurveyAnswers;
import net.zfp.entity.survey.SurveyCategory;
import net.zfp.entity.survey.SurveyFactor;
import net.zfp.entity.survey.SurveyQuestions;
import net.zfp.entity.survey.SurveySummary;
import net.zfp.entity.survey.SurveyTips;
import net.zfp.entity.tax.TaxLocation;
import net.zfp.entity.tax.TaxValue;
import net.zfp.entity.tips.Tips;
import net.zfp.entity.utility.UtilityConnectionRequest;
import net.zfp.entity.utility.UtilityType;
import net.zfp.entity.wallet.PointsType;
import net.zfp.entity.weather.ForecastWeatherData;
import net.zfp.form.TranslationForm;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class EntityDao<T> {

	private EntityManager entityManager;

	private Class<T> type;

	public EntityDao() {
	}

	public EntityDao(Class<T> type) {
		this.type = type;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public String getTrackingId(String domainName) {
		Query query = entityManager.createQuery("select d.trackingId from Domain d where d.name=:name").setParameter("name", domainName);
		
		try {
			return (String) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Long getCommunityId(String communityCode) {
		Query query = entityManager.createQuery("select c.id from Community c where c.code=:code").setParameter("code", communityCode);
		
		try {
			return (Long) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CorporatePreference getCorporatePreference(Long communityId) {
		Query query = entityManager.createQuery("select c from CorporatePreference c where c.community.id=:communityId").setParameter("communityId", communityId);
		
		try {
			return (CorporatePreference) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Community> getCorporateCommunitiesByCategory(Long categoryId) {
		Query query = entityManager.createQuery("select c.community from CorporatePreference c where c.corporateCategory.id = :categoryId").setParameter("categoryId", categoryId);
		
		try {
			return (List<Community>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Domain getDomainByCommunityId(Long communityId) {
		Query query = entityManager.createQuery("select d from Domain d where d.community.id =:communityId").setParameter("communityId", communityId);
		
		try {
			return (Domain) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Long getCommunitId(String communityName) {
		Query query = entityManager.createQuery("select d.community.id from Domain d where d.name=:name").setParameter("name", communityName);
		
		try {
			return (Long) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Based on domain the function will return associated community
	 * 
	 * @param domainName
	 * @return community associated with domain
	 */
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Community getCommunity(String domainName) {
		Query query = entityManager.createQuery("select c from Domain d join d.community c where d.name=:name");
		query.setParameter("name", domainName);
		try {
			return (Community) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public UserRole getUserRole(String role) {
		Query query = entityManager.createQuery("select a from Authority a where a.authority=:role");
		query.setParameter("role", role);
		try {
			return (UserRole) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public SegmentType getSegmentType(String type) {
		Query query = entityManager.createQuery("select s from SegmentType s where s.type= :type");
		query.setParameter("type", type);
		try {
			return (SegmentType) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Segment> getSegmentsByType(Integer typeId) {
		Query query = entityManager.createQuery("select s from Segment s where s.segmentType.id = :typeId");
		query.setParameter("typeId", typeId);
		return (List<Segment>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Segment> getSegmentsByTypeAndCommunity(Integer typeId, Long communityId) {
		Query query = entityManager.createQuery("select s from Segment s where s.segmentType.id = :typeId and s.community.id = :communityId");
		query.setParameter("typeId", typeId);
		query.setParameter("communityId", communityId);
		return (List<Segment>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Segment> getSegmentByAccount(Long accountId) {
		Query query = entityManager.createQuery("select a.segment from AccountSegment a where a.user.id = :accountId");
		query.setParameter("accountId", accountId);
		return (List<Segment>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Segment> getSegmentByGroup(Long groupId) {
		Query query = entityManager.createQuery("select g.segment from GroupSegment g where g.groups.id = :groupId");
		query.setParameter("groupId", groupId);
		return (List<Segment>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Segment getSegmentByCode(String code, Long communityId) {
		Query query = entityManager.createQuery("select s from Segment s where s.code = :code and s.community.id = :communityId");
		query.setParameter("code", code);
		query.setParameter("communityId", communityId);
		try {
			return (Segment) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public AccountSegment getSegmentByAccountSegmentCode(Long accountId, String code, Long communityId) {
		Query query = entityManager.createQuery("select a from AccountSegment a where a.user.id = :accountId and a.segment.code = :code and a.segment.community.id = :communityId");
		query.setParameter("accountId", accountId);
		query.setParameter("code", code);
		query.setParameter("communityId", communityId);
		try {
			return (AccountSegment) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<News> getNews(Long communityId, Long portalTypeId) {
		Query query = entityManager.createQuery("select n from News n where (n.community.id = :communityId or n.community.id is NULL) and (n.portalType.id = :portalTypeId or n.portalType.id is NULL) order by n.created DESC");
		query.setParameter("communityId",  communityId);
		query.setParameter("portalTypeId",  portalTypeId);
		return (List<News>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<SourceType> getTipsSourceTypes(Long tipCategoryId) {
		Query query = entityManager.createQuery("select t.sourceType from Tips t where t.tipsCategory.id = :tipCategoryId group by t.sourceType");
		query.setParameter("tipCategoryId",  tipCategoryId);
		return (List<SourceType>) query.getResultList();
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Tips> getAllTips(Long tipCategoryId) {
		Query query = entityManager.createQuery("select t from Tips t where (t.tipsCategory.id = :tipCategoryId or t.tipsCategory.id = 1) order by t.relevance ASC");
		query.setParameter("tipCategoryId",  tipCategoryId);
		return (List<Tips>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Tips> getAllTips(Long tipCategoryId, Long sourceTypeId) {
		Query query = entityManager.createQuery("select t from Tips t where (t.tipsCategory.id = :tipCategoryId or t.tipsCategory.id = 1) and t.sourceType.id = :sourceTypeId order by t.relevance ASC");
		query.setParameter("tipCategoryId",  tipCategoryId);
		query.setParameter("sourceTypeId",  sourceTypeId);
		query.setMaxResults(2);
		return (List<Tips>) query.getResultList();
	}
	
	//************************************************ Offer ********************************************************************************
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getBusinessPartnerOffers(Long partnerId) {
		Query query = entityManager.createQuery(
				"select o from Offer o where o.businessPartner.id = :partnerId");
		query.setParameter("partnerId",  partnerId);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getOffers(String typeName, Long communityId, Date currentDate) {
		Query query = entityManager.createQuery(
				"select o from Offer o where o.offerType.name =:typeName and o.community.id = :communityId and o.startDate <= :currentDate and o.endDate >= :currentDate and o.status.code = 'ACTIVE'"+
						"or o.name in (select o1.name from Offer o1 where o1.offerTemplate.offerType.name =:typeName and o1.startDate <= :currentDate and o1.endDate >= :currentDate and o1.community.id is NULL and "+
						"o1.name not in (select o2.name from Offer o2 where o2.offerTemplate.offerType.name =:typeName and o2.community.id = :communityId and o2.startDate <= :currentDate and o2.endDate >= :currentDate)) " +
						"order by o.startDate DESC");
		query.setParameter("typeName",  typeName);
		query.setParameter("communityId",  communityId);
		query.setParameter("currentDate",  currentDate);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getOffersByCategoryAndType(Long categoryId, String typeName, Long communityId, Date today) {
		Query query = entityManager.createQuery(
				"select o.offer from OfferCategory o where o.category.id = :categoryId and o.offer.offerTemplate.offerType.name = :typeName" +
				 " and (o.offer.community.id = :communityId or o.offer.community.id is NULL) and o.offer.startDate <= :today and o.offer.endDate >= :today" +
						" and o.offer.status.code = 'ACTIVE'");
		query.setParameter("categoryId",  categoryId);
		query.setParameter("typeName",  typeName);
		query.setParameter("communityId",  communityId);
		query.setParameter("today",  today);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getOffersByCategory(Long categoryId, List<Segment> segments) {
		String hql = "select o.offer from OfferCategory o where o.category.id = :categoryId and o.offer.status.code = 'ACTIVE' and o.offer.startDate <= :currentDate and o.offer.endDate >= :currentDate";
		
		if (segments != null && segments.size() > 0){
			hql += " and (";
			for (int i = 0; i < segments.size(); i++){
				Segment segment = segments.get(i);
				hql += "o.offer.segment.id= " + segment.getId();
				if (i < (segments.size()-1)) hql += " or ";
				
			}
			hql += " or o.offer.segment.id is null";
			hql += ") "; 
		}
		
		hql += "order by o.offer.startDate DESC";
		Date currentDate = new Date();
		Query query = entityManager.createQuery(hql);
		query.setParameter("categoryId",  categoryId);
		query.setParameter("currentDate",  currentDate);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getOffersByTopNumberByCommunity(Integer topNumber, Long communityId){
		String hql = "select o.offer from OfferCategory o where o.offer.status.code = 'ACTIVE' and o.offer.startDate <= :currentDate and o.offer.endDate >= :currentDate ";
		hql += " and o.offer.community.id = :communityId ";
		hql += "order by o.offer.startDate DESC";
			
		Date currentDate = new Date();
		Query query = entityManager.createQuery(hql);
		query.setParameter("communityId", communityId);
		query.setParameter("currentDate",  currentDate);
		query.setMaxResults(topNumber);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getOffersByTopNumber(Integer topNumber, List<Segment> segments, Long communityId){
		String hql = "select o.offer from OfferCategory o where o.offer.status.code = 'ACTIVE' and o.offer.startDate <= :currentDate and o.offer.endDate >= :currentDate ";
		if (segments != null && segments.size() > 0){
			hql += "and (";
			for (int i = 0; i < segments.size(); i++){
				Segment segment = segments.get(i);
				hql += "o.offer.segment.id= " + segment.getId();
				if (i < (segments.size()-1)) hql += " or ";
				
			}
			hql += " or o.offer.segment.id is null";
			hql += ") "; 
		}
		//hql += " and o.offer.community.id != :communityId ";
		//hql += "order by RAND()";
		hql += "order by o.offer.startDate DESC";
		
		System.out.println("TOP NUMBER :: " + topNumber + " : " + communityId);
		
		System.out.println(hql);
		
		Date currentDate = new Date();
		Query query = entityManager.createQuery(hql);
		query.setParameter("currentDate",  currentDate);
		query.setMaxResults(topNumber);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getOffersByCategoryAndTopNumber(Long categoryId, Integer topNumber, List<Segment> segments, Long communityId) {
		String hql = "select o.offer from OfferCategory o where o.category.id = :categoryId and o.offer.status.code = 'ACTIVE' and o.offer.startDate <= :currentDate and o.offer.endDate >= :currentDate ";
		if (segments != null && segments.size() > 0){
			hql += "and (";
			for (int i = 0; i < segments.size(); i++){
				Segment segment = segments.get(i);
				hql += "o.offer.segment.id= " + segment.getId();
				hql += " or ";
			}
			hql += "o.offer.segment.id is null";
			//hql += " or (o.offer.segment.id is NULL and o.offer.community.id = :communityId)";
			hql += ") ";
		}
		hql += "order by o.offer.startDate DESC";
		Date currentDate = new Date();
		Query query = entityManager.createQuery(hql);
		query.setParameter("categoryId",  categoryId);
		query.setParameter("currentDate",  currentDate);
		query.setMaxResults(topNumber);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Offer getOffer(Long offerId) {
		Query query = entityManager.createQuery(
				"select o from Offer o where o.id =:offerId ");
		query.setParameter("offerId",  offerId);
		try {
			return (Offer) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Offer getOfferByCode(Long offerCode) {
		Query query = entityManager.createQuery(
				"select o from Offer o where o.code =:offerCode and o.status.code = 'ACTIVE'");
		query.setParameter("offerCode",  offerCode);
		try {
			return (Offer) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public OfferBehavior getOfferBehavior(String actionCode, String resultsTypeCode) {
		Query query = entityManager.createQuery(
				"select ob from OfferBehavior ob where ob.behaviorAction.code = :actionCode and ob.behaviorTrackerType.code = :resultsTypeCode ");
		query.setParameter("actionCode",  actionCode);
		query.setParameter("resultsTypeCode",  resultsTypeCode);
		try {
			return (OfferBehavior) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getOfferByBehaviorActions(String trackerType, List<Segment> segments, String attributeId, Date currentDate) {
		
		String hql = "select ob.offer from OfferBehaviorAttribute ob where ob.behaviorAttribute = :attributeId and ob.offerBehavior.behaviorTrackerType.code = :trackerType and (ob.offer.status.code='ACTIVE' or ob.offer.status.code= 'IN_PROGRESS' or ob.offer.status.code = 'COMPLETED') and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate ";
		
		if (segments != null && segments.size() > 0){
			hql += " and (";
			for (int i = 0; i < segments.size(); i++){
				Segment segment = segments.get(i);
				hql += "ob.offer.segment.id= " + segment.getId();
				if (i < (segments.size()-1)) hql += " or ";
				
			}
			hql += " or ob.offer.segment.id is null";
			hql += ") "; 
		}
		
		hql += "order by ob.offer.name ASC";
		Query query = entityManager.createQuery(hql);
		
		query.setParameter("trackerType",  trackerType);
		query.setParameter("attributeId",  attributeId);
		query.setParameter("currentDate",  currentDate);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getOfferByBehaviorActionsAndTrackerTypeAndAttributeId(String trackerType, String action, List<Segment> segments, String attributeId, Date currentDate) {
		
		String hql = "select ob.offer from OfferBehaviorAttribute ob where ob.behaviorAttribute = :attributeId and ob.offerBehavior.behaviorAction.code = :action and ob.offerBehavior.behaviorTrackerType.code = :trackerType and (ob.offer.status.code='ACTIVE' or ob.offer.status.code= 'IN_PROGRESS' or ob.offer.status.code = 'COMPLETED') and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate ";
		
		if (segments != null && segments.size() > 0){
			hql += " and (";
			for (int i = 0; i < segments.size(); i++){
				Segment segment = segments.get(i);
				hql += "ob.offer.segment.id= " + segment.getId();
				if (i < (segments.size()-1)) hql += " or ";
				
			}
			hql += " or ob.offer.segment.id is null";
			hql += ") "; 
		}
		
		hql += "order by ob.offer.name ASC";
		Query query = entityManager.createQuery(hql);
		query.setParameter("action",  action);
		query.setParameter("trackerType",  trackerType);
		query.setParameter("attributeId",  attributeId);
		query.setParameter("currentDate",  currentDate);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getOfferByBehaviorActionsAndTrackerType(String trackerType, String action, List<Segment> segments, Date currentDate) {
		
		String hql = "select ob.offer from OfferBehaviorAttribute ob where ob.offerBehavior.behaviorAction.code = :action and ob.offerBehavior.behaviorTrackerType.code = :trackerType and (ob.offer.status.code='ACTIVE' or ob.offer.status.code= 'IN_PROGRESS' or ob.offer.status.code = 'COMPLETED') and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate ";
		
		if (segments != null && segments.size() > 0){
			hql += " and (";
			for (int i = 0; i < segments.size(); i++){
				Segment segment = segments.get(i);
				hql += "ob.offer.segment.id= " + segment.getId();
				if (i < (segments.size()-1)) hql += " or ";
				
			}
			hql += " or ob.offer.segment.id is null";
			hql += ") "; 
		}
		
		hql += "order by ob.offer.name ASC";
		Query query = entityManager.createQuery(hql);
		query.setParameter("action",  action);
		query.setParameter("trackerType",  trackerType);
		query.setParameter("currentDate",  currentDate);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Offer> getOfferByBehaviorActions(String actionCode, List<Segment> segments, Date currentDate) {
		
		String hql = "select ob.offer from OfferBehaviorAttribute ob where ob.offerBehavior.behaviorAction.code = :actionCode and (ob.offer.status.code='ACTIVE' or ob.offer.status.code= 'IN_PROGRESS' or ob.offer.status.code = 'COMPLETED') and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate ";
		
		if (segments != null && segments.size() > 0){
			hql += " and (";
			for (int i = 0; i < segments.size(); i++){
				Segment segment = segments.get(i);
				hql += "ob.offer.segment.id= " + segment.getId();
				if (i < (segments.size()-1)) hql += " or ";
				
			}
			hql += " or ob.offer.segment.id is null";
			hql += ") "; 
		}
		
		hql += "order by ob.offer.name ASC";
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("actionCode",  actionCode);
		query.setParameter("currentDate",  currentDate);
		return (List<Offer>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<OfferValue> getOfferValueByOffer(Long offerCode) {
		
		String hql = "select ov from OfferValue ov where ov.offer.code = :offerCode";
				
		Query query = entityManager.createQuery(hql);
		query.setParameter("offerCode",  offerCode);
		return (List<OfferValue>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<OfferValue> getOfferValueByOfferAndCode(Long offerCode, String unitCode) {
		
		String hql = "select ov from OfferValue ov where ov.offer.code = :offerCode and ov.unit.code = :unitCode";
				
		Query query = entityManager.createQuery(hql);
		query.setParameter("offerCode",  offerCode);
		query.setParameter("unitCode",  unitCode);
		return (List<OfferValue>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public OfferPromoCode getOfferPromoCode(String promoCode, Date currentDate) {
		Query query = entityManager.createQuery(
				"select opc from OfferPromoCode opc where opc.promoCode = :promoCode and opc.offerBonus.startDate <= :currentDate and opc.offerBonus.endDate >= :currentDate");
		query.setParameter("promoCode",  promoCode);
		query.setParameter("currentDate",  currentDate);
		try {
			return (OfferPromoCode) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public OfferBonus getOfferBonus (Long offerCode, Date currentDate, Boolean promoRequired) {
		Query query = entityManager.createQuery(
				"select ob from OfferBonus ob where ob.offer.code = :offerCode and ob.startDate <= :currentDate and ob.endDate >= :currentDate and ob.promoCodeRequired = :promoRequired");
		query.setParameter("offerCode",  offerCode);
		query.setParameter("currentDate",  currentDate);
		query.setParameter("promoRequired",  promoRequired);
		try {
			return (OfferBonus) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public OfferBehaviorAttribute getOfferBehaviorAttribute (Long offerId, Long offerBehaviorId) {
		Query query = entityManager.createQuery(
				"select ob from OfferBehaviorAttribute ob where ob.offer.id = :offerId and ob.offerBehavior.id = :offerBehaviorId");
		query.setParameter("offerId",  offerId);
		query.setParameter("offerBehaviorId",  offerBehaviorId);
		try {
			return (OfferBehaviorAttribute) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Offer getOfferByBehaviorAndSegments (Long offerBehaviorId,String attribute, Date currentDate, List<Segment> segments) {
		String hql = "select ob.offer from OfferBehaviorAttribute ob where ob.offerBehavior.id = :offerBehaviorId and ob.behaviorAttribute = :attribute and ob.offer.status.code='ACTIVE' and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate";
		
		if (segments != null && segments.size() > 0){
			hql += " and (";
			for (int i = 0; i < segments.size(); i++){
				Segment segment = segments.get(i);
				hql += "ob.offer.segment.id= " + segment.getId();
				if (i < (segments.size()-1)) hql += " or ";
				
			}
			hql += " or ob.offer.segment.id is null";
			hql += ") "; 
		}
		Query query = entityManager.createQuery(hql);
		query.setParameter("offerBehaviorId",  offerBehaviorId);
		query.setParameter("attribute",  attribute);
		query.setParameter("currentDate",  currentDate);
		try {
			return (Offer) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Offer getOfferByBehavior (Long offerBehaviorId, Date currentDate) {
		
		Query query = entityManager.createQuery(
				"select ob.offer from OfferBehaviorAttribute ob where ob.offerBehavior.id = :offerBehaviorId and ob.offer.status.code='ACTIVE' and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate");
		query.setParameter("offerBehaviorId",  offerBehaviorId);
		query.setParameter("currentDate",  currentDate);
		try {
			return (Offer) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Offer getOfferByBehaviorAndAttribute (Long offerBehaviorId, String attribute, Date currentDate) {
	
		Query query = entityManager.createQuery(
				"select ob.offer from OfferBehaviorAttribute ob where ob.offerBehavior.id = :offerBehaviorId and ob.behaviorAttribute = :attribute and ob.offer.status.code='ACTIVE' and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate");
		query.setParameter("offerBehaviorId",  offerBehaviorId);
		query.setParameter("attribute",  attribute);
		query.setParameter("currentDate",  currentDate);
		try {
			return (Offer) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Offer getOfferByBehaviorAndAttribute (Long offerBehaviorId, String attribute) {
	
		Query query = entityManager.createQuery(
				"select ob.offer from OfferBehaviorAttribute ob where ob.offerBehavior.id = :offerBehaviorId and ob.behaviorAttribute = :attribute");
		query.setParameter("offerBehaviorId",  offerBehaviorId);
		query.setParameter("attribute",  attribute);
		try {
			return (Offer) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Offer getOfferByBehaviorActionAndOfferCode (Long offerBehaviorId, Long offerCode) {
		Date currentDate = new Date();
		Query query = entityManager.createQuery(
				"select ob.offer from OfferBehaviorAttribute ob where ob.offer.code = :offerCode and ob.offerBehavior.id = :offerBehaviorId and ob.offer.status.code='ACTIVE' and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate");
		query.setParameter("offerCode",  offerCode);
		query.setParameter("currentDate",  currentDate);
		query.setParameter("offerBehaviorId",  offerBehaviorId);
		try {
			return (Offer) query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Offer getOfferByBehaviorAttribute (Long offerBehaviorId, String attribute) {
		Date currentDate = new Date();
		Query query = entityManager.createQuery(
				"select ob.offer from OfferBehaviorAttribute ob where ob.behaviorAttribute = :attribute and ob.offerBehavior.id = :offerBehaviorId and ob.offer.status.code='ACTIVE' and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate");
		query.setParameter("attribute",  attribute);
		query.setParameter("currentDate",  currentDate);
		query.setParameter("offerBehaviorId",  offerBehaviorId);
		try {
			return (Offer) query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Offer getProductOffer(String UPC, String attribute, String offerType) {
		Query query = entityManager.createQuery(
				"select ob.offer from OfferBehaviorAttribute ob where ob.offerBehavior.behaviorTrackerType.code = :attribute and ob.behaviorAttribute = :UPC and ob.offer.status.code='ACTIVE' and ob.offer.offerTemplate.offerType.name = :offerType ");
		query.setParameter("UPC",  UPC);
		query.setParameter("attribute",  attribute);
		query.setParameter("offerType",  offerType);
		try {
			return (Offer) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	//************************************************End of Offer***************************************************************************
	
	//************************************************ Location ********************************************************************************
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Long getLocationId(Double latitude, Double longitude) {
		Query query = entityManager.createQuery(
				"select l.id from Location l order by sqrt((power(:latitude - l.latitude, 2) + power(:longitude - l.longitude, 2))) ASC");
		query.setParameter("latitude",  latitude);
		query.setParameter("longitude",  longitude);
		query.setMaxResults(1);
		try {
			return (Long) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public MemberHouseholdProfile getMemberHouseholdProfileByMember(Long memberId) {
		Query query = entityManager.createQuery(
				"select m from MemberHouseholdProfile m where m.user.id = :memberId");
		query.setParameter("memberId",  memberId);
		query.setMaxResults(1);
		try {
			return (MemberHouseholdProfile) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Location> getWeatherLocationByCommunity(Long communityId) {
		Query query = entityManager.createQuery(
				"select distinct m.location from MemberExtendedProfile m where m.user.defaultCommunity = :communityId");
		query.setParameter("communityId",  communityId);
		
		return (List<Location>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<User> getMembersByLocation(Long locationId) {
		Query query = entityManager.createQuery(
				"select distinct m.user from MemberExtendedProfile m where m.location.id = :locationId");
		query.setParameter("locationId",  locationId);
		
		return (List<User>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public User getUserByUID(String uid) {
		Query query = entityManager.createQuery("select u from Account u where u.uid = :uid");
		query.setParameter("uid",  uid);
		query.setMaxResults(1);
		try {
			return (User) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public MemberProfileValue getMemberProfileValue(String value) {
		Query query = entityManager.createQuery(
				"select m from MemberProfileValue m where m.value = :value");
		query.setParameter("value",  value);
		query.setMaxResults(1);
		try {
			return (MemberProfileValue) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public MemberExtendedProfile getMemberExtendedProfile (Long memberId) {
		Query query = entityManager.createQuery(
				"select m from MemberExtendedProfile m where m.user.id = :memberId");
		query.setParameter("memberId",  memberId);
		query.setMaxResults(1);
		try {
			return (MemberExtendedProfile) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	//******************************************************************************************************************************************
	//************************************************ MarketingBanner *********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<Fundraiser> getCharityBySegments(List<Segment> segments, Date date) {
			String hql = "";
			hql += "select c from Fundraiser c where c.status.code ='ACTIVE' and c.endDate >= :date";
			if (segments != null && segments.size() > 0){
				hql += " and (";
				for (int i = 0; i < segments.size(); i++){
					Segment user = segments.get(i);
					hql += "c.segment.id=" + user.getId();
					if (i < (segments.size()-1)) hql += " or ";
				}
				hql += " or c.segment is NULL";
				hql += ")";
			}
			Query query = entityManager.createQuery(hql);
			query.setParameter("date",  date);
			return (List<Fundraiser>) query.getResultList();
			
		}	
		
	
	//*************************************************End of MarketingBanner **************************************************************************
	//************************************************ MarketingBanner *********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public MarketingBannerType getMarketingBannerType(String bannerType) {
			Query query = entityManager.createQuery(
					"select m from MarketingBannerType m where m.type =:bannerType");
			query.setParameter("bannerType",  bannerType);
			query.setMaxResults(1);
			try {
				return (MarketingBannerType) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}	
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<MarketingBanner> getMarketingBanner(Long communityId, Long typeId) {
			Query query = entityManager.createQuery(
					"select m from MarketingBanner m where m.community.id = :communityId and m.type.id = :typeId");
			query.setParameter("communityId",  communityId);
			query.setParameter("typeId",  typeId);
			
			return (List<MarketingBanner>) query.getResultList();
			
		}	
	
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<ProgramBanner> getProgramBanner(Long communityId) {
			Query query = entityManager.createQuery(
					"select m from ProgramBanner m where m.community.id = :communityId");
			query.setParameter("communityId",  communityId);
			
			return (List<ProgramBanner>) query.getResultList();
			
		}
	//*************************************************End of MarketingBanner **************************************************************************
	//************************************************ Weather *********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public ForecastWeatherData getForecastWeatherData(Long locationId, Date date) {
		Query query = entityManager.createQuery(
				"select fw from ForecastWeatherData fw where fw.location.id = :locationId and YEAR(fw.forecastDate) = YEAR(:date) and MONTH(fw.forecastDate) = MONTH(:date)" +
				" and DAY(fw.forecastDate) = DAY(:date) order by fw.forecastDate DESC");
		query.setParameter("date",  date);
		query.setParameter("locationId",  locationId);
		query.setMaxResults(1);
		try {
			return (ForecastWeatherData) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}	
	
	//*************************************************End of Weather **************************************************************************
	
	//************************************************ Provider ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Provider getProvider(String providerCode) {
		Query query = entityManager.createQuery(
				"select p from Provider p where p.code =:providerCode");
		query.setParameter("providerCode",  providerCode);
		try {
			return (Provider) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}	
		
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public ProviderType getProviderType(String type) {
		Query query = entityManager.createQuery(
				"select p from ProviderType p where p.type =:type");
		query.setParameter("type",  type);
		try {
			return (ProviderType) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Provider> getProvidersByType(String providerType) {
		Query query = entityManager.createQuery("select p from Provider p where p.providerType.type = :providerType");
		query.setParameter("providerType",  providerType);
		return (List<Provider>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ProviderDeviceMobileReference> getProviderDeviceMobileReferences(Long providerDeviceId) {
		Query query = entityManager.createQuery("select p from ProviderDeviceMobileReference p where p.providerDevice.id = :providerDeviceId");
		query.setParameter("providerDeviceId",  providerDeviceId);
		return (List<ProviderDeviceMobileReference>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ProviderAccount> getProviderAccountsByProviderAndUser(Long providerId, Long memberId) {
		Query query = entityManager.createQuery("select p from ProviderAccount p where p.provider.id = :providerId and p.user.id = :memberId");
		query.setParameter("providerId",  providerId);
		query.setParameter("memberId",  memberId);
		return (List<ProviderAccount>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ProviderAccount> getProviderAccountsByProviderAndUserCode(Long providerId, Long memberId, String sourceCode) {
		Query query = entityManager.createQuery("select p from ProviderAccount p where p.provider.id = :providerId and p.user.id = :memberId and p.code = :sourceCode");
		query.setParameter("providerId",  providerId);
		query.setParameter("memberId",  memberId);
		query.setParameter("sourceCode",  sourceCode);
		return (List<ProviderAccount>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ProviderSource> getProviderSourceByProviderAndUserCode(Long providerId, Long memberId, String sourceCode) {
		Query query = entityManager.createQuery("select p from ProviderSource p, AccountSource s where p.provider.id = :providerId and s.user.id = :memberId and s.source.id = p.source.id and p.source.code = :sourceCode");
		query.setParameter("providerId",  providerId);
		query.setParameter("memberId",  memberId);
		query.setParameter("sourceCode",  sourceCode);
		return (List<ProviderSource>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Source> getProviderSources(Long memberId, String sourceCode, String providerCode, String providerType) {
		Query query = entityManager.createQuery("select a.source from AccountSource a, ProviderSource ps where a.user.id = :memberId and a.source.id = ps.source.id and " +
				" ps.provider.code = :providerCode and ps.provider.providerType.type = :providerType and a.source.code = :sourceCode order by a.status.id");
		query.setParameter("memberId",  memberId);
		query.setParameter("sourceCode",  sourceCode);
		query.setParameter("providerCode",  providerCode);
		query.setParameter("providerType",  providerType);
		return (List<Source>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Source> getProviderSources(Long memberId, Long providerId) {
		Query query = entityManager.createQuery("select a.source from AccountSource a, ProviderSource ps where a.user.id = :memberId and a.source.id = ps.source.id and " +
				" ps.provider.id = :providerId order by a.status.id, a.source.lastModified DESC");
		query.setParameter("memberId",  memberId);
		query.setParameter("providerId",  providerId);
		return (List<Source>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Source> getProviderSourcesBySourceType(Long memberId, Long providerId, String sourceType) {
		Query query = entityManager.createQuery("select a.source from AccountSource a, ProviderSource ps where a.user.id = :memberId and a.source.id = ps.source.id and " +
				" ps.provider.id = :providerId and a.source.sourceType.code = :sourceType order by a.status.id, a.source.lastModified DESC");
		query.setParameter("memberId",  memberId);
		query.setParameter("providerId",  providerId);
		query.setParameter("sourceType",  sourceType);
		return (List<Source>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Source> getProviderActiveSources(Long memberId, Long providerId) {
		Query query = entityManager.createQuery("select a.source from AccountSource a, ProviderSource ps where a.user.id = :memberId and a.source.id = ps.source.id and " +
				" ps.provider.id = :providerId and a.status.code = 'ACTIVE'");
		query.setParameter("memberId",  memberId);
		query.setParameter("providerId",  providerId);
		return (List<Source>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Provider> getAllProviders(Long communityId) {
		String hql = "select p from Provider p where p.community.id = :communityId";
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("communityId",  communityId);
		return (List<Provider>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Provider> getAllProviders(List<Community> communities) {
		String hql = "select p from Provider p ";
		if (!communities.isEmpty()){
			hql += "where (";
			for (Community community : communities){
				hql += "p.community.id = " + community.getId() + " or ";
			}
			hql += "p.community.id is NULL";
			hql += ")";
		}
		Query query = entityManager.createQuery(hql);
		return (List<Provider>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ProviderSource> getProviderSourceByProviderId(Long providerId) {
		Query query = entityManager.createQuery("select p from ProviderSource p where p.provider.id = :providerId");
		query.setParameter("providerId",  providerId);
		return (List<ProviderSource>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public ProviderSource getProviderSourceBySourceId(Long sourceId) {
		Query query = entityManager.createQuery("select p from ProviderSource p where p.source.id = :sourceId");
		query.setParameter("sourceId",  sourceId);
		try {
			return (ProviderSource) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ProviderDevice> getProviderDevicesByProviderId(Long providerId, List<Segment> segments) {
		String hql = "select p from ProviderDevice p where p.provider.id = :providerId";
		if (!segments.isEmpty()){
			hql += " and (";
			for (int i = 0; i < segments.size(); i++){
				Segment segment = segments.get(i);
				hql += "p.segment.id= " + segment.getId();
				hql += " or ";
			}
			hql += "p.segment.id is null";
			hql += ") ";
		}
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("providerId",  providerId);
		return (List<ProviderDevice>) query.getResultList();
	}
	
	//************************************************End of Provider***************************************************************************
	//************************************************ Connector ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Connector getConnector(String code) {
		Query query = entityManager.createQuery(
				"select c from Connector c where c.code =:code");
		query.setParameter("code",  code);
		try {
			return (Connector) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
		
	//************************************************End of Connector***************************************************************************
	//************************************************ Alerts ********************************************************************************
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<AlertNews> getAlertNewsByType(Long accountId, Date fromDate) {
			Query query = entityManager.createQuery("select a from AlertNews a where a.user.id = :accountId and a.status.code = 'ACTIVE' and a.created >= :fromDate order by a.created DESC");
			query.setParameter("accountId",  accountId);
			query.setParameter("fromDate",  fromDate);
			return (List<AlertNews>) query.getResultList();
		}
	//************************************************End of Alerts***************************************************************************
	//************************************************ Community News ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public NewsContentType getNewsContentType(String type) {
			Query query = entityManager.createQuery("select n from NewsContentType n where n.type = :type");
			query.setParameter("type",  type);
			try {
				return (NewsContentType) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
	//************************************************End of Community News ***************************************************************************
	//************************************************ Community News ********************************************************************************
				
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<CommunityNews> getCommunityNewsByType(List<User> users, Date fromDate, Integer maximum) {
			String hql = "";
			hql += "select c from CommunityNews c where ";
			if (users != null && users.size() > 0){
				hql += "(";
				for (int i = 0; i < users.size(); i++){
					User user = users.get(i);
					hql += "c.user.id=" + user.getId();
					if (i < (users.size()-1)) hql += " or ";
				}
				hql += ")";
			}
			hql += " and c.status.code = 'ACTIVE' and c.created >= :fromDate order by c.created DESC";
			
			Query query = entityManager.createQuery(hql);
			query.setParameter("fromDate",  fromDate);
			query.setMaxResults(maximum);
			return (List<CommunityNews>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Integer getNumberOfCommunityNewsByType(List<User> users, Date fromDate) {
			String hql = "";
			hql += "select COUNT(c) from CommunityNews c where ";
			if (users != null && users.size() > 0){
				hql += "(";
				for (int i = 0; i < users.size(); i++){
					User user = users.get(i);
					hql += "c.user.id=" + user.getId();
					if (i < (users.size()-1)) hql += " or ";
				}
				hql += ")";
			}
			hql += " and c.status.code = 'ACTIVE' and c.created >= :fromDate order by c.created DESC";
			
			Query query = entityManager.createQuery(hql);
			query.setParameter("fromDate",  fromDate);
			try{
				return (int)(long)(Long)query.getResultList().get(0);
			}catch(Exception e){
				return 0;
			}
			
		}
	//************************************************End of Community News ***************************************************************************
	//************************************************ Membership News ********************************************************************************
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<MembershipNews> getMembershipNews(List<Segment> segments, Long communityId, Date fromDate, Integer maximumResult) {
			
			Date currentDate = new Date();
			String hql = "";
			hql += "select m from MembershipNews m where ";
			if (segments != null && segments.size() > 0){
				hql += "(";
				for (int i = 0; i < segments.size(); i++){
					Segment user = segments.get(i);
					hql += "m.segment.id=" + user.getId();
					if (i < (segments.size()-1)) hql += " or ";
				}
				hql += " or m.segment is NULL";
				hql += ")";
			}
			hql += " and m.status.code = 'ACTIVE' and m.published <= :currentDate and m.published >= :fromDate order by m.published DESC";
					
			Query query = entityManager.createQuery(hql);
			query.setParameter("currentDate",  currentDate);
			query.setParameter("fromDate",  fromDate);
			query.setMaxResults(maximumResult);
			return (List<MembershipNews>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Integer getNumberOfMembershipNews(List<Segment> segments, Long communityId, Date fromDate) {
			
			Date currentDate = new Date();
			String hql = "";
			hql += "select COUNT(m) from MembershipNews m where ";
			if (segments != null && segments.size() > 0){
				hql += "(";
				for (int i = 0; i < segments.size(); i++){
					Segment user = segments.get(i);
					hql += "m.segment.id=" + user.getId();
					if (i < (segments.size()-1)) hql += " or ";
				}
				hql += " or m.segment is NULL";
				hql += ")";
			}
			hql += " and m.status.code = 'ACTIVE' and m.published <= :currentDate and m.published >= :fromDate order by m.published DESC";
					
			Query query = entityManager.createQuery(hql);
			query.setParameter("currentDate",  currentDate);
			query.setParameter("fromDate",  fromDate);
			try{
				return (int)(long)(Long)query.getResultList().get(0);
			}catch(Exception e){
				e.printStackTrace();
				return 0;
			}
			
		}
	//************************************************End of Membership News ***************************************************************************
	
	//************************************************ Connector ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public PointsTransactionType getPointsTransactionType(String code) {
			Query query = entityManager.createQuery(
					"select p from PointsTransactionType p where p.code =:code");
			query.setParameter("code",  code);
			try {
				return (PointsTransactionType) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
			
	//************************************************End of Connector***************************************************************************

	//************************************************ Partner Points ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public PartnerPointsAccount getPartnerPointsAccountByPartnerId(Long partnerId, Long pointsTypeId) {
			Query query = entityManager.createQuery(
					"select p from PartnerPointsAccount p where p.businessPartner.id =:partnerId and p.pointsType.id = :pointsTypeId");
			query.setParameter("partnerId",  partnerId);
			query.setParameter("pointsTypeId",  pointsTypeId);
			try {
				return (PartnerPointsAccount) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<BusinessPartner> getBusinessPartnerByType(Long typeId) {
			Query query = entityManager.createQuery("select b from BusinessPartner b where b.businessPartnerType.id = :typeId and b.name != 'ZFP'");
			query.setParameter("typeId",  typeId);
			return (List<BusinessPartner>) query.getResultList();
		}	
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public BusinessPartnerType getBusinessPartnerTypeByCode(String code) {
			Query query = entityManager.createQuery(
					"select p from BusinessPartnerType p where p.code =:code");
			query.setParameter("code",  code);
			try {
				return (BusinessPartnerType) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}	
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public PartnerActivityType getPartnerActivityTypeByCode(String code) {
			Query query = entityManager.createQuery(
					"select p from PartnerActivityType p where p.code =:code");
			query.setParameter("code",  code);
			try {
				return (PartnerActivityType) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}				
	//************************************************End of Partner Points***************************************************************************
	//************************************************ Utility ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public UtilityType getUtilityType(String type) {
			Query query = entityManager.createQuery(
					"select u from UtilityType u where u.type =:type");
			query.setParameter("type",  type);
			try {
				return (UtilityType) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}	
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public UtilityConnectionRequest getUtilityConnectionRequest(Long accountId, String type) {
			Query query = entityManager.createQuery(
					"select u from UtilityConnectionRequest u where u.user.id =:accountId and u.utilityType.type = :type");
			query.setParameter("accountId",  accountId);
			query.setParameter("type",  type);
			try {
				return (UtilityConnectionRequest) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
	//************************************************End of Utility***************************************************************************
	//************************************************ Group ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Groups> getGroups(Long accountId) {
		Query query = entityManager.createQuery(
				"select g from Groups g where g.id in "
				+ "(select g1.id from Groups g1 where g1.accountId = :accountId)"
				+ " or g.id in "
				+ "(select ag.groups.id from AccountGroup ag where ag.user.id = :accountId and ag.status.code = 'APPROVED' and ag.groups.groupsType.name != 'DEFAULT')"
				+ " order by g.name ASC");
		query.setParameter("accountId",  accountId);
		
		return (List<Groups>) query.getResultList();

	}	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<User> getAccountFromGroups(Long groupId) {
		Query query = entityManager.createQuery(
				"select a from Account a where a.id in "
				+ "(select g1.accountId from Groups g1 where g1.id = :groupId)"
				+ " or a.id in "
				+ "(select ag.user.id from AccountGroup ag where ag.groups.id = :groupId and ag.status.code = 'APPROVED' and ag.groups.groupsType.name != 'DEFAULT')");
		query.setParameter("groupId",  groupId);
		
		return (List<User>) query.getResultList();

	}	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Groups> getGroupsBySegment(Long accountId, Long segmentId) {
		Query query = entityManager.createQuery(
				"select g from Groups g, GroupSegment gs where g.id = gs.groups.id and gs.segment.id = :segmentId and (g.id in "
				+ "(select g1.id from Groups g1 where g1.accountId = :accountId)"
				+ " or g.id in "
				+ "(select ag.groups.id from AccountGroup ag where ag.user.id = :accountId and ag.status.code = 'APPROVED' and ag.groups.groupsType.name != 'DEFAULT'))"
				+ " order by g.name ASC");
		query.setParameter("accountId",  accountId);
		query.setParameter("segmentId",  segmentId);
		return (List<Groups>) query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public GroupsType getGroupsTypeByName( String name) {
		Query query = entityManager.createQuery("select g from GroupsType g where g.name = :name");
		query.setParameter("name",  name);
		try {
			return (GroupsType) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Groups getGroup(Long accountId, Long groupId, String groupName) {
		String hql = "";
		hql += "select g from Groups g where g.name = :name and g.accountId = :accountId ";
		if (groupId != null){
			hql += " and g.id != " + groupId;
		}
		Query query = entityManager.createQuery(hql);
		query.setParameter("name",  groupName);
		query.setParameter("accountId",  accountId);
		try {
			return (Groups) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Groups getGroupByName(Long accountId, Long communityId, String name) {
		Query query = entityManager.createQuery("select g from Groups g where g.name = :name and g.accountId = :accountId and g.communityId = :communityId");
		query.setParameter("name",  name);
		query.setParameter("accountId",  accountId);
		query.setParameter("communityId",  communityId);
		try {
			return (Groups) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Groups getGroupByAccountIdType(Long accountId, String typeName) {
		Query query = entityManager.createQuery("select g from Groups g where g.accountId = :accountId and g.groupsType.name= :typeName");
		query.setParameter("accountId",  accountId);
		query.setParameter("typeName",  typeName);
		try {
			return (Groups) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Groups> getGroupsByAccount(Long accountId) {
		Query query = entityManager.createQuery("select g from Groups g where g.accountId = :accountId");
		query.setParameter("accountId",  accountId);
		try {
			return (List<Groups>) query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Groups getGroup(Long groupId) {
		Query query = entityManager.createQuery("select g from Groups g where g.id = :groupId");
		query.setParameter("groupId",  groupId);
		
		try {
			return (Groups) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}

	}	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public EmailTemplate getEmailTemplateByType(Long typeId, String code) {
		Query query = entityManager.createQuery("select e from EmailTemplate e where e.emailTemplateType.id = :typeId and e.code = :code");
		query.setParameter("typeId",  typeId);
		query.setParameter("code",  code);
		try {
			return (EmailTemplate) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<EmailTemplate> getEmailTemplateByType(Long typeId) {
		Query query = entityManager.createQuery("select e from EmailTemplate e where e.emailTemplateType.id = :typeId");
		query.setParameter("typeId",  typeId);
		return (List<EmailTemplate>) query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<EmailTemplate> getAllEmailTemplate() {
		Query query = entityManager.createQuery("select e from EmailTemplate e");
		return (List<EmailTemplate>) query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<AccountGroup> getAccountGroups(Long inviterId, Long inviteeId) {
		Query query = entityManager.createQuery("select a from AccountGroup a where a.groups.accountId = :inviterId and a.user.id = :inviteeId");
		query.setParameter("inviterId",  inviterId);
		query.setParameter("inviteeId",  inviteeId);
		return (List<AccountGroup>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public AccountGroup getAccountGroup(Long accountId, Long groupId) {
		Query query = entityManager.createQuery("select a from AccountGroup a where a.groups.id = :groupId and a.user.id = :accountId");
		query.setParameter("accountId",  accountId);
		query.setParameter("groupId",  groupId);
		try {
			return (AccountGroup) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<User> getFriends(Long accountId) {
		Query query = entityManager.createQuery("select a.user from AccountGroup a where a.groups.accountId = :accountId and a.groups.groupsType.name = 'DEFAULT'");
		query.setParameter("accountId",  accountId);
		return (List<User>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public AccountGroupCommunicationStatus getAccountGroupCommunicationStatus(Long accountId, Long groupId, Long campaignId) {
		Query query = entityManager.createQuery("select a from AccountGroupCommunicationStatus a where a.groups.id = :groupId and a.user.id = :accountId and a.campaign.id = :campaignId");
		query.setParameter("accountId",  accountId);
		query.setParameter("groupId",  groupId);
		query.setParameter("campaignId",  campaignId);
		try {
			return (AccountGroupCommunicationStatus) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}

	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public void deletePreferences(Long memberId) {
		Query query = entityManager.createQuery("delete from CommunicationPreference c where c.user.id = :memberId");
		query.setParameter("memberId",  memberId);
		query.executeUpdate();
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public void deleteGroup(Long groupId) {
		Query query = entityManager.createQuery("delete from Groups g where g.id = :groupId");
		query.setParameter("groupId",  groupId);
		query.executeUpdate();
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public void deleteAccountGroupByGroupId(Long groupId) {
		Query query = entityManager.createQuery("delete from AccountGroup a where a.groups.id = :groupId");
		query.setParameter("groupId",  groupId);
		query.executeUpdate();
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public void deleteProviderAccount(Long providerAccountId) {
		Query query = entityManager.createQuery("delete from ProviderAccount a where a.id = :providerAccountId");
		query.setParameter("providerAccountId",  providerAccountId);
		query.executeUpdate();
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public void deleteAccountGroup(Long accountGroupId) {
		Query query = entityManager.createQuery("delete from AccountGroup a where a.id = :accountGroupId");
		query.setParameter("accountGroupId",  accountGroupId);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<AccountGroup> getNumberOfMembers(Long groupId) {
		Query query = entityManager.createQuery("select a from AccountGroup a where a.groups.id = :groupId order by a.status.id ASC");
		query.setParameter("groupId",  groupId);
		return (List<AccountGroup>) query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public AccountGroupCommunicationStatus getAccountCampaignStatus(Long accountId, Long groupId, Long campaignId) {
		Query query = entityManager.createQuery("select a from AccountGroupCommunicationStatus a where a.user.id = :accountId and a.groups.id = :groupId and a.campaign.id = :campaignId");
		query.setParameter("accountId",  accountId);
		query.setParameter("groupId",  groupId);
		query.setParameter("campaignId",  campaignId);
		try {
			return (AccountGroupCommunicationStatus) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}

	}
	
	//************************************************End of Group***************************************************************************
			
	//************************************************ Category ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<Category> getCategories(Integer categoryTypeId) {
			Query query = entityManager.createQuery(
					"select c from Category c where c.categoryType.id =:categoryTypeId");
			query.setParameter("categoryTypeId",  categoryTypeId);
			return (List<Category>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<MerchandisingCategory> getMerchandisingCategories() {
			Query query = entityManager.createQuery(
					"select mc from MerchandisingCategory mc");
			
			return (List<MerchandisingCategory>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Category getCategoryByCode(String categoryCode, Integer categoryTypeId) {
			Query query = entityManager.createQuery(
					"select c from Category c where c.code =:categoryCode and c.categoryType.id = :categoryTypeId");
			query.setParameter("categoryCode",  categoryCode);
			query.setParameter("categoryTypeId",  categoryTypeId);
			try {
				return (Category) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}	
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Category getCategoryByType(Long categoryType) {
			Query query = entityManager.createQuery(
					"select c from Category c where c.id =:categoryType");
			query.setParameter("categoryType",  categoryType);
			try {
				return (Category) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}	
		
	//************************************************End of Category***************************************************************************
		
	//************************************************ Survey ********************************************************************************
		
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Integer getSurveySummaryIteration(Long surveyId, Long memberId) {
			Query query = entityManager.createQuery("select MAX(s.iteration) from SurveySummary s where s.user.id = :memberId and s.survey.id = :surveyId");
			query.setParameter("surveyId", surveyId);
			query.setParameter("memberId", memberId);
			try {
				return (Integer) query.getSingleResult();
			} catch (Exception e) {
				return 0;
			}
		}
		
		/**
		 * Get all currently available surveys with segments
		 * 
		 * @author Youngwook Yoo
		 * @since 2014-09-19
		 * @param segments
		 * @param currentDate
		 * @return a list of surveys
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<Survey> getSurveyList(List<Segment> segments, Date currentDate) {
			String hql = "select s from Survey s where s.status.code='ACTIVE' and s.startDate <= :currentDate and s.endDate >= :currentDate";
			
			if (segments != null && segments.size() > 0){
				hql += " and (";
				for (int i = 0; i < segments.size(); i++){
					Segment segment = segments.get(i);
					hql += "s.segment.id= " + segment.getId();
					if (i < (segments.size()-1)) hql += " or ";
					
				}
				hql += " or s.segment.id is null";
				hql += ") "; 
			}
			
			Query query = entityManager.createQuery(hql);
			query.setParameter("currentDate", currentDate);
			return (List<Survey>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Survey getSurveyName(Long surveyId, Date currentDate) {
			
			Query query = entityManager.createQuery("select s from Survey s where s.id =:surveyId and s.status.code='ACTIVE' and s.startDate <= :currentDate and s.endDate >= :currentDate");
			query.setParameter("surveyId", surveyId);
			query.setParameter("currentDate", currentDate);
			try {
				return (Survey) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * Returns a list of member's survey histories based on iteration descending order
		 * 
		 * @author Youngwook Yoo
		 * @since 2014-09-11
		 * @param surveyId
		 * @param memberId
		 * @param limit
		 * @return a list of survey
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SurveySummary> getSurveySummaries(Long surveyId, Long memberId, Integer limit) {
			
			Query query = entityManager.createQuery("select s from SurveySummary s where s.survey.id =:surveyId and s.user.id = :memberId group by iteration order by iteration DESC");
			query.setParameter("surveyId", surveyId);
			query.setParameter("memberId", memberId);
			query.setMaxResults(limit);
			try {
				return (List<SurveySummary>)query.getResultList();
			} catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * Get a list of SurveySummaries for a given community
		 * 
		 * @author Youngwook Yoo
		 * @since 2014-09-15
		 * @param surveyId
		 * @param limit
		 * @return a list of survey summaries
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SurveySummary> getSurveySummariesForCommunity(Long surveyId, Long communityId) {
			
			Query query = entityManager.createQuery("select s from SurveySummary s where s.survey.id =:surveyId and s.user.defaultCommunity = :communityId and "
					+ "s.iteration = (select MAX(s1.iteration) from SurveySummary s1 where s1.survey.id = s.survey.id and s1.user.id = s.user.id) "
					+ "group by s.user.id order by SUM(s.result) ASC");
			query.setParameter("surveyId", surveyId);
			query.setParameter("communityId", communityId);
			try {
				return (List<SurveySummary>)query.getResultList();
			} catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * Get a list of survey summaries for member who took the survey
		 * 
		 * @author Youngwook Yoo
		 * @since 2014-09-19
		 * @param surveyId
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SurveySummary> getSurveySummaries(Long surveyId) {
			
			Query query = entityManager.createQuery("select s from SurveySummary s where s.survey.id =:surveyId and "
					+ "s.iteration = (select MAX(s1.iteration) from SurveySummary s1 where s1.survey.id = s.survey.id and s1.user.id = s.user.id) "
					+ "group by s.user.id order by SUM(s.result) ASC");
			query.setParameter("surveyId", surveyId);
			try {
				return (List<SurveySummary>)query.getResultList();
			} catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * Get member's result of survey summary
		 * 
		 * @author Youngwook Yoo
		 * @since 2014-09-11
		 * @param surveyId
		 * @param communityId
		 * @return latest survey result
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Double getMemberSurveyResult(Long surveyId, Long memberId) {
			
			Query query = entityManager.createQuery("select sum(s.result) from SurveySummary s where s.survey.id =:surveyId and s.user.id = :memberId and "
					+ "s.iteration = (select MAX(iteration) from SurveySummary where survey.id = s.survey.id and user.id = s.user.id)");
			query.setParameter("surveyId", surveyId);
			query.setParameter("memberId", memberId);
			try {
				return (Double) query.getResultList().get(0);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		/**
		 * Get a sum of survey summary results for community members
		 * 
		 * @author Youngwook Yoo
		 * @since 2014-09-12
		 * @param surveyId
		 * @param communityId
		 * @return
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Double getCommunitySurveyResultSum(Long surveyId, Long communityId) {
			Query query = entityManager.createQuery("select SUM(s.result) from SurveySummary s where s.survey.id =:surveyId and s.user.defaultCommunity = :communityId and "
					+ "s.iteration = (select MAX(s1.iteration) from SurveySummary s1 where s1.survey.id = s.survey.id and s1.user.id = s.user.id) "
					);
			query.setParameter("surveyId", surveyId);
			query.setParameter("communityId", communityId);
			query.setMaxResults(1);
			try {
				return (Double) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		/**
		 * Get minimum value of survey summary for a given community
		 * 
		 * @author Youngwook Yoo
		 * @since 2014-09-11
		 * @param surveyId
		 * @param communityId
		 * @return a minimum survey result
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Double getMinimumSurveyResult(Long surveyId, Long communityId) {
			Query query = entityManager.createQuery("select SUM(s.result) from SurveySummary s where s.survey.id =:surveyId and s.user.defaultCommunity = :communityId and "
					+ "s.iteration = (select MAX(s1.iteration) from SurveySummary s1 where s1.survey.id = s.survey.id and s1.user.id = s.user.id) "
					+ "group by s.user.id order by SUM(s.result) ASC");
			query.setParameter("surveyId", surveyId);
			query.setParameter("communityId", communityId);
			query.setMaxResults(1);
			try {
				return (Double) query.getResultList().get(0);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		/**
		 * Get minimum value of survey summary
		 * 
		 * @author Youngwook Yoo
		 * @since 2014-09-19
		 * @param surveyId
		 * @return a minimum survey result
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Double getMinimumSurveyResult(Long surveyId) {
			Query query = entityManager.createQuery("select SUM(s.result) from SurveySummary s where s.survey.id =:surveyId and "
					+ "s.iteration = (select MAX(s1.iteration) from SurveySummary s1 where s1.survey.id = s.survey.id and s1.user.id = s.user.id) "
					+ "group by s.user.id order by SUM(s.result) ASC");
			query.setParameter("surveyId", surveyId);
			query.setMaxResults(1);
			try {
				return (Double) query.getResultList().get(0);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		/**
		 * Get maximum value of survey summary for a given community
		 * 
		 * @authory Youngwook Yoo
		 * @since 2014-09-11
		 * @param surveyId
		 * @param communityId
		 * @return a maximum survey result
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Double getMaximumSurveyResult(Long surveyId, Long communityId) {
			
			Query query = entityManager.createQuery("select SUM(s.result) from SurveySummary s where s.survey.id =:surveyId and s.user.defaultCommunity = :communityId and "
					+ "s.iteration = (select MAX(s1.iteration) from SurveySummary s1 where s1.survey.id = s.survey.id and s1.user.id = s.user.id) "
					+ "group by s.user.id order by SUM(s.result) DESC");
			query.setParameter("surveyId", surveyId);
			query.setParameter("communityId", communityId);
			query.setMaxResults(1);
			try {
				return (Double) query.getResultList().get(0);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		/**
		 * Get maximum value of survey summary
		 * 
		 * @authory Youngwook Yoo
		 * @since 2014-09-19
		 * @param surveyId
		 * @return a maximum survey result
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Double getMaximumSurveyResult(Long surveyId) {
			
			Query query = entityManager.createQuery("select SUM(s.result) from SurveySummary s where s.survey.id =:surveyId and "
					+ "s.iteration = (select MAX(s1.iteration) from SurveySummary s1 where s1.survey.id = s.survey.id and s1.user.id = s.user.id) "
					+ "group by s.user.id order by SUM(s.result) DESC");
			query.setParameter("surveyId", surveyId);
			query.setMaxResults(1);
			try {
				return (Double) query.getResultList().get(0);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		/**
		 * Returns a list of survey history based on iteration descending order
		 * 
		 * @author Youngwook Yoo
		 * @since 2014-09-11
		 * @param surveyId
		 * @param memberId
		 * @param limit
		 * @return a list of survey
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SurveySummary> getLatestSurveySummary(Long surveyId, Long memberId) {
			
			Query query = entityManager.createQuery("select s from SurveySummary s where s.survey.id =:surveyId and s.user.id = :memberId and "
					+ "s.iteration = (select MAX(iteration) from SurveySummary where survey.id = s.survey.id and user.id = s.user.id )");
			query.setParameter("surveyId", surveyId);
			query.setParameter("memberId", memberId);
			try {
				return (List<SurveySummary>)query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		/**
		 * Get total summary results for a given survey
		 * 
		 * @author Youngwook Yoo
		 * @since 2014-09-11
		 * @param surveyId
		 * @param memberId
		 * @param iteration
		 * @return the summary results
		 */
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Double getSurveySummaryResult(Long surveyId, Long memberId, Integer iteration) {
			
			Query query = entityManager.createQuery("select SUM(s.result) from SurveySummary s where s.survey.id =:surveyId and s.user.id = :memberId and s.iteration = :iteration");
			query.setParameter("surveyId", surveyId);
			query.setParameter("memberId", memberId);
			query.setParameter("iteration", iteration);
			try {
				return (Double) query.getSingleResult();
			} catch (Exception e) {
				return 0.0;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Integer getSurveyTips(Double result) {
			
			Query query = entityManager.createQuery("select s.relevance from SurveyTips s where s.rangeFrom <= :result and s.rangeTo >= :result");
			query.setParameter("result", result);
			query.setMaxResults(1);
			try {
				return (Integer)query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<Tips> getSurveyTips(Integer relevance, Integer limit) {
			
			Query query = entityManager.createQuery("select t from Tips t where t.relevance = :relevance order by rand()");
			query.setParameter("relevance", relevance);
			query.setMaxResults(limit);
			try {
				return (List<Tips>)query.getResultList();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<Tips> getAllTipsByCategories() {
			
			Query query = entityManager.createQuery("select t from Tips t");
			
			try {
				return (List<Tips>)query.getResultList();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SurveySummary getSurveySummary(Long surveyId, Long memberId) {
			
			Query query = entityManager.createQuery("select s from SurveySummary s where s.survey.id =:surveyId and s.user.id = :memberId");
			query.setParameter("surveyId", surveyId);
			query.setParameter("memberId", memberId);
			try {
				return (SurveySummary) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<Survey> getCompletedSurvey(Long memberId) {
			
			Query query = entityManager.createQuery("select distinct(s.survey) from SurveyAnswers s where s.user.id =:memberId ");
			query.setParameter("memberId", memberId);
			try {
				return (List<Survey>) query.getResultList();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SurveyAnswers getSurveyAnswers(Long surveyId, Long memberId) {
			
			Query query = entityManager.createQuery("select s from SurveyAnswers s where s.user.id =:memberId and s.survey.id= :surveyId");
			query.setParameter("surveyId", surveyId);
			query.setParameter("memberId", memberId);
			try {
				return (SurveyAnswers) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SurveyCategory> getSurveyCategory(Long surveyId) {
			Query query = entityManager.createQuery("select s from SurveyCategory s where s.survey.id =:surveyId");
			query.setParameter("surveyId", surveyId);
			
			return (List<SurveyCategory>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SurveyQuestions> getSurveyQuestions(Long surveyCategoryId) {
			Query query = entityManager.createQuery("select s from SurveyQuestions s where s.surveyCategory.id =:surveyCategoryId order by s.questionCode ASC");
			query.setParameter("surveyCategoryId", surveyCategoryId);
			
			return (List<SurveyQuestions>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SurveyAnswerChoices> getSurveyAnswerChoices(Long questionId) {
			Query query = entityManager.createQuery("select s from SurveyAnswerChoices s where s.surveyQuestion.id =:questionId order by s.choiceCode ASC");
			query.setParameter("questionId", questionId);
			
			return (List<SurveyAnswerChoices>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SurveyAnswerRanges getSurveyRanges(Long questionId) {
			Query query = entityManager.createQuery("select s from SurveyAnswerRanges s where s.surveyQuestion.id = :questionId");
			query.setParameter("questionId", questionId);
			try {
				return (SurveyAnswerRanges) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SurveyAnswerChoices getSurveyChoices(Long surveyId, Long questionId, Integer choiceCode) {
			Query query = entityManager.createQuery("select s from SurveyAnswerChoices s where s.survey.id =:surveyId and s.surveyQuestion.id = :questionId and s.choiceCode = :choiceCode");
			query.setParameter("surveyId", surveyId);
			query.setParameter("questionId", questionId);
			query.setParameter("choiceCode", choiceCode);
			try {
				return (SurveyAnswerChoices) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SurveyAnswerValueType getSurveyAnswerValueType(String type) {
			Query query = entityManager.createQuery("select s from SurveyAnswerValueType s where s.type =:type");
			query.setParameter("type", type);
			try {
				return (SurveyAnswerValueType) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SurveyQuestions getSurveyQuestion(Long surveyQuestionId) {
			Query query = entityManager.createQuery("select s from SurveyQuestions s where s.id =:surveyQuestionId");
			query.setParameter("surveyQuestionId", surveyQuestionId);
			try {
				return (SurveyQuestions) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SurveyFactor getSurveyFactor(String factorKey, Long country) {
			Query query = entityManager.createQuery("select s from SurveyFactor s where s.key =:factorKey and s.countryList.id = :country");
			query.setParameter("factorKey", factorKey);
			query.setParameter("country", country);
			try {
				return (SurveyFactor) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SurveyAnswerChoices> getSurveyChoices(Long surveyId, Long questionId) {
			Query query = entityManager.createQuery("select s from SurveyAnswerChoices s where s.survey.id = :surveyId and s.surveyQuestion.id = :questionId");
			query.setParameter("surveyId", surveyId);
			query.setParameter("questionId", questionId);
			return (List<SurveyAnswerChoices>) query.getResultList();
		}
		
	//************************************************End of Survey***************************************************************************
		
	//************************************************ Product ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Product getProduct(String UPC, Long productTypeId) {
		Query query = entityManager.createQuery("select p from Product p where p.UPC =:UPC and p.productType.id = :productTypeId and p.status.code = 'ACTIVE'");
		query.setParameter("UPC", UPC);
		query.setParameter("productTypeId", productTypeId);
		try {
			return (Product) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Product getProductByUPC(String UPC) {
		Query query = entityManager.createQuery("select p from Product p where p.UPC =:UPC and p.status.code = 'ACTIVE'");
		query.setParameter("UPC", UPC);
		try {
			return (Product) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Product getProduct(String sku) {
		Query query = entityManager.createQuery("select p from Product p where p.sku =:sku and statusId = 1");
		query.setParameter("sku", sku);
		try {
			return (Product) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Product getProductBySKU(String sku) {
		Query query = entityManager.createQuery("select p.product from ProductSKU p where p.SKUId =:sku");
		query.setParameter("sku", sku);
		try {
			return (Product) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Product getProduct(Long productId) {
		Query query = entityManager.createQuery("select p from Product p where p.id = :productId");
		query.setParameter("productId", productId);
		try {
			return (Product) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Product> getAllProductByMerchandisingCategoryAndCommunity(Integer categoryId, String productType, List<Community> communities, String sort) {
		String hql = "select p.product from ProductCategory p where p.merchandisingCategory.id =:categoryId and p.product.status.code = 'ACTIVE' and p.product.productType.type = :productType";
		
		if (!communities.isEmpty()){
			hql += " and p.product.id in (select distinct(pc.product.id) from ProductCommunity pc where (";
			for (int i = 0; i < communities.size(); i++){
				Community community = communities.get(i);
				hql += "pc.community.id= " + community.getId();
				if (i < (communities.size()-1)) hql += " or ";
				
			}
			hql += "))"; 
		}
		
		if (sort != null) hql += " order by p.product." + sort;
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("categoryId", categoryId);
		query.setParameter("productType", productType);
		
		return (List<Product>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Product> getTopNumberProductByCommunity(String productType, List<Community> communities, Integer limit) {
		String hql = "select p from Product p where p.status.code = 'ACTIVE' and p.productType.type = :productType";
		
		if (!communities.isEmpty()){
			hql += " and p.id in (select distinct(pc.product.id) from ProductCommunity pc where (";
			for (int i = 0; i < communities.size(); i++){
				Community community = communities.get(i);
				hql += "pc.community.id= " + community.getId();
				if (i < (communities.size()-1)) hql += " or ";
				
			}
			hql += "))"; 
		}
		
		hql += " order by rand()";
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("productType", productType);
		query.setMaxResults(limit);
		return (List<Product>) query.getResultList();
		
	}
	
	/**
	 * Get community products that have offers associated. This product should also exists in product categories. 
	 * @param offerBehaviorId
	 * @param productType
	 * @param communities
	 * @param sort
	 * @return offered products
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Product> getOfferedProducts(Long offerBehaviorId, String productType, List<Community> communities, String sort) {
		Date currentDate = new Date();
		
		String hql = "select distinct(p) from Product p, OfferBehaviorAttribute ob, ProductCategory p2 where p2.product.UPC = p.UPC and p.UPC = ob.behaviorAttribute and ob.offerBehavior.id = :offerBehaviorId and ob.offer.status.code = 'ACTIVE' and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate and p.status.code = 'ACTIVE' and p.productType.type = :productType";
		
		if (!communities.isEmpty()){
			hql += " and p.id in (select distinct(pc.product.id) from ProductCommunity pc where (";
			for (int i = 0; i < communities.size(); i++){
				Community community = communities.get(i);
				hql += "pc.community.id= " + community.getId();
				if (i < (communities.size()-1)) hql += " or ";
				
			}
			hql += ")) "; 
		}
		//hql += " order by p.name ASC";
		if (sort != null) hql += " order by p." + sort;
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("currentDate", currentDate);
		query.setParameter("offerBehaviorId", offerBehaviorId);
		query.setParameter("productType", productType);
		return (List<Product>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Product> getProductByMerchandisingCategoryAndCommunity(Integer categoryId, String productType, List<Community> communities, Integer page, Integer limit, String sort) {
		String hql = "select p.product from ProductCategory p where p.merchandisingCategory.id =:categoryId and p.product.status.code = 'ACTIVE' and p.product.productType.type = :productType";
		
		if (!communities.isEmpty()){
			hql += " and p.product.id in (select distinct(pc.product.id) from ProductCommunity pc where (";
			for (int i = 0; i < communities.size(); i++){
				Community community = communities.get(i);
				hql += "pc.community.id= " + community.getId();
				if (i < (communities.size()-1)) hql += " or ";
				
			}
			hql += ")) "; 
		}
		
		if (sort != null) hql += " order by p.product." + sort;
	
		Query query = entityManager.createQuery(hql);
		query.setParameter("categoryId", categoryId);
		query.setParameter("productType", productType);
		query.setMaxResults(limit); // equivalent to LIMIt
		query.setFirstResult(page); // equivalent to OFFSET
		
		return (List<Product>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Product> getProductByCategoryAndCommunity(Long categoryId, String productType, List<Community> communities) {
		String hql = "select p.product from ProductCategory p where p.category.id =:categoryId and p.product.status.code = 'ACTIVE' and p.product.productType.type = :productType";
		
		if (!communities.isEmpty()){
			hql += " and p.product.id in (select distinct(pc.product.id) from ProductCommunity pc where (";
			for (int i = 0; i < communities.size(); i++){
				Community community = communities.get(i);
				hql += "pc.community.id= " + community.getId();
				if (i < (communities.size()-1)) hql += " or ";
				
			}
			hql += ")) "; 
		}
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("categoryId", categoryId);
		query.setParameter("productType", productType);
		return (List<Product>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ProductBanner> getProductBanners(String productType, List<Community> communities) {
		String hql = "select pb from Product p, ProductBanner pb where pb.product.id = p.id and p.status.code = 'ACTIVE' and p.productType.type = :productType";
		
		if (!communities.isEmpty()){
			hql += " and p.id in (select distinct(pc.product.id) from ProductCommunity pc where (";
			for (int i = 0; i < communities.size(); i++){
				Community community = communities.get(i);
				hql += "pc.community.id= " + community.getId();
				if (i < (communities.size()-1)) hql += " or ";
				
			}
			hql += ")) "; 
		}
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("productType", productType);
		return (List<ProductBanner>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Product> getProductByTypeWithOffset(Long productType, Integer limit, Integer offset) {
		Query query = entityManager.createQuery("select p from Product p where p.productType.id =:productType and p.status.code = 'ACTIVE' order by p.id");
		query.setParameter("productType", productType);
		query.setFirstResult(offset); // equivalent to OFFSET
		query.setMaxResults(limit); // equivalent to LIMIt
		return (List<Product>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Product> getProductByType(Long productType) {
		Query query = entityManager.createQuery("select p from Product p where p.productType.id =:productType and p.status.code = 'ACTIVE'");
		query.setParameter("productType", productType);
		return (List<Product>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ProductSKU> getProductSKUByProduct(Long productId) {
		Query query = entityManager.createQuery("select p from ProductSKU p where p.product.id =:productId");
		query.setParameter("productId", productId);
		return (List<ProductSKU>) query.getResultList();
	}
	
	//************************************************End of Product***************************************************************************
	//************************************************ Status ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Status getStatus(Long statusId) {
			Query query = entityManager.createQuery("select s from Status s where s.id = :statusId");
			query.setParameter("statusId", statusId);
			try {
				return (Status) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Status getStatusByCode(String code) {
			Query query = entityManager.createQuery("select s from Status s where s.code = :code");
			query.setParameter("code", code);
			try {
				return (Status) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
	//************************************************End of Status***************************************************************************
	//************************************************ Status ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CommunicationPreference getCommunicationPreference(Long memberId) {
		Query query = entityManager.createQuery("select c from CommunicationPreference c where c.user.id = :memberId");
		query.setParameter("memberId", memberId);
		try {
			return (CommunicationPreference) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
				
	//************************************************End of Status***************************************************************************	
	//************************************************ Channel Activity ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public ChannelType getChannelType(String type) {
			Query query = entityManager.createQuery("select c from ChannelType c where c.type = :type");
			query.setParameter("type", type);
			try {
				return (ChannelType) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
					
	//************************************************End of Channel Activity***************************************************************************	
		
	//************************************************ MemberActivity ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public MemberActivityType getMemberActivityType(String code) {
			Query query = entityManager.createQuery("select m from MemberActivityType m where m.code = :code");
			query.setParameter("code", code);
			try {
				return (MemberActivityType) query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<MemberActivity> getMemberActivities() {
			Query query = entityManager.createQuery("select m from MemberActivity m where m.processed = 0");
			return (List<MemberActivity>)query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<MemberActivity> checkDuplicates(MemberActivity ma) {
			Query query = entityManager.createQuery("select m from MemberActivity m where m.membershipId = :membershipId " +
					" and m.user.id = :accountId and m.memberActivityType.id = :activityType and m.referenceCode = :referenceCode " +
					" and m.reference = :reference and m.processed = 1");
			query.setParameter("membershipId", ma.getMembershipId());
			query.setParameter("accountId", ma.getUser().getId());
			query.setParameter("activityType", ma.getMemberActivityType().getId());
			query.setParameter("referenceCode", ma.getReferenceCode());
			query.setParameter("reference", ma.getReference());
			return (List<MemberActivity>)query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<MemberActivity> getMemberActivitiesByUser(Long accountId) {
			Query query = entityManager.createQuery("select m from MemberActivity m where m.processed = 0 and m.user.id = :accountId");
			query.setParameter("accountId", accountId);
			return (List<MemberActivity>)query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<MemberActivity> getMemberActivitiesByUserAndReference(Long accountId, Long reference, String referenceCode, String activityType) {
			Query query = entityManager.createQuery("select m from MemberActivity m where m.processed = 0 and m.user.id = :accountId and m.reference = :reference and m.referenceCode = :referenceCode and m.memberActivityType.code = :activityType order by m.date DESC");
			query.setParameter("accountId", accountId);
			query.setParameter("reference", reference);
			query.setParameter("referenceCode", referenceCode);
			query.setParameter("activityType", activityType);
			return (List<MemberActivity>)query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public MemberActivity getMemberActivityByUserAndReference(Long accountId, Long reference, String referenceCode, String activityType) {
			Query query = entityManager.createQuery("select m from MemberActivity m where m.processed = 0 and m.user.id = :accountId and m.reference = :reference and m.referenceCode = :referenceCode and m.memberActivityType.code = :activityType order by m.date DESC");
			query.setParameter("accountId", accountId);
			query.setParameter("reference", reference);
			query.setParameter("referenceCode", referenceCode);
			query.setParameter("activityType", activityType);
			try{
				return (MemberActivity)query.getResultList().get(0);
			}catch(Exception e){
				//e.printStackTrace();
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public MemberActivity getMemberActivitiesByUserAndReferenceCode(Long accountId, String referenceCode, String activityType) {
			Query query = entityManager.createQuery("select m from MemberActivity m where m.user.id = :accountId and m.referenceCode = :referenceCode and m.memberActivityType.code = :activityType order by m.date DESC");
			query.setParameter("accountId", accountId);
			query.setParameter("referenceCode", referenceCode);
			query.setParameter("activityType", activityType);
			try{
				return (MemberActivity)query.getResultList().get(0);
			}catch(Exception e){
				//e.printStackTrace();
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public MemberActivity getMemberActivitiesByUserAndReferenceAndDate(Long accountId, String referenceCode, String activityType, Date activityDate) {
			Query query = entityManager.createQuery("select m from MemberActivity m where m.user.id = :accountId and m.referenceCode = :referenceCode and m.memberActivityType.code = :activityType and m.date = :activityDate");
			query.setParameter("accountId", accountId);
			query.setParameter("referenceCode", referenceCode);
			query.setParameter("activityType", activityType);
			query.setParameter("activityDate", activityDate);
			try{
				return (MemberActivity)query.getResultList().get(0);
			}catch(Exception e){
				//e.printStackTrace();
				return null;
			}
			
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<MemberActivity> getMemberActivitiesByTypandReferenceCode(Long activityTypeId, String referenceCode, Long reference) {
			Query query = entityManager.createQuery("select m from MemberActivity m where m.memberActivityType.id = :activityTypeId and m.referenceCode = :referenceCode and m.reference = :reference");
			query.setParameter("activityTypeId", activityTypeId);
			query.setParameter("referenceCode", referenceCode);
			query.setParameter("reference", reference);
			
			return (List<MemberActivity>)query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public MemberActivity getMemberActivity(Long memberId, Long activityType, String referenceCode) {
			Query query = entityManager.createQuery("select m from MemberActivity m where m.user.id = :memberId and m.memberActivityType.id = :activityType and m.referenceCode = :referenceCode");
			query.setParameter("memberId", memberId);
			query.setParameter("activityType", activityType);
			query.setParameter("referenceCode", referenceCode);
			try {
				return (MemberActivity) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public MemberActivity getMemberActivityByReferenceAsCampaign(Long userId, Long campaignId, Long offerCode) {
			Query query = entityManager.createQuery("select m from PointsTransaction p, MemberActivity m where p.memberActivity.id = m.id and p.offer.code = :offerCode and m.user.id = :userId and m.reference = :campaignId and m.processed is true");
			query.setParameter("userId", userId);
			query.setParameter("campaignId", campaignId);
			query.setParameter("offerCode", offerCode);
			try {
				return (MemberActivity) query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
	
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public MemberActivity getMemberActivityByReferenceAsCampaignAndDate(Long userId, Long campaignId, Long offerCode, Date startDate, Date endDate) {
			Query query = entityManager.createQuery("select m from PointsTransaction p, MemberActivity m where p.memberActivity.id = m.id and p.offer.code = :offerCode and m.user.id = :userId and m.reference = :campaignId and m.date >= :startDate and m.date <= :endDate and m.processed is true");
			query.setParameter("userId", userId);
			query.setParameter("campaignId", campaignId);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameter("offerCode", offerCode);
			
			try {
				return (MemberActivity) query.getResultList().get(0);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
	//************************************************End of MemberActivity***************************************************************************	
				
	
	//************************************************ Driving ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Community getCommunityByCode(String communityCode) {
		Query query = entityManager.createQuery("select c from Community c where c.code = :communityCode");
		query.setParameter("communityCode",  communityCode);
		try {
			return (Community)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}		
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double getDrivingMonthEfficiencyBySource(Long sourceId, Integer month, Integer year) {
		String queryString = "select avg(d.fuelEfficiency) from DrivingHour d where YEAR(d.startTime) = :year and MONTH(d.startTime) = :month";
		queryString += " and d.source.id= :sourceId";
		queryString += " group by YEAR(d.startTime), MONTH(d.startTime)";
		
		Query query = entityManager.createQuery(queryString);
		query.setParameter("month",   month);
		query.setParameter("sourceId",   sourceId);
		query.setParameter("year",  year);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double getDrivingMonthFuelBySource(Long sourceId, Integer month, Integer year) {
		String queryString = "select sum(d.fuel) from DrivingHour d where YEAR(d.startTime) = :year and MONTH(d.startTime) = :month";
		queryString += " and d.source.id= :sourceId";
		queryString += " group by YEAR(startTime), MONTH(startTime)";
		
		Query query = entityManager.createQuery(queryString);
		query.setParameter("month",   month);
		query.setParameter("sourceId",   sourceId);
		query.setParameter("year",  year);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double getDrivingMonthAttributeBySource(String attribute, Long sourceId, Integer month, Integer year) {
		String queryString = "select sum(d." + attribute + ") from DrivingHour d where YEAR(d.startTime) = :year and MONTH(d.startTime) = :month";
		queryString += " and d.source.id= :sourceId";
		queryString += " group by YEAR(d.startTime), MONTH(d.startTime)";
		
		Query query = entityManager.createQuery(queryString);
		query.setParameter("month",   month);
		query.setParameter("sourceId",   sourceId);
		query.setParameter("year",  year);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public DrivingDay getDrivingDataDay(Long sourceId, Date startTime) {
		Query query = entityManager.createQuery("select d from DrivingDay d where d.source.id = :sourceId and d.startTime = :startTime");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startTime",  startTime);
		try {
			return (DrivingDay) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public DrivingHour getDrivingDataHour(Long sourceId, Date startTime) {
		Query query = entityManager.createQuery("select d from DrivingHour d where d.source.id = :sourceId and d.startTime = :startTime");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startTime",  startTime);
		try {
			return (DrivingHour) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<DrivingHour> getDrivingHourByInterval(Long sourceId, Date startTime, Date endTime) {
		Query query = entityManager.createQuery("select d from DrivingHour d where d.source.id = :sourceId and d.startTime >= :startTime and d.endTime <= :endTime order by d.startTime ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startTime",  startTime);
		query.setParameter("endTime",  endTime);
		return (List<DrivingHour>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Date> getDrivingMonthBySource(Long sourceId, Integer lastYear, Integer month, Integer thisYear) {
		Query query = entityManager.createQuery("select d.startTime from DrivingHour d where ((YEAR(d.startTime) = :lastYear and MONTH(d.startTime) >= :month) or Year(d.startTime)= :thisYear) and d.source.id = :sourceId group by YEAR(d.startTime), MONTH(d.startTime)");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("lastYear",  lastYear);
		query.setParameter("month",   month);
		query.setParameter("thisYear",  thisYear);
		return (List<Date>) query.getResultList();
	}
	
	/*
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Driving> getDrivingBySource(Long sourceId, Date date) {
		Query query = entityManager.createQuery("select d from Driving d where d.source.id = :sourceId and YEAR(startTime) = YEAR(:date) and MONTH(startTime) = MONTH(:date) and DAY(startTime) = DAY(:date) order by startTime DESC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		return (List<Driving>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<String> getDriving(Long sourceId) {
		Query query = entityManager.createQuery("select distinct date_format(d.startTime, '%Y-%m-%d') from Driving d where d.source.id = :sourceId group by d.startTime order by d.startTime DESC");
		query.setParameter("sourceId",  sourceId);
		query.setMaxResults(7);
		return (List<String>)query.getResultList();
	}
	*/
	//************************************************End of Driving***************************************************************************	
	
	//************************************************ PersonalMotion ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<String> getWalking(Long sourceId) {
		Query query = entityManager.createQuery("select distinct date_format(w.startTime, '%Y-%m-%d') from WalkingHour w where w.source.id = :sourceId group by w.startTime order by w.startTime DESC");
		query.setParameter("sourceId",  sourceId);
		query.setMaxResults(7);
		return (List<String>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<WalkingHour> getWalkingBySource(Long sourceId, Date date) {
		Query query = entityManager.createQuery("select w from WalkingHour w where w.source.id = :sourceId and YEAR(startTime) = YEAR(:date) and MONTH(startTime) = MONTH(:date) and DAY(startTime) = DAY(:date) order by startTime ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		return (List<WalkingHour>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<WalkingHour> getWalkingByInterval(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select w from WalkingHour w where w.source.id = :sourceId and w.startTime >= :startDate and w.startTime <= :endDate order by startTime ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (List<WalkingHour>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public WalkingDay getWalkingDay(Long sourceId, Date startDate) {
		Query query = entityManager.createQuery("select w from WalkingDay w where w.source.id = :sourceId and w.startTime = :startDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		try{
			return (WalkingDay) query.getResultList().get(0);
		}catch(Exception e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public WalkingMonth getWalkingMonth(Long sourceId, Date startDate) {
		Query query = entityManager.createQuery("select w from WalkingMonth w where w.source.id = :sourceId and w.startTime = :startDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		try{
			return (WalkingMonth) query.getResultList().get(0);
		}catch(Exception e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<WalkingDay> getWalkingDayByInterval(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select w from WalkingDay w where w.source.id = :sourceId and w.startTime >= :startDate and w.startTime <= :endDate order by startTime ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (List<WalkingDay>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<RunningDataHour> getRunningByInterval(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select distinct w from RunningDataHour w where w.source.id = :sourceId and w.startTime >= :startDate and w.startTime <= :endDate order by startTime ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (List<RunningDataHour>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public RunningDataDay getRunningDay(Long sourceId, Date startDate) {
		Query query = entityManager.createQuery("select w from RunningDataDay w where w.source.id = :sourceId and w.startTime = :startDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		try{
			return (RunningDataDay) query.getResultList().get(0);
		}catch(Exception e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public RunningDataMonth getRunningMonth(Long sourceId, Date startDate) {
		Query query = entityManager.createQuery("select w from RunningDataMonth w where w.source.id = :sourceId and w.startTime = :startDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		try{
			return (RunningDataMonth) query.getResultList().get(0);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Get cycling day data
	 * 
	 * @param sourceId
	 * @param startDate
	 * @return cycling data
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CyclingDataDay getCyclingDay(Long sourceId, Date startDate) {
		Query query = entityManager.createQuery("select w from CyclingDataDay w where w.source.id = :sourceId and w.startTime = :startDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		try{
			return (CyclingDataDay) query.getResultList().get(0);
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Get cycling month data
	 * 
	 * @param sourceId
	 * @param startDate
	 * @return cycling data
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CyclingDataMonth getCyclingMonth(Long sourceId, Date startDate) {
		Query query = entityManager.createQuery("select w from CyclingDataMonth w where w.source.id = :sourceId and w.startTime = :startDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		try{
			return (CyclingDataMonth) query.getResultList().get(0);
		}catch(Exception e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<RunningDataDay> getRunningDayByInterval(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select distinct w from RunningDataDay w where w.source.id = :sourceId and w.startTime >= :startDate and w.startTime <= :endDate order by startTime ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (List<RunningDataDay>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public DrivingDay getDrivingDayByDate(Long sourceId, Integer year, Integer month, Integer day) {
		Query query = entityManager.createQuery("select distinct r from DrivingDay r where r.source.id = :sourceId and YEAR(r.created) = :year and MONTH(r.created) = :month and DAY(r.created) = :day");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("year",  year);
		query.setParameter("month",  month);
		query.setParameter("day",  day);
		try {
			return (DrivingDay)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public DrivingMonth getDrivingMonthByDate(Long sourceId, Integer year, Integer month) {
		Query query = entityManager.createQuery("select distinct r from DrivingMonth r where r.source.id = :sourceId and YEAR(r.created) = :year and MONTH(r.created) = :month");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("year",  year);
		query.setParameter("month",  month);
		try {
			return (DrivingMonth)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public ElectricityDataDay getElectricityDayByDate(Long sourceId, Integer year, Integer month, Integer day) {
		Query query = entityManager.createQuery("select distinct r from ElectricityDataDay r where r.source.id = :sourceId and YEAR(r.created) = :year and MONTH(r.created) = :month and DAY(r.created) = :day");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("year",  year);
		query.setParameter("month",  month);
		query.setParameter("day",  day);
		try {
			return (ElectricityDataDay)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public ElectricityDataMonth getElectricityMonthByDate(Long sourceId, Integer year, Integer month) {
		Query query = entityManager.createQuery("select distinct r from ElectricityDataMonth r where r.source.id = :sourceId and YEAR(r.created) = :year and MONTH(r.created) = :month");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("year",  year);
		query.setParameter("month",  month);
		try {
			return (ElectricityDataMonth)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public WalkingDay getWalkingDayByDate(Long sourceId, Integer year, Integer month, Integer day) {
		Query query = entityManager.createQuery("select distinct r from WalkingDay r where r.source.id = :sourceId and YEAR(r.startTime) = :year and MONTH(r.startTime) = :month and DAY(r.startTime) = :day");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("year",  year);
		query.setParameter("month",  month);
		query.setParameter("day",  day);
		try {
			return (WalkingDay)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public RunningDataDay getRunningDayByDate(Long sourceId, Integer year, Integer month, Integer day) {
		Query query = entityManager.createQuery("select distinct r from RunningDataDay r where r.source.id = :sourceId and YEAR(r.startTime) = :year and MONTH(r.startTime) = :month and DAY(r.startTime) = :day");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("year",  year);
		query.setParameter("month",  month);
		query.setParameter("day",  day);
		try {
			return (RunningDataDay)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CyclingDataDay getCyclingDayByDate(Long sourceId, Integer year, Integer month, Integer day) {
		Query query = entityManager.createQuery("select distinct r from CyclingDataDay r where r.source.id = :sourceId and YEAR(r.startTime) = :year and MONTH(r.startTime) = :month and DAY(r.startTime) = :day");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("year",  year);
		query.setParameter("month",  month);
		query.setParameter("day",  day);
		try {
			return (CyclingDataDay)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public WalkingMonth getWalkingMonthByDate(Long sourceId, Integer year, Integer month) {
		Query query = entityManager.createQuery("select distinct r from WalkingMonth r where r.source.id = :sourceId and YEAR(r.startTime) = :year and MONTH(r.startTime) = :month");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("year",  year);
		query.setParameter("month",  month);
		try {
			return (WalkingMonth)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public RunningDataMonth getRunningMonthByDate(Long sourceId, Integer year, Integer month) {
		Query query = entityManager.createQuery("select distinct r from RunningDataMonth r where r.source.id = :sourceId and YEAR(r.startTime) = :year and MONTH(r.startTime) = :month");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("year",  year);
		query.setParameter("month",  month);
		try {
			return (RunningDataMonth)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CyclingDataMonth getCyclingMonthByDate(Long sourceId, Integer year, Integer month) {
		Query query = entityManager.createQuery("select distinct r from CyclingDataMonth r where r.source.id = :sourceId and YEAR(r.startTime) = :year and MONTH(r.startTime) = :month");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("year",  year);
		query.setParameter("month",  month);
		try {
			return (CyclingDataMonth)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CyclingDataHour> getCyclingByInterval(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select w from CyclingDataHour w where w.source.id = :sourceId and w.startTime >= :startDate and w.startTime <= :endDate order by startTime ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (List<CyclingDataHour>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CyclingDataDay> getCyclingDayByInterval(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select w from CyclingDataDay w where w.source.id = :sourceId and w.startTime >= :startDate and w.startTime <= :endDate order by startTime ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (List<CyclingDataDay>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double getWalkingDistanceByInterval(String table, Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select SUM(w.distance) from " + table + "  w where w.source.id = :sourceId and w.startTime >= :startDate and w.startTime <= :endDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double getDrivingFuelEfficiencyByInterval(String table, Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select AVG(w.fuelEfficiency) from " + table + "  w where w.source.id = :sourceId and w.startTime >= :startDate and w.startTime <= :endDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double getDrivingDistanceByInterval(String table, Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select SUM(w.distance) from " + table + "  w where w.source.id = :sourceId and w.startTime >= :startDate and w.startTime <= :endDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<WalkingHour> checkWalkingBySource(Long sourceId, Date date) {
		Query query = entityManager.createQuery("select w from WalkingHour w where w.source.id = :sourceId and w.startTime < :date order by startTime ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		return (List<WalkingHour>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Long getPersonalMotionMonthCaloriesBySource(String database, List<Long> sourceIds, Integer month, Integer year) {
		String queryString = "select sum(w.calories) from " + database + " w where YEAR(w.startTime) = :year and MONTH(w.startTime) = :month";
		queryString += " and (";
		for (int i = 0; i< sourceIds.size(); i++ ){
			queryString += "w.source.id=" + sourceIds.get(i);
			
			if (i != sourceIds.size()-1){
				queryString += " or ";
			}
		}
		queryString += " ) group by YEAR(startTime), MONTH(startTime)";
		
		Query query = entityManager.createQuery(queryString);
		query.setParameter("month",   month);
		query.setParameter("year",  year);
		try {
			return (Long)query.getResultList().get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Long getPersonalMotionMonthStepsBySource(String database, List<Long> sourceIds, Integer month, Integer year) {
		String queryString = "select sum(w.steps) from "+ database + " w where YEAR(w.startTime) = :year and MONTH(w.startTime) = :month";
		queryString += " and (";
		for (int i = 0; i< sourceIds.size(); i++ ){
			queryString += "w.source.id=" + sourceIds.get(i);
			
			if (i != sourceIds.size()-1){
				queryString += " or ";
			}
		}
		queryString += " ) group by YEAR(startTime), MONTH(startTime)";
		
		Query query = entityManager.createQuery(queryString);
		query.setParameter("month",   month);
		query.setParameter("year",  year);
		try {
			return (Long)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double getPersonalMotionMonthDistanceBySource(String database, List<Long> sourceIds, Integer month, Integer year) {
		String queryString = "select sum(w.distance) from " + database + " w where YEAR(w.startTime) = :year and MONTH(w.startTime) = :month";
		queryString += " and (";
		for (int i = 0; i< sourceIds.size(); i++ ){
			queryString += "w.source.id=" + sourceIds.get(i);
			
			if (i != sourceIds.size()-1){
				queryString += " or ";
			}
		}
		queryString += " ) group by YEAR(startTime), MONTH(startTime)";
		
		Query query = entityManager.createQuery(queryString);
		query.setParameter("month",   month);
		query.setParameter("year",  year);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double getPersonalMotionMonthDurationBySource(String database, List<Long> sourceIds, Integer month, Integer year) {
		String queryString = "select sum(w.duration) from " + database + " w where YEAR(w.startTime) = :year and MONTH(w.startTime) = :month";
		queryString += " and (";
		for (int i = 0; i< sourceIds.size(); i++ ){
			queryString += "w.source.id=" + sourceIds.get(i);
			
			if (i != sourceIds.size()-1){
				queryString += " or ";
			}
		}
		queryString += " ) group by YEAR(startTime), MONTH(startTime)";
		
		Query query = entityManager.createQuery(queryString);
		query.setParameter("month",   month);
		query.setParameter("year",  year);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Date> getPersonalMotionMonthBySource(String database, List<Long> sourceIds, Integer lastYear, Integer month, Integer thisYear) {
		String queryString = "select w.startTime from "+ database + " w where ((YEAR(w.startTime) = :lastYear and MONTH(w.startTime) >= :month) or Year(w.startTime)= :thisYear) ";
		queryString += " and (";
		for (int i = 0; i< sourceIds.size(); i++ ){
			queryString += "w.source.id=" + sourceIds.get(i);
			
			if (i != sourceIds.size()-1){
				queryString += " or ";
			}
		}
		queryString += " ) group by YEAR(startTime), MONTH(startTime)";
		
		Query query = entityManager.createQuery(queryString);
		query.setParameter("lastYear",  lastYear);
		query.setParameter("month",   month);
		query.setParameter("thisYear",  thisYear);
		return (List<Date>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public PersonalMotion getTodayPersonalMotion(Long sourceId, Date startDate) {
		Query query = entityManager.createQuery("select p from PersonalMotion p where p.source.id = :sourceId and p.startTime >= :startDate order by p.startTime DESC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		try {
			return (PersonalMotion)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<PersonalMotionInterval> getPersonalMotionHalfHourData(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select p from PersonalMotionInterval p where p.source.id = :sourceId and p.startTime between :startDate and :endDate order by p.startTime ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (List<PersonalMotionInterval>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<WalkingBoundary> getWalkingBoundaries(String periodType) {
		Query query = entityManager.createQuery("select p from WalkingBoundary p where p.periodType.type = :periodType");
		query.setParameter("periodType",  periodType);
		return (List<WalkingBoundary>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<WalkingBoundary> getWalkingBoundariesByActivityTrackerType(String periodType, String activityTrackerType) {
		Query query = entityManager.createQuery("select p from WalkingBoundary p where p.periodType.type = :periodType and p.activityTrackerType.type = :activityTrackerType");
		query.setParameter("periodType",  periodType);
		query.setParameter("activityTrackerType",  activityTrackerType);
		return (List<WalkingBoundary>)query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public WalkingDayAverage getWalkingDayAverage(Long sourceId) {
		Query query = entityManager.createQuery("select w from WalkingDayAverage w where w.source.id = :sourceId");
		query.setParameter("sourceId",  sourceId);
		try{
			return (WalkingDayAverage)query.getResultList().get(0);
		}catch(Exception e){
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public RunningDataDayAverage getRunningDayAverage(Long sourceId) {
		Query query = entityManager.createQuery("select w from RunningDataDayAverage w where w.source.id = :sourceId");
		query.setParameter("sourceId",  sourceId);
		try{
			return (RunningDataDayAverage)query.getResultList().get(0);
		}catch(Exception e){
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CyclingDataDayAverage getCyclingDayAverage(Long sourceId) {
		Query query = entityManager.createQuery("select w from CyclingDataDayAverage w where w.source.id = :sourceId");
		query.setParameter("sourceId",  sourceId);
		try{
			return (CyclingDataDayAverage)query.getResultList().get(0);
		}catch(Exception e){
			return null;
		}
		
	}
	
	//************************************************End of PersonalMotion***************************************************************************	
	//************************************************ Tax ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public TaxLocation getTaxLocation(String locationCode) {
			Query query = entityManager.createQuery("select t from TaxLocation t where t.locationCode = :locationCode");
			query.setParameter("locationCode",  locationCode);
			try {
				return (TaxLocation)query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public TaxValue getTaxValue(Long taxLocationId) {
			Query query = entityManager.createQuery("select t from TaxValue t where t.taxLocation.id = :taxLocationId");
			query.setParameter("taxLocationId",  taxLocationId);
			try {
				return (TaxValue)query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		//************************************************End of Tax***************************************************************************				
		
	//************************************************ Source ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<SourceHierarchy> getChildrenSourcePaths(Long parentSourceId) {
		Query query = entityManager.createQuery("select s from SourcePath s where s.parent_id in (select s1.id from SourcePath s1 where s1.source.id = :parentSourceId)");
		query.setParameter("parentSourceId",  parentSourceId);
		return (List<SourceHierarchy>) query.getResultList();
	}
	
	//************************************************End of Source***************************************************************************	
	
	//************************************************ Unit ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public MobileDevice getMobileDeviceByIdentifier(String identifier) {
		Query query = entityManager.createQuery("select m from MobileDevice m where m.identifier = :identifier");
		query.setParameter("identifier",  identifier);
		try {
			return (MobileDevice)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	//************************************************End of Unit***************************************************************************
		
	//************************************************ Rule ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CampaignRule> getCampaignRules(Long campaignId) {
		Query query = entityManager.createQuery("select cr from CampaignRule cr where cr.campaign.id = :campaignId");
		query.setParameter("campaignId",  campaignId);
		return (List<CampaignRule>) query.getResultList();
	}
	
	//************************************************End of Rule***************************************************************************
		
	//************************************************ Unit ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Unit getUnitByCode(String code) {
		Query query = entityManager.createQuery("select u from Unit u where u.code = :code");
		query.setParameter("code",  code);
		try {
			return (Unit)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	//************************************************End of Unit***************************************************************************	
			
		
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CommunityProduct> getCommunityProducts(String communityCode) {
		Query query = entityManager.createQuery("select c from CommunityProduct c where c.communityCode = :communityCode order by c.id ASC");
		query.setParameter("communityCode",  communityCode);
		return (List<CommunityProduct>) query.getResultList();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public FootprintType getFootprintTypeById(Long footprintTypeId) {
		Query query = entityManager.createQuery("select f from FootprintType f where f.id = :footprintTypeId");
		
		query.setParameter("footprintTypeId", footprintTypeId);
		try {
			return (FootprintType) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public PersonalFootprintSurvey findSurveyByCommunity(Long sourceId, Long communityId) {
		Query query = entityManager.createQuery("select p from PersonalFootprintSurvey p where p.community.id = :communityId and p.source.id = :sourceId ");
		query.setParameter("communityId",  communityId);
		query.setParameter("sourceId",  sourceId);
		try {
			return (PersonalFootprintSurvey) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Category findCategoryByType(Integer type) {
		Query query = entityManager.createQuery("select c from Category c where c.type = :type");
		query.setParameter("type",  type);
		try {
			return (Category) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CarbonFootprint> findCarbonFootprint(Long sourceId, Long communityId, Long footprintTypeId) {
		Query query = entityManager.createQuery("select c from CarbonFootprint c where c.source.id = :sourceId and "+
					"c.community.id = :communityId and c.footprintType.id = :footprintTypeId");
		
		query.setParameter("sourceId", sourceId);
		query.setParameter("communityId", communityId);
		query.setParameter("footprintTypeId", footprintTypeId);
		return (List<CarbonFootprint>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<SourceType> getSourceTypesFromCarbonFootprint(Long sourceId, Long communityId, Long footprintTypeId) {
		Query query = entityManager.createQuery("select c.sourceType from CarbonFootprint c where c.source.id = :sourceId and "+
					"c.community.id = :communityId and c.footprintType.id = :footprintTypeId group by c.sourceType");
		
		query.setParameter("sourceId", sourceId);
		query.setParameter("communityId", communityId);
		query.setParameter("footprintTypeId", footprintTypeId);
		return (List<SourceType>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CarbonFootprint getCarbonFootprint(Long sourceId, Long sourceTypeId, Long communityId, Long footprintTypeId) {
		Query query = entityManager.createQuery("select c from CarbonFootprint c where c.source.id = :sourceId and c.sourceType.id = :sourceTypeId and "+
					"c.community.id = :communityId and c.footprintType.id = :footprintTypeId");
		
		query.setParameter("sourceId", sourceId);
		query.setParameter("sourceTypeId", sourceTypeId);
		query.setParameter("communityId", communityId);
		query.setParameter("footprintTypeId", footprintTypeId);
		try {
			return (CarbonFootprint) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Double> findFootprintByCommunity(Long communityId, Date startDate, Date endDate, Long footprintTypeId ) {
		Query query = entityManager.createQuery("select sum(c.value) from CarbonFootprint c where c.community.id = :communityId and c.created between :startDate and :endDate and c.footprintType.id = :footprintTypeId and " +
				"c.created = (select max(cf.created) from CarbonFootprint cf where cf.source.id = c.source.id) " +
				"group by c.source.id order by sum(c.value) ASC");
		query.setParameter("communityId",  communityId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		query.setParameter("footprintTypeId",  footprintTypeId);
		return (List<Double>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findValueByDateAndSource(Long sourceId, Long communityId, Date date) {
		Query query = entityManager.createQuery("select sum(c.value) from CarbonFootprint c where c.community.id = :communityId and c.source.id = :sourceId and c.created = :date "+
				"group by c.created");
		query.setParameter("communityId",  communityId);
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		return (Double) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Date> findDatesBySourceAndCommunity(Long sourceId, Long communityId) {
		Query query = entityManager.createQuery("select c.created from CarbonFootprint c where c.community.id = :communityId and c.source.id = :sourceId "+
				"group by c.created order by c.created DESC");
		query.setParameter("communityId",  communityId);
		query.setParameter("sourceId",  sourceId);
		query.setMaxResults(5);
		return (List<Date>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Source> findSourceByCommunity(Long communityId, Date startDate, Date endDate, Long footprintTypeId) {
		Query query = entityManager.createQuery("select c.source from CarbonFootprint c where c.community.id = :communityId and c.created between :startDate and :endDate and c.footprintType.id = :footprintTypeId and "+
				"c.created = (select max(cf.created) from CarbonFootprint cf where cf.source.id = c.source.id) " +
				"group by c.source.id order by sum(c.value) ASC");
		query.setParameter("communityId",  communityId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		query.setParameter("footprintTypeId",  footprintTypeId);
		return (List<Source>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findValueBySourceAndCommunity(Long sourceId, Long communityId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select sum(c.value) from CarbonFootprint c where c.source.id = :sourceId and c.community.id = :communityId and c.created between :startDate and :endDate and "+
				"c.created = (select max(cf.created) from CarbonFootprint cf where cf.source.id = c.source.id)");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("communityId",  communityId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (Double) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findValueBySourceTypeAndSource(Long sourceTypeId, Long sourceId, Long communityId, Long footprintTypeId) {
		Query query = entityManager.createQuery("select c.value from CarbonFootprint c where c.sourceType.id = :sourceTypeId and c.source.id = :sourceId and c.community.id = :communityId and c.footprintType.id = :footprintTypeId and "+
				"c.created = (select max(cf.created) from CarbonFootprint cf where cf.source.id = c.source.id and cf.sourceType.id = c.sourceType.id)");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("communityId",  communityId);
		query.setParameter("sourceTypeId",  sourceTypeId);
		query.setParameter("footprintTypeId",  footprintTypeId);
		return (Double) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CarbonFootprint> findFirstAndSecondValueSourceTypeAndSource(Long sourceTypeId, Long sourceId, Long communityId, Long footprintTypeId) {
		Query query = entityManager.createQuery("select c from CarbonFootprint c where c.sourceType.id = :sourceTypeId and c.source.id = :sourceId and c.community.id = :communityId and c.footprintType.id = :footprintTypeId order by c.created DESC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("communityId",  communityId);
		query.setParameter("sourceTypeId",  sourceTypeId);
		query.setParameter("footprintTypeId",  footprintTypeId);
		query.setMaxResults(2);
		return (List<CarbonFootprint>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Community> getAllCommunities() {
		Query query = entityManager.createQuery("select c from Community c, CommunityHierarchy ch where ch.community.id = c.id and ch.depth != 0 and c.status.code = 'ACTIVE' and c.communityType.type = 'CONSUMER'");
		
		return (List<Community>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Community getCommunityById(Long communityId) {
		Query query = entityManager.createQuery("select c from Community c where c.id = :communityId");
		
		query.setParameter("communityId", communityId);
		try {
			return (Community) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CommunityHierarchy getCommunityHierarchyByCommunityId(Long communityId) {
		Query query = entityManager.createQuery("select c from CommunityHierarchy c where c.community.id = :communityId");
		
		query.setParameter("communityId", communityId);
		try {
			return (CommunityHierarchy) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CommunityHierarchy> getSubCommunityHierarchyByCommunityId(Long communityId) {
		Query query = entityManager.createQuery("select c from CommunityHierarchy c where c.parent.id = :communityId order by c.community.name ASC");
		query.setParameter("communityId", communityId);
		
		return (List<CommunityHierarchy>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CommunityPortal getCommunityPortal(Long communityId, Long portalTypeId) {
		Query query = entityManager.createQuery("select c from CommunityPortal c where c.community.id = :communityId and c.portalType.id = :portalTypeId");
		
		query.setParameter("portalTypeId", portalTypeId);
		query.setParameter("communityId", communityId);
		try {
			return (CommunityPortal) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CommunityPortalTheme getCommunityPortalTheme(Long communityPortalId) {
		Query query = entityManager.createQuery("select c from CommunityPortalTheme c where c.CommunityPortal.id = :communityPortalId");
		
		query.setParameter("communityPortalId", communityPortalId);
		try {
			return (CommunityPortalTheme) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Long getCommunityFeatureId(Long communityPortalId, String featureName) {
		Query query = entityManager.createQuery("select f.id from CommunityFeature f where f.communityPortal.id = :communityPortalId and f.feature.name = :featureName");
		
		query.setParameter("communityPortalId", communityPortalId);
		query.setParameter("featureName", featureName);
		try {
			return (Long) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
		
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<FeatureTravelType> getFeatureTravelType(Long communityFeatureId) {
		Query query = entityManager.createQuery("select f from FeatureTravelType f where f.communityFeature.id = :communityFeatureId");
		
		query.setParameter("communityFeatureId", communityFeatureId);
		return (List<FeatureTravelType>) query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CommunityFeature> getCommunityFeature(Long communityPortalId) {
		Query query = entityManager.createQuery("select c from CommunityFeature c where c.communityPortal.id = :communityPortalId");
		
		query.setParameter("communityPortalId", communityPortalId);
		return (List<CommunityFeature>) query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Source getSourceById(Long sourceId) {
		Query query = entityManager.createQuery("select s from Source s where s.id = :sourceId");
		
		query.setParameter("sourceId", sourceId);
		try {
			return (Source) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public SourceType getSourceTypeById(Long sourceTypeId) {
		Query query = entityManager.createQuery("select s from SourceType s where s.id = :sourceTypeId");
		
		query.setParameter("sourceTypeId", sourceTypeId);
		try {
			return (SourceType) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public SourceType getSourceTypeByCode(String sourceTypeCode) {
		Query query = entityManager.createQuery("select s from SourceType s where s.code = :sourceTypeCode");
		
		query.setParameter("sourceTypeCode", sourceTypeCode);
		try {
			return (SourceType) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Source findSourceBySourceCodeAndProvider(String sourceCode, String providerCode) {
		Query query = entityManager.createQuery("select p.source from ProviderSource p where p.source.code = :sourceCode and p.provider.code = :providerCode");
		query.setParameter("sourceCode",  sourceCode);
		query.setParameter("providerCode",  providerCode);
		try {
			return (Source)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<DeviceSourceType> getSourceTypeByProviderDevice(Long providerDeviceId) {
		Query query = entityManager.createQuery("select dst from DeviceSourceType dst where dst.providerDevice.id = :providerDeviceId");
		query.setParameter("providerDeviceId", providerDeviceId);
		return (List<DeviceSourceType>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public SourceMode getSourceModeByCode(String sourceModeCode) {
		Query query = entityManager.createQuery("select s from SourceMode s where s.code = :sourceModeCode");
		
		query.setParameter("sourceModeCode", sourceModeCode);
		try {
			return (SourceMode) query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Source> getSourceChildren(Long sourceId) {
		Query query = entityManager.createQuery("select s.source from SourceHierarchy s where s.parent_id = :sourceId");
		query.setParameter("sourceId", sourceId);
		return (List<Source>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<SourceType> getSourceTypes() {
		Query query = entityManager.createQuery("select s from SourceType s");
		return (List<SourceType>) query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Translation> findTranslation(TranslationForm translationFrom) {

		Query query = entityManager.createQuery(
				"select t from Translation t where t.translationKey=:key and ( t.community_id=:community_id or t.community_id is NULL)" +
				" and ( t.locale=:locale or t.locale is NULL ) and t.component.id=:componentId order by t.community_id DESC, t.locale DESC");
		query.setParameter("key", translationFrom.getKey());
		query.setParameter("locale", translationFrom.getLocale());
		query.setParameter("community_id", translationFrom.getCommunityId());
		query.setParameter("componentId", translationFrom.getComponentId());
		System.out.println("************ want one key. ");
		return (List<Translation>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CampaignActionList> getCampaignActionList(Long campaignId) {
		Query query = entityManager.createQuery(
				"select p from CampaignActionList p where p.campaign.id = :campaignId");
		query.setParameter("campaignId", campaignId);
		return (List<CampaignActionList>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CampaignFactList> getCampaignFactList(Long campaignId) {
		Query query = entityManager.createQuery(
				"select p from CampaignFactList p where p.campaign.id = :campaignId");
		query.setParameter("campaignId", campaignId);
		return (List<CampaignFactList>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Campaign getCampaign(Long campaignId) {

		Query query = entityManager.createQuery(
				"select p from Campaign p where p.id = :campaignId");
		query.setParameter("campaignId", campaignId);
		try {
			return (Campaign)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CampaignTarget getCampaignTarget(Long campaignId) {

		Query query = entityManager.createQuery(
				"select p from CampaignTarget p where p.campaign.id = :campaignId");
		query.setParameter("campaignId", campaignId);
		try {
			return (CampaignTarget)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getCampaigns(Long communityId) {

		Query query = entityManager.createQuery(
				"select p from Campaign p where p.communityId = :communityId order by p.endDate DESC");
		query.setParameter("communityId", communityId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getCampaigns(Long communityId, Long categoryId) {

		Query query = entityManager.createQuery(
				"select p from Campaign p where p.communityId = :communityId and p.category.id = :categoryId and p.status.code = 'ACTIVE' order by p.endDate DESC");
		query.setParameter("communityId", communityId);
		query.setParameter("categoryId", categoryId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<String> getCampaignByBehaviorAttributes(String trackerType, Long offerCode, Date currentDate) {
		
		Query query = entityManager.createQuery(
				"select ob.behaviorAttribute from OfferBehaviorAttribute ob where ob.offer.code = :offerCode and ob.offerBehavior.behaviorTrackerType.code = :trackerType and (ob.offer.status.code='ACTIVE' or ob.offer.status.code= 'IN_PROGRESS' or ob.offer.status.code = 'COMPLETED') and ob.offer.startDate <= :currentDate and ob.offer.endDate >= :currentDate "
				+ "order by ob.offer.name ASC ");
		query.setParameter("trackerType",  trackerType);
		query.setParameter("offerCode",  offerCode);
		query.setParameter("currentDate",  currentDate);
		return (List<String>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Long> getPeersInCampaign(Long campaignId) {

		Query query = entityManager.createQuery(
				"select ac.user.id from AccountCampaign ac where ac.campaign.id = :campaignId");
	query.setParameter("campaignId", campaignId);
		return (List<Long>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Long> getGroupsInCampaign(Long groupId) {

		Query query = entityManager.createQuery(
				"select gc.groups.id from GroupCampaign gc where gc.campaign.id = :groupId");
		query.setParameter("groupId", groupId);
		return (List<Long>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Long> getFriendsInCampaign(Long memberId, Long campaignId) {

		Query query = entityManager.createQuery(
				"select ag.user.id from AccountCampaign ac, AccountGroup ag, Groups g where g.accountId = :memberId and g.groupsType.name = 'DEFAULT' and g.id = ag.groups.id and ag.user.id = ac.user.id and ac.campaign.id = :campaignId");
		query.setParameter("memberId", memberId);
		query.setParameter("campaignId", campaignId);
		return (List<Long>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getCampaignsByAllSegment(Long communityId) {

		Query query = entityManager.createQuery(
				"select p from Campaign p where p.communityId = :communityId and p.segment.id is NULL order by p.endDate DESC");
		query.setParameter("communityId", communityId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getCampaignsByAllSegmentWithCategory(Long communityId, Long categoryId) {

		Query query = entityManager.createQuery(
				"select p from Campaign p where p.communityId = :communityId and p.segment.id is NULL and p.category.id = :categoryId and (p.status.code='ACTIVE' or p.status.code= 'IN_PROGRESS') order by p.endDate DESC");
		query.setParameter("communityId", communityId);
		query.setParameter("categoryId", categoryId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getCampaignsBySegment(Long communityId, Long segmentId) {

		Query query = entityManager.createQuery(
				"select p from Campaign p where p.communityId = :communityId and p.segment.id = :segmentId order by p.endDate DESC");
		query.setParameter("communityId", communityId);
		query.setParameter("segmentId", segmentId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getCampaignsBySegmentWithCategory( Long segmentId, Long categoryId) {

		Query query = entityManager.createQuery(
				"select p from Campaign p where p.segment.id = :segmentId and p.category.id = :categoryId and (p.status.code='ACTIVE' or p.status.code= 'IN_PROGRESS') order by p.endDate DESC");
		query.setParameter("segmentId", segmentId);
		query.setParameter("categoryId", categoryId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getTopCampaignsBySegmentsAndCategory(List<Segment> segments, List<Category> categories, Integer limit) {
		
		
		String hql = "select c from Campaign c where c.status.code = 'ACTIVE' or c.status.code = 'IN_PROGRESS'";
		
		if (!segments.isEmpty()){
			hql += " and (";
			for (int i = 0; i < segments.size(); i++){
				Segment segment = segments.get(i);
				hql += "c.segment.id= " + segment.getId();
				if (i < (segments.size()-1)) hql += " or ";
				
			}
			hql += " or c.segment.id is null";
			hql += ") "; 
		}
		
		if (!categories.isEmpty()){
			hql += " and (";
			for (int i = 0; i < categories.size(); i++){
				Category category = categories.get(i);
				hql += "c.category.id= " + category.getId();
				if (i < (categories.size()-1)) hql += " or ";
				
			}
			hql += " or c.category.id is null";
			hql += ") "; 
		}
		
		hql += " order by rand()";
		
		System.out.println(hql);
		Query query = entityManager.createQuery(hql);
		//query.setParameter("pastDate", pastDate);
		query.setMaxResults(limit);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CampaignProgress> getCampaignAccountProgress(Long campaignId, Long accountId, Integer iteration) {

		Query query = entityManager.createQuery("select p from CampaignProgress p where p.campaign.id = :campaignId and p.accountId = :accountId and p.iteration = :iteration");
		query.setParameter("campaignId", campaignId);
		query.setParameter("accountId", accountId);
		query.setParameter("iteration", iteration);
		return (List<CampaignProgress>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CampaignGroupProgress> getCampaignGroupProgresses(Long campaignId, Long groupId, Integer iteration) {

		Query query = entityManager.createQuery("select p from CampaignGroupProgress p where p.campaign.id = :campaignId and p.group.id = :groupId and p.iteration = :iteration");
		query.setParameter("campaignId", campaignId);
		query.setParameter("groupId", groupId);
		query.setParameter("iteration", iteration);
		return (List<CampaignGroupProgress>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CampaignProgress getCampaignProgress(Long campaignId, Long accountId, Integer iteration) {

		Query query = entityManager.createQuery("select p from CampaignProgress p where p.campaign.id = :campaignId and p.accountId = :accountId and p.iteration = :iteration");
		query.setParameter("campaignId", campaignId);
		query.setParameter("accountId", accountId);
		query.setParameter("iteration", iteration);
		try {
			return (CampaignProgress)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CampaignGroupProgress getCampaignGroupProgress(Long campaignId, Long groupId, Integer iteration) {

		Query query = entityManager.createQuery("select p from CampaignGroupProgress p where p.campaign.id = :campaignId and p.group.id = :groupId and p.iteration = :iteration");
		query.setParameter("campaignId", campaignId);
		query.setParameter("groupId", groupId);
		query.setParameter("iteration", iteration);
		try {
			return (CampaignGroupProgress)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CampaignProgressActivity> getCampaignProgressActivities(Long campaignProgressId, Date startDate) {

		Query query = entityManager.createQuery("select p from CampaignProgressActivity p where p.campaignProgress.id = :campaignProgressId and p.startDate >= :startDate");
		query.setParameter("campaignProgressId", campaignProgressId);
		query.setParameter("startDate", startDate);
		return (List<CampaignProgressActivity>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CampaignGroupProgressActivity> getCampaignGroupProgressActivities(Long campaignGroupProgressId, Date startDate) {

		Query query = entityManager.createQuery("select p from CampaignGroupProgressActivity p where p.campaignGroupProgress.id = :campaignGroupProgressId and p.startDate >= :startDate");
		query.setParameter("campaignGroupProgressId", campaignGroupProgressId);
		query.setParameter("startDate", startDate);
		return (List<CampaignGroupProgressActivity>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CampaignProgressActivity getLatestCampaignProgressActivity(Long campaignProgressId) {

		Query query = entityManager.createQuery("select p from CampaignProgressActivity p where p.campaignProgress.id = :campaignProgressId order by p.startDate DESC ");
		query.setParameter("campaignProgressId", campaignProgressId);
		query.setMaxResults(1);
		try {
			return (CampaignProgressActivity)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CampaignProgressActivity getLatestCampaignProgressActivity(Long campaignProgressId, Date startDate) {

		Query query = entityManager.createQuery("select p from CampaignProgressActivity p where p.campaignProgress.id = :campaignProgressId and p.startDate >= :startDate order by p.startDate DESC ");
		query.setParameter("campaignProgressId", campaignProgressId);
		query.setParameter("startDate", startDate);
		query.setMaxResults(1);
		try {
			return (CampaignProgressActivity)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CampaignGroupProgressActivity getLatestCampaignGroupProgressActivity(Long campaignGroupProgressId, Date startDate) {

		Query query = entityManager.createQuery("select c from CampaignGroupProgressActivity c where c.campaignGroupProgress.id = :campaignGroupProgressId and p.startDate >= :startDate order by p.startDate DESC ");
		query.setParameter("campaignGroupProgressId", campaignGroupProgressId);
		query.setParameter("startDate", startDate);
		query.setMaxResults(1);
		try {
			return (CampaignGroupProgressActivity)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Image> fingImages(String name, Long communityId, String locale, Long portalId) {

		Query query = entityManager.createQuery(
				"select t from Image t where t.name=:name and ( t.community.id=:communityId or t.community.id is NULL)" +
				" and ( t.locale=:locale or t.locale='en_CA' or t.locale is NULL ) order by t.community.id DESC, t.locale DESC");
		
		query.setParameter("name", name);
		query.setParameter("communityId", communityId);
		query.setParameter("locale", locale);
		//query.setParameter("portalId", 1l);
		return (List<Image>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Image> fingImages(Long communityId, String locale, Long portalTypeId) {

		Query query = entityManager.createQuery(
				"select t from Image t where (t.community.id=:communityId)" +
				" and ( t.locale=:locale) and portalType.id = :portalTypeId " +
				" or t.id in ("+
				"select im.id from Image im where im.portalType.id = :portalTypeId "+
				"and im.community.id = :communityId and im.locale='en_CA' " +
				"and im.imageTypeId not in (select imageTypeId from Image where community.id = :communityId and locale= :locale and portalType.id = :portalTypeId)) " +
				" or t.id in ("+
				"select im.id from Image im where im.portalType.id = :portalTypeId "+
				"and im.community.id is NULL and im.locale='en_CA' " +
				"and im.imageTypeId not in (select imageTypeId from Image where community.id = :communityId and locale= :locale and portalType.id = :portalTypeId "+
													" or t.id in ("+
													"select im.id from Image im where im.portalType.id = :portalTypeId "+
													"and im.community.id = :communityId and im.locale='en_CA' " +
													"and im.imageTypeId not in (select imageTypeId from Image where community.id = :communityId and locale= :locale and portalType.id = :portalTypeId)) " +
										   "))");
		
		query.setParameter("communityId", communityId);
		query.setParameter("locale", locale);
		query.setParameter("portalTypeId", portalTypeId);
		return (List<Image>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Translation findTranslation(String translationKey, Long communityId, Long portalTypeId, String locale, Long componentId) {

		Query query = entityManager.createQuery(
				//communityId is not NULL, portalTypeId is not NULL
				"select t from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale and t.translationKey = :translationKey "+
				//communityId is not NULL, portalTypeId is NULL
				"or t.id in ("+
							"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale and t.translationKey = :translationKey " +
							" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale and t.translationKey = :translationKey)) "+
				//communityId is Null, portalTypeId is not NULL
				"or t.id in ("+
							"select t2.id from Translation t2 where t2.community.id is NULL and t2.component.id =:componentId and t2.portalType.id = :portalTypeId and t2.locale=:locale and t.translationKey = :translationKey "+
							"and t2.translationKey not in ("+
										//communityId is not NULL, portalTypeId is not NULL
										"select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale and t.translationKey = :translationKey "+
										//communityId is not NULL, portalTypeId is NULL
										"or t.id in ("+
											"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale and t.translationKey = :translationKey " +
											" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale and t.translationKey = :translationKey ))"+
							"))"+
				//communityId is Null, portalTypeId is NULL
				"or t.id in ("+
							"select t3.id from Translation t3 where t3.community.id is NULL and t3.component.id = :componentId and t3.portalType.id is NULL and t3.locale=:locale and t.translationKey = :translationKey "+
							"and t3.translationKey not in ("+
									//communityId is not NULL, portalTypeId is not NULL
									"select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale and t.translationKey = :translationKey "+
									//communityId is not NULL, portalTypeId is NULL
									"or t.id in ("+
										"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale and t.translationKey = :translationKey " +
										" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale and t.translationKey = :translationKey ))"+
									//communityId is Null, portalTypeId is not NULL
									"or t.id in ("+
										"select t2.id from Translation t2 where t2.community.id is NULL and t2.component.id =:componentId and t2.portalType.id = :portalTypeId and t2.locale=:locale and t.translationKey = :translationKey "+
										"and t2.translationKey not in ("+
													//communityId is not NULL, portalTypeId is not NULL
													"select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale and t.translationKey = :translationKey "+
													//communityId is not NULL, portalTypeId is NULL
													"or t.id in ("+
														"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale and t.translationKey = :translationKey " +
														" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale and t.translationKey = :translationKey ))"+
										"))"+
									
							"))"+
				")");

		query.setParameter("translationKey",  translationKey);
		query.setParameter("portalTypeId", portalTypeId);
		query.setParameter("locale",  locale);
		query.setParameter("communityId", communityId);
		query.setParameter("componentId", componentId);
		return (Translation) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Translation> findTranslationsList(TranslationForm translationFrom, Long communityId, Long portalTypeId ) {

		Query query = entityManager.createQuery(
				//communityId is not NULL, portalTypeId is not NULL
				"select t from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale "+
				//communityId is not NULL, portalTypeId is NULL
				"or t.id in ("+
							"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale " +
							" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale))"+
				//communityId is Null, portalTypeId is not NULL
				"or t.id in ("+
							"select t2.id from Translation t2 where t2.community.id is NULL and t2.component.id =:componentId and t2.portalType.id = :portalTypeId and t2.locale=:locale "+
							"and t2.translationKey not in ("+
										//communityId is not NULL, portalTypeId is not NULL
										"select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale "+
										//communityId is not NULL, portalTypeId is NULL
										"or t.id in ("+
											"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale " +
											" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale))"+
							"))"+
				//communityId is Null, portalTypeId is NULL
				"or t.id in ("+
							"select t3.id from Translation t3 where t3.community.id is NULL and t3.component.id = :componentId and t3.portalType.id is NULL and t3.locale=:locale "+
							"and t3.translationKey not in ("+
									//communityId is not NULL, portalTypeId is not NULL
									"select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale "+
									//communityId is not NULL, portalTypeId is NULL
									"or t.id in ("+
										"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale " +
										" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale))"+
									//communityId is Null, portalTypeId is not NULL
									"or t.id in ("+
										"select t2.id from Translation t2 where t2.community.id is NULL and t2.component.id =:componentId and t2.portalType.id = :portalTypeId and t2.locale=:locale "+
										"and t2.translationKey not in ("+
													//communityId is not NULL, portalTypeId is not NULL
													"select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale "+
													//communityId is not NULL, portalTypeId is NULL
													"or t.id in ("+
														"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale " +
														" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale))"+
										"))"+
									
							"))"+
				")");

		query.setParameter("componentId",  translationFrom.getComponentId());
		query.setParameter("portalTypeId", portalTypeId);
		query.setParameter("locale",  translationFrom.getLocale());
		query.setParameter("communityId", communityId);
		return (List<Translation>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Translation> findBodyTranslationsList(TranslationForm translationFrom, Long communityId, Long portalTypeId ) {

		Query query = entityManager.createQuery(
				//communityId is not NULL, portalTypeId is not NULL
				"select t from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale "+
				//communityId is not NULL, portalTypeId is NULL
				"or t.id in ("+
							"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale " +
							" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale))"+
				//communityId is Null, portalTypeId is not NULL
				"or t.id in ("+
							"select t2.id from Translation t2 where t2.community.id is NULL and t2.component.id =:componentId and t2.portalType.id = :portalTypeId and t2.locale=:locale "+
							"and t2.translationKey not in ("+
										//communityId is not NULL, portalTypeId is not NULL
										"select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale "+
										//communityId is not NULL, portalTypeId is NULL
										"or t.id in ("+
											"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale " +
											" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale))"+
							"))"+
				//communityId is Null, portalTypeId is NULL
				"or t.id in ("+
							"select t3.id from Translation t3 where t3.community.id is NULL and t3.component.id = :componentId and t3.portalType.id is NULL and t3.locale=:locale "+
							"and t3.translationKey not in ("+
									//communityId is not NULL, portalTypeId is not NULL
									"select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale "+
									//communityId is not NULL, portalTypeId is NULL
									"or t.id in ("+
										"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale " +
										" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale))"+
									//communityId is Null, portalTypeId is not NULL
									"or t.id in ("+
										"select t2.id from Translation t2 where t2.community.id is NULL and t2.component.id =:componentId and t2.portalType.id = :portalTypeId and t2.locale=:locale "+
										"and t2.translationKey not in ("+
													//communityId is not NULL, portalTypeId is not NULL
													"select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale "+
													//communityId is not NULL, portalTypeId is NULL
													"or t.id in ("+
														"select t1.id from Translation t1 where t1.community.id = :communityId and t1.component.id=:componentId and t1.portalType.id is NULL and t1.locale=:locale " +
														" and t1.translationKey not in (select t.translationKey from Translation t where t.community.id = :communityId and t.component.id=:componentId and t.portalType.id = :portalTypeId and t.locale=:locale))"+
										"))"+
									
							"))"+
				")");

		query.setParameter("componentId",  translationFrom.getComponentId());
		query.setParameter("portalTypeId", portalTypeId);
		query.setParameter("locale",  translationFrom.getLocale());
		query.setParameter("communityId", communityId);
		return (List<Translation>) query.getResultList();

	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Long findPortalTypeListId(String portalName) {
		Query query = entityManager.createQuery("select p.id from PortalType p where p.type = :portalName");
		query.setParameter("portalName",  portalName);
		try {
			return (Long)query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public PortalLayout findPortalLayoutByCommunityPortalThemeId(Long communityPortalThemeId) {
		Query query = entityManager.createQuery("select p from PortalLayout p where p.communityPortalTheme.id = :communityPortalThemeId");
		query.setParameter("communityPortalThemeId",  communityPortalThemeId);
		try {
			return (PortalLayout)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<VehicleType> findAllVehicleTypes() {
		Query query = entityManager.createQuery("select v from VehicleType v");

		return (List<VehicleType>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CarbonTransaction> findAllCarbonTransaction(String communityName, Long communityId) {
		Query query = entityManager.createQuery("select t from CarbonTransaction t where t.communityId = :communityId");
		query.setParameter("communityId",  communityId);
		return (List<CarbonTransaction>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CarbonTransaction> findCarbonTransaction(String email, String communityName, Long communityId) {
		Query query = entityManager.createQuery("select t from CarbonTransaction t where t.email=:email and t.communityId = :communityId");
		query.setParameter("email",  email);
		query.setParameter("communityId",  communityId);
		return (List<CarbonTransaction>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<CarbonTransactionDetail> findCarbonTransactionDetail(Long carbonTransactionId) {
		Query query = entityManager.createQuery("select c from CarbonTransactionDetail c where c.carbonTransaction.id = :carbonTransactionId");
		query.setParameter("carbonTransactionId",  carbonTransactionId);
		return (List<CarbonTransactionDetail>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CarbonTransaction findLatestCarbonTransaction(String email, Long communityId) {
		Query query = entityManager.createQuery("select c from CarbonTransaction c where c.email=:email and c.communityId = :communityId and " +
				"c.created = (select max(cf.created) from CarbonTransaction cf where cf.email = c.email and cf.communityId = c.communityId)");
		query.setParameter("email",  email);
		query.setParameter("communityId",  communityId);
		return (CarbonTransaction) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CarbonTransaction findLatestCarbonTransaction(String email, Double totalCost, Long communityId) {
		Query query = entityManager.createQuery("select c from CarbonTransaction c where c.email=:email and c.totalCost= :totalCost and c.communityId = :communityId and " +
				"c.created = (select max(cf.created) from CarbonTransaction cf where cf.email = c.email and cf.totalCost= c.totalCost and cf.communityId = c.communityId)");
		query.setParameter("email",  email);
		query.setParameter("totalCost",  totalCost);
		query.setParameter("communityId",  communityId);
		return (CarbonTransaction) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CarbonTransaction findCarbonTransaction(Long carbontransactionId) {
		Query query = entityManager.createQuery("select c from CarbonTransaction c where c.id = :carbontransactionId");
		query.setParameter("carbontransactionId",  carbontransactionId);
		return (CarbonTransaction) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public CarbonTransaction findCarbonTransaction(String reference) {
		Query query = entityManager.createQuery("select c from CarbonTransaction c where c.reference = :reference");
		query.setParameter("reference",  reference);
		return (CarbonTransaction) query.getSingleResult();
	}
///////////////////////////////////////////////
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double getElectricityMonthConsumptionBySource(Long sourceId, Integer month, Integer year) {
		String queryString = "select e.onPeakConsumption + e.midPeakConsumption + e.offPeakConsumption from ElectricityDataMonth e where YEAR(e.created) = :year and MONTH(e.created) = :month";
		queryString += " and e.source.id= :sourceId";
		queryString += " group by YEAR(e.created), MONTH(e.created)";
		
		Query query = entityManager.createQuery(queryString);
		query.setParameter("month",   month);
		query.setParameter("sourceId",   sourceId);
		query.setParameter("year",  year);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ElectricityRate> findElectricityRate() {
		Query query = entityManager.createQuery("select e from ElectricityRate e");
		return (List<ElectricityRate>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findElectricityWeatherNormHistoryByMonth(Integer month) {
		Query query = entityManager.createQuery("select e.cdd from ElectricityWeatherNormHistory e where e.month = :month");
		query.setParameter("month",  month);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findElectricityWeatherNormHistoryByYear() {
		Query query = entityManager.createQuery("select SUM(e.cdd) from ElectricityWeatherNormHistory e");
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findElectricityWeatherNormByMonth(Integer month, Integer year) {
		Query query = entityManager.createQuery("select e.cdd from ElectricityWeatherNormMonth e where e.month = :month and e.year = :year");
		query.setParameter("month",  month);
		query.setParameter("year",  year);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findElectricityWeatherNormByYear(Integer year) {
		Query query = entityManager.createQuery("select e.cdd from ElectricityWeatherNormYear e where e.year = :year");
		query.setParameter("year",  year);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findElectricityBaselineByYear(Integer month, Integer year) {
		Query query = entityManager.createQuery("select e.consumption from ElectricityBaseline e where e.month = :month and e.year = :year");
		query.setParameter("month",  month);
		query.setParameter("year",  year);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findElectricityBaselineByMonth(Integer month) {
		Query query = entityManager.createQuery("select e.consumption from ElectricityBaseline e where e.month = :month and e.year is NULL");
		query.setParameter("month",  month);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public ElectricityAverage findElectricityAverageByType(Long memberId, String averageType) {
		Query query = entityManager.createQuery("select e from ElectricityAverage e where e.user.id = :memberId and e.averageType.type = :averageType");
		query.setParameter("memberId",  memberId);
		query.setParameter("averageType",  averageType);
		try {
			return (ElectricityAverage)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ElectricitySummary> findElectricitySummary(Long sourceId) {
		Query query = entityManager.createQuery("select e from ElectricitySummary e where e.source.id=:sourceId order by e.startDate DESC");
		query.setParameter("sourceId",  sourceId);
		return (List<ElectricitySummary>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ElectricityDataMonth> findElectricityDataYear(Long sourceId) {
		Query query = entityManager.createQuery("select e from ElectricityDataMonth e where e.source.id=:sourceId group by YEAR(e.created) order by e.created DESC");
		query.setParameter("sourceId",  sourceId);
		query.setMaxResults(5);
		return (List<ElectricityDataMonth>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findElectricityConsumptionYear(Long sourceId, Date date) {
		Query query = entityManager.createQuery("select SUM(e.energy) from ElectricityDataMonth e where e.source.id=:sourceId and YEAR(e.created) = YEAR(:date)");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
		
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findSumElectricityYear(String column, Long sourceId, Date date) {
		Query query = entityManager.createQuery("select SUM(e." + column +")  from ElectricityDataMonth e where e.source.id=:sourceId and YEAR(e.created) = YEAR(:date)");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
			
	//************************************************ Transaction ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SalesOrderType findTransactionType(String name) {
			Query query = entityManager.createQuery("select t from SalesOrderType t where t.name = :name");
			query.setParameter("name",  name);
			try {
				return (SalesOrderType)query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SalesOrder findTransactionByOrderNumber(Long orderNumber) {
			Query query = entityManager.createQuery("select t from SalesOrder t where t.orderNumber = :orderNumber");
			query.setParameter("orderNumber",  orderNumber);
			try {
				return (SalesOrder)query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SalesOrderBilling findBillingByOrderNumber(Long orderNumber) {
			Query query = entityManager.createQuery("select t from SalesOrderBilling t where t.salesOrder.orderNumber = :orderNumber");
			query.setParameter("orderNumber",  orderNumber);
			try {
				return (SalesOrderBilling)query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public SalesOrderReferenceType findTransactionReferenceType(String name) {
			Query query = entityManager.createQuery("select t from SalesOrderReferenceType t where t.name = :name");
			query.setParameter("name",  name);
			try {
				return (SalesOrderReferenceType)query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SalesOrderDetail> getTransactionDetails(Long salesOrderNumber) {
			Query query = entityManager.createQuery("select t from SalesOrderDetail t where t.salesOrder.orderNumber = :salesOrderNumber");
			query.setParameter("salesOrderNumber",  salesOrderNumber);
			return (List<SalesOrderDetail>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SalesOrderDetail> getTransactionDetails(Long accountId, Integer typeId, Date year) {
			Query query = entityManager.createQuery("select t from SalesOrderDetail t where t.salesOrder.user.id = :accountId and t.salesOrderType.id = :typeId and t.created >= :year order by t.created ASC");
			query.setParameter("accountId",  accountId);
			query.setParameter("typeId",  typeId);
			query.setParameter("year",  year);
			return (List<SalesOrderDetail>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SalesOrderDetail> getTransactionDetails(Long accountId, Integer typeId, Integer month, Integer year) {
			Query query = entityManager.createQuery("select t from SalesOrderDetail t where t.salesOrder.user.id = :accountId and t.salesOrderType.id = :typeId and MONTH(t.created) = :month and YEAR(t.created) = :year order by t.created ASC");
			query.setParameter("accountId",  accountId);
			query.setParameter("typeId",  typeId);
			query.setParameter("month",  month);
			query.setParameter("year",  year);
			return (List<SalesOrderDetail>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<PointsTransaction> getPointsTransaction(Long accountId, Integer typeId, Date year) {
			Query query = entityManager.createQuery("select t from PointsTransaction t where t.pointsAccount.user.id = :accountId and t.pointsTransactionType.id = :typeId and t.date >= :year order by t.date ASC");
			query.setParameter("accountId",  accountId);
			query.setParameter("typeId",  typeId);
			query.setParameter("year",  year);
			return (List<PointsTransaction>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<PointsTransaction> getPointsTransaction(Long accountId, Integer typeId, Integer month, Integer year) {
			Query query = entityManager.createQuery("select t from PointsTransaction t where t.pointsAccount.user.id = :accountId and t.pointsTransactionType.id = :typeId and MONTH(t.date) = :month and YEAR(t.date) = :year order by t.date ASC");
			query.setParameter("accountId",  accountId);
			query.setParameter("typeId",  typeId);
			query.setParameter("month",  month);
			query.setParameter("year",  year);
			return (List<PointsTransaction>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SalesOrder> findTransactions(Long accountId, Long communityId, Integer typeId) {
			Query query = entityManager.createQuery("select t from SalesOrder t where t.user.id = :accountId and t.community.id = :communityId and t.salesOrderType.id = :typeId order by t.created DESC");
			query.setParameter("accountId",  accountId);
			query.setParameter("communityId",  communityId);
			query.setParameter("typeId",  typeId);
			return (List<SalesOrder>) query.getResultList();
		}
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SalesOrder> findTransactions(Long accountId,  Integer typeId) {
			Query query = entityManager.createQuery("select t.salesOrder from SalesOrderDetail t where t.salesOrder.user.id = :accountId and t.salesOrderType.id = :typeId group by t.salesOrder order by t.created DESC");
			query.setParameter("accountId",  accountId);
			query.setParameter("typeId",  typeId);
			return (List<SalesOrder>) query.getResultList();
		}
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<SalesOrderDetail> findTransactionDetails(Long salesOrderId) {
			Query query = entityManager.createQuery("select t from SalesOrderDetail t where t.salesOrder.orderNumber = :salesOrderId");
			query.setParameter("salesOrderId",  salesOrderId);
			return (List<SalesOrderDetail>) query.getResultList();
		}
	//************************************************End of Transaction***************************************************************************
		
	//************************************************ Electricity ********************************************************************************
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Date getLatestMinuteData(Long sourceId) {
			Query query = entityManager.createQuery("select MAX(e.created) from ElectricityDataMinute e where e.source.id = :sourceId");
			query.setParameter("sourceId",  sourceId);
			try {
				return (Date)query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<ElectricityDataMinute> findElectricityMinuteWithWeekends( Long sourceId, Date startDate, Date endDate, boolean includeWeekends) {
			String queryString = "";
			if (includeWeekends){
				queryString = "select e from ElectricityDataMinute e where e.source.id=:sourceId and e.created > :startDate and e.created <= :endDate";
			}else{
				queryString = "select e from ElectricityDataMinute e where e.source.id=:sourceId and e.created > :startDate and e.created <= :endDate and (WEEKDAY(e.created) = 5 or WEEKDAY(e.created) = 6)";
			}
			Query query = entityManager.createQuery(queryString);
			query.setParameter("sourceId",  sourceId);
			query.setParameter("startDate",  startDate);
			query.setParameter("endDate",  endDate);
			return (List<ElectricityDataMinute>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<ElectricityDataMinute> findElectricityMinuteWithoutWeekends( Long sourceId, Date startDate, Date endDate, boolean includeWeekends) {
			String queryString = "";
			if (includeWeekends){
				queryString = "select e from ElectricityDataMinute e where e.source.id=:sourceId and e.created > :startDate and e.created <= :endDate";
			}else{
				queryString = "select e from ElectricityDataMinute e where e.source.id=:sourceId and e.created > :startDate and e.created <= :endDate and WEEKDAY(e.created) != 5 and WEEKDAY(e.created) !=6";
			}
			Query query = entityManager.createQuery(queryString);
			query.setParameter("sourceId",  sourceId);
			query.setParameter("startDate",  startDate);
			query.setParameter("endDate",  endDate);
			return (List<ElectricityDataMinute>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<ElectricityDataHour> findLatestElectricityHour(Integer length, Long sourceId) {
			Query query = entityManager.createQuery("select e from ElectricityDataHour e where e.source.id=:sourceId order by e.created DESC");
			query.setParameter("sourceId",  sourceId);
			query.setMaxResults(length);
			return (List<ElectricityDataHour>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<ElectricityDataDay> findLatestElectricityDay(Integer length, Long sourceId) {
			Query query = entityManager.createQuery("select e from ElectricityDataDay e where e.source.id=:sourceId order by e.created DESC");
			query.setParameter("sourceId",  sourceId);
			query.setMaxResults(length);
			return (List<ElectricityDataDay>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<ElectricityDataDay> findLatestElectricityDayWithWeekends(int length, Long sourceId, boolean includeWeekends) {
			String queryString = "";
			if (includeWeekends){
				queryString = "select e from ElectricityDataDay e where e.source.id=:sourceId order by e.created DESC";
			}else{
				queryString = "select e from ElectricityDataDay e where e.source.id=:sourceId and (WEEKDAY(e.created) = 5 or WEEKDAY(e.created) =6) order by e.created DESC";
			}
			Query query = entityManager.createQuery(queryString);
			query.setParameter("sourceId",  sourceId);
			query.setMaxResults(length);
			return (List<ElectricityDataDay>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<ElectricityDataDay> findLatestElectricityDayWithoutWeekends(int length, Long sourceId, boolean includeWeekends) {
			String queryString = "";
			if (includeWeekends){
				queryString = "select e from ElectricityDataDay e where e.source.id=:sourceId order by e.created DESC";
			}else{
				queryString = "select e from ElectricityDataDay e where e.source.id=:sourceId and WEEKDAY(e.created) != 5 and WEEKDAY(e.created) !=6 order by e.created DESC";
			}
			Query query = entityManager.createQuery(queryString);
			query.setParameter("sourceId",  sourceId);
			query.setMaxResults(length);
			return (List<ElectricityDataDay>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<ElectricityDataDay> findElectricityDayByTimestampWithoutWeekends(Integer length, Long sourceId, Date date, boolean includeWeekends) {
			String queryString = "";
			if (includeWeekends){
				queryString = "select e from ElectricityDataDay e where e.source.id=:sourceId and e.created < :date order by e.created DESC";
			}else{
				queryString = "select e from ElectricityDataDay e where e.source.id=:sourceId and e.created < :date and WEEKDAY(e.created) != 5 and WEEKDAY(e.created) !=6 order by e.created DESC";
			}
			Query query = entityManager.createQuery(queryString);
			query.setParameter("sourceId",  sourceId);
			query.setParameter("date",  date);
			query.setMaxResults(length);
			return (List<ElectricityDataDay>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<ElectricityDataDay> findElectricityDayByTimestampWithWeekends(Integer length, Long sourceId, Date date, boolean includeWeekends) {
			String queryString = "";
			if (includeWeekends){
				queryString = "select e from ElectricityDataDay e where e.source.id=:sourceId and e.created < :date order by e.created DESC";
			}else{
				queryString = "select e from ElectricityDataDay e where e.source.id=:sourceId and e.created < :date and (WEEKDAY(e.created) = 5 or WEEKDAY(e.created) =6) order by e.created DESC";
			}
			Query query = entityManager.createQuery(queryString);
			query.setParameter("sourceId",  sourceId);
			query.setParameter("date",  date);
			query.setMaxResults(length);
			return (List<ElectricityDataDay>) query.getResultList();
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public List<ElectricityDataMonth> findLatestElectricityMonth(Integer length, Long sourceId) {
			Query query = entityManager.createQuery("select e from ElectricityDataMonth e where e.source.id=:sourceId order by e.created DESC");
			query.setParameter("sourceId",  sourceId);
			query.setMaxResults(length);
			return (List<ElectricityDataMonth>) query.getResultList();
		}
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ElectricityDataHour> findElectricityHourByTimestamp(Integer length, Long sourceId, Date date) {
		Query query = entityManager.createQuery("select e from ElectricityDataHour e where e.source.id=:sourceId and e.created <= :date order by e.created DESC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		query.setMaxResults(length);
		return (List<ElectricityDataHour>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ElectricityDataHour> findElectricityHourByInterval(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select e from ElectricityDataHour e where e.source.id=:sourceId and e.created >= :startDate and e.created <= :endDate order by e.created ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (List<ElectricityDataHour>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ElectricityDataDay> findElectricityDayByTimestamp(Integer length, Long sourceId, Date date) {
		Query query = entityManager.createQuery("select e from ElectricityDataDay e where e.source.id=:sourceId and e.created <= :date order by e.created DESC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		query.setMaxResults(length);
		return (List<ElectricityDataDay>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ElectricityDataMonth> findElectricityMonthByTimestamp(Integer length, Long sourceId, Date date) {
		Query query = entityManager.createQuery("select e from ElectricityDataMonth e where e.source.id=:sourceId and e.created <= :date order by e.created DESC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		query.setMaxResults(length);
		return (List<ElectricityDataMonth>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public ElectricityDataHour findElectricityDataHour(Long sourceId, Date date) {
		Query query = entityManager.createQuery("select e from ElectricityDataHour e where e.source.id=:sourceId and e.created = :date");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		try {
			return (ElectricityDataHour)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public ElectricityDataDay findElectricityDataDay(Long sourceId, Date date) {
		Query query = entityManager.createQuery("select e from ElectricityDataDay e where e.source.id=:sourceId and e.created = :date");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		try {
			return (ElectricityDataDay)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public ElectricityDataMonth findElectricityDataMonth(Long sourceId, Date date) {
		Query query = entityManager.createQuery("select e from ElectricityDataMonth e where e.source.id=:sourceId and e.created = :date");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("date",  date);
		try {
			return (ElectricityDataMonth)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ElectricityDataMonth> findElectricityDataYear(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select e from ElectricityDataMonth e where e.source.id=:sourceId and e.created >= :startDate and e.created <= :endDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (List<ElectricityDataMonth>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Integer findCountsElectricity(String table, Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select count(*) from " + table + " e where e.source.id=:sourceId and e.created >= :startDate and e.created < :endDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		try {
			return (Integer)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findSumElectricity(String table, Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select SUM(e.energy) from " + table + " e where e.source.id=:sourceId and e.created >= :startDate and e.created < :endDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findMinMaxSumElectricityCost(String table, List<User> communityUsers, Date startDate, Date endDate, Boolean findMin) {
		String hql = "";
		
		hql += "select SUM(e.onPeakSpend + e.midPeakSpend + e.offPeakSpend)/Count(e.energy)*24 from " + table + " e, AccountSource a where e.created >= :startDate and e.created < :endDate";
		if (!communityUsers.isEmpty()){
			hql += " and (";
			for (int i = 0; i < communityUsers.size(); i++){
				User communityUser = communityUsers.get(i);
				hql += "a.user.id= " + communityUser.getId();
				hql += " and a.source.id = e.source.id";
				if (i < (communityUsers.size()-1)) hql += " or ";
				
			}
			hql += ") "; 
		}
		
		hql += " group by e.source.id order by SUM(e.onPeakSpend + e.midPeakSpend + e.offPeakSpend)/Count(e.energy)*24 ";
		if (findMin) hql += "ASC";
		else hql += "DESC";
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		query.setMaxResults(1);
		try {
			return (Double)query.getSingleResult();
		} catch (Exception e) {
			return 0.0;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findMinMaxSumElectricity(String table, List<User> communityUsers, Date startDate, Date endDate, Boolean findMin) {
		String hql = "";
		
		hql += "select SUM(e.energy)/Count(e.energy)*24 from " + table + " e, AccountSource a where e.created >= :startDate and e.created < :endDate";
		if (!communityUsers.isEmpty()){
			hql += " and (";
			for (int i = 0; i < communityUsers.size(); i++){
				User communityUser = communityUsers.get(i);
				hql += "a.user.id= " + communityUser.getId();
				hql += " and a.source.id = e.source.id";
				if (i < (communityUsers.size()-1)) hql += " or ";
				
			}
			hql += ") "; 
		}
		
		hql += " group by e.source.id order by SUM(e.energy)/Count(e.energy)*24 ";
		if (findMin) hql += "ASC";
		else hql += "DESC";
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		query.setMaxResults(1);
		try {
			return (Double)query.getSingleResult();
		} catch (Exception e) {
			return 0.0;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Double findSumCostElectricity(String table, Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select SUM(e.onPeakSpend + e.midPeakSpend + e.offPeakSpend) from " + table + " e where e.source.id=:sourceId and e.created >= :startDate and e.created < :endDate");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		try {
			return (Double)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	//************************************************End of Electricity***************************************************************************
	
	//************************************************ Fundraise ********************************************************************************
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Double getFundraisedValue(Long accountCampaignId) {
			Query query = entityManager.createQuery("select SUM(f.amount) from Fundraise f where f.accountCampaign.id = :accountCampaignId");
			query.setParameter("accountCampaignId",  accountCampaignId);
			try {
				return (Double)query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
		public Double getIndividualFundraisedValue(Long accountId, Long accountCampaignId) {
			Query query = entityManager.createQuery("select SUM(f.amount) from Fundraise f where f.user.id = :accountId and f.accountCampaign.id = :accountCampaignId");
			query.setParameter("accountId",  accountId);
			query.setParameter("accountCampaignId",  accountCampaignId);
			try {
				return (Double)query.getResultList().get(0);
			} catch (Exception e) {
				return null;
			}
		}
		
		//************************************************End of Fundraise***************************************************************************
	
	//************************************************ User ********************************************************************************
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public User findUser(Long communityId, String accountEmail) {
		Query query = entityManager.createQuery("select u from Account u where u.defaultCommunity =:communityId and u.email = :accountEmail");
		query.setParameter("communityId",  communityId);
		query.setParameter("accountEmail",  accountEmail);
		try {
			return (User)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<User> getUsersInCommunity(Long communityId) {
		Query query = entityManager.createQuery("select a from Account a, Account_Authority aa where a.defaultCommunity = :communityId and aa.user.id = a.id " +
				"and (aa.userRole.authority ='USER' or aa.userRole.authority = 'GROUP_GUEST') order by a.firstName");
		query.setParameter("communityId",  communityId);
		return (List<User>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Long> getPeersInCommunity(Long accountId, Long communityId) {
		Query query = entityManager.createQuery("select a.id from Account a, Account_Authority aa where a.defaultCommunity = :communityId and a.id != :accountId" +
				" and aa.user.id = a.id and (aa.userRole.authority = 'USER' or aa.userRole.authority = 'GROUP_GUEST') order by a.firstName");
		query.setParameter("accountId",  accountId);
		query.setParameter("communityId",  communityId);
		return (List<Long>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Groups> getGroupsInCampagin(Long campaignId) {
		Query query = entityManager.createQuery("select a.groups from GroupCampaign a where a.campaign.id = :campaignId");
		query.setParameter("campaignId",  campaignId);
		return (List<Groups>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<User> getUsersInCampagin(Long campaignId) {
		Query query = entityManager.createQuery("select a.user from AccountCampaign a where a.campaign.id = :campaignId");
		query.setParameter("campaignId",  campaignId);
		return (List<User>) query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public User findUser(Long userId) {
		Query query = entityManager.createQuery("select u from Account u where u.id = :userId");
		query.setParameter("userId",  userId);
		try {
			return (User)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public User findActiveUserByEmail(String accountEmail) {
		
		Query query = entityManager.createQuery("select u from Account u, Account_Authority aa where u.email = :accountEmail and u.status.code='ACTIVE' and u.id = aa.user.id and (aa.userRole.authority= 'USER' or aa.userRole.authority= 'GROUP_GUEST')");
		query.setParameter("accountEmail",  accountEmail);
		try {
			return (User)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public User findAllUserByEmail(String accountEmail) {
		
		Query query = entityManager.createQuery("select u from Account u where u.email = :accountEmail");
		query.setParameter("accountEmail",  accountEmail);
		try {
			return (User)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public User findUserByEmailWithException(String accountEmail) {
		
		Query query = entityManager.createQuery("select u from Account u, Account_Authority aa where u.email = :accountEmail and u.status.code='ACTIVE' and u.id = aa.user.id and (aa.userRole.authority= 'USER' or aa.userRole.authority= 'GROUP_GUEST')");
		query.setParameter("accountEmail",  accountEmail);
		
		return (User)query.getSingleResult();
		
	}
	
	/**
	 * Finds a member based on member email and sign up mode
	 * 
	 * @param accountEmail
	 * @param mode
	 * @return member
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public User findUserByEmailAndModeWithException(String accountEmail, Integer mode) {
		
		Query query = entityManager.createQuery("select u from Account u, Account_Authority aa where u.email = :accountEmail and u.mode = :mode and u.status.code='ACTIVE' and u.id = aa.user.id and (aa.userRole.authority= 'USER' or aa.userRole.authority= 'GROUP_GUEST')");
		query.setParameter("accountEmail",  accountEmail);
		query.setParameter("mode", mode);
		return (User)query.getSingleResult();
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public PointsType getPointsType(String type) {
		Query query = entityManager.createQuery("select p from PointsType p where p.type = :type");
		query.setParameter("type",  type);
		try {
			return (PointsType)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public PointsAccount getPointsAccount(Long accountId, Long pointsTypeId) {
		Query query = entityManager.createQuery("select p from PointsAccount p where p.user.id = :accountId and p.pointsType.id = :pointsTypeId");
		query.setParameter("accountId",  accountId);
		query.setParameter("pointsTypeId",  pointsTypeId);
		try {
			return (PointsAccount)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public AccountCampaign getAccountCampaign(Long accountId, Long campaignId) {
		Query query = entityManager.createQuery("select a from AccountCampaign a where a.user.id = :accountId and a.campaign.id = :campaignId");
		query.setParameter("accountId",  accountId);
		query.setParameter("campaignId",  campaignId);
		try {
			return (AccountCampaign)query.getResultList().get(0);
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public AccountCampaign getAccountCampaignByGroupAndCampaign(Long groupId, Long campaignId) {
		Query query = entityManager.createQuery("select a from AccountCampagin a where a.groups.id = :groupId and a.campaign.id = :campaignId");
		query.setParameter("groupId",  groupId);
		query.setParameter("campaignId",  campaignId);
		try {
			return (AccountCampaign)query.getResultList().get(0);
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public AccountCampaign getAccountCampaign(Long accountCampaignId) {
		Query query = entityManager.createQuery("select a from AccountCampaign a where a.id = :accountCampaignId");
		query.setParameter("accountCampaignId",  accountCampaignId);
		try {
			return (AccountCampaign)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<GroupCampaign> getGroupCampaigns(List<Groups> groups, Long campaignId) {
		String hql = "select g from GroupCampaign g where g.campaign.id = :campaignId";
		if (!groups.isEmpty()){
			hql += " and (";
			for (int i = 0; i< groups.size(); i++){
				hql += " g.groups.id = " + groups.get(i).getId();
				if (i < groups.size()-1) hql += " or";
			}
			hql += ")";
		}
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("campaignId",  campaignId);
		try {
			return (List<GroupCampaign>)query.getResultList();
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Account_Authority getAccountAuthority(Long accountId) {
		Query query = entityManager.createQuery("select aa from Account_Authority aa where aa.user.id = :accountId");
		query.setParameter("accountId",  accountId);
		try {
			return (Account_Authority)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getAccountCampaigns(Long accountId) {
		Query query = entityManager.createQuery("select a.campaign from AccountCampaign a where a.user.id =:accountId");
		query.setParameter("accountId",  accountId);
		return (List<Campaign>) query.getResultList();
	}
	
	/**
	 * Get group campaigns with active or in progress status
	 * 
	 * @param groups
	 * @param categories
	 * @return a list of qualified campaigns
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getGroupActiveCampaignsWithCategories(List<Groups> groups, List<Category> categories) {
		String hql = "select distinct(g.campaign) from GroupCampaign g where (g.campaign.status.code = 'ACTIVE' or g.campaign.status.code='IN_PROGRESS') ";
		if (!groups.isEmpty()){
			hql += "and (";
			for (int i =0; i < groups.size(); i++){
				hql += "g.groups.id = " + groups.get(i).getId();
				if (i < groups.size() -1) hql += " or ";
			}
			hql += ")";
		}
		
		if (!categories.isEmpty()){
			hql += "and (";
			for (int i = 0; i < categories.size(); i++){
				hql += "g.campaign.category.id = " + categories.get(i).getId();
				if (i < categories.size()-1) hql += " or ";
			}
			hql += " or g.campaign.category.id is null";
			hql += ") ";
		}
		
		hql += "order by g.campaign.status.id ASC";
		Query query = entityManager.createQuery(hql);
		return (List<Campaign>) query.getResultList();
	}
	
	/**
	 * get group campaigns
	 * 
	 * @param groups
	 * @param categories
	 * @return a list of group campaigns
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getGroupCampaignsWithCategories(List<Groups> groups, List<Category> categories) {
		String hql = "select distinct(g.campaign) from GroupCampaign g where (g.campaign.status.code = 'ACTIVE' or g.campaign.status.code = 'COMPLETED' or g.campaign.status.code='IN_PROGRESS') ";
		if (!groups.isEmpty()){
			hql += "and (";
			for (int i =0; i < groups.size(); i++){
				hql += "g.groups.id = " + groups.get(i).getId();
				if (i < groups.size() -1) hql += " or ";
			}
			hql += ")";
		}
		
		if (!categories.isEmpty()){
			hql += "and (";
			for (int i = 0; i < categories.size(); i++){
				hql += "g.campaign.category.id = " + categories.get(i).getId();
				if (i < categories.size()-1) hql += " or ";
			}
			hql += " or g.campaign.category.id is null";
			hql += ") ";
		}
		
		hql += "order by g.campaign.status.id ASC";
		Query query = entityManager.createQuery(hql);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Groups> getGroupsWithCampaign(List<Groups> groups, Long campaignId) {
		String hql = "select distinct(g.groups) from GroupCampaign g where g.campaign.id = :campaignId and (g.campaign.status.code = 'ACTIVE' or g.campaign.status.code = 'COMPLETED' or g.campaign.status.code='IN_PROGRESS') ";
		if (!groups.isEmpty()){
			hql += "and (";
			for (int i =0; i < groups.size(); i++){
				hql += "g.groups.id = " + groups.get(i).getId();
				if (i < groups.size() -1) hql += " or ";
			}
			hql += ")";
		}
		
		hql += "order by g.campaign.status.id ASC";
		Query query = entityManager.createQuery(hql);
		query.setParameter("campaignId",  campaignId);
		return (List<Groups>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getUserCampaignsWithCategories(Long accountId, List<Category> categories) {
		String hql = "select distinct(a.campaign) from AccountCampaign a where a.user.id =:accountId and (a.campaign.status.code = 'ACTIVE' or a.campaign.status.code = 'COMPLETED' or a.campaign.status.code='IN_PROGRESS') ";
		
		if (!categories.isEmpty()){
			hql += " and (";
			for (int i = 0; i < categories.size(); i++){
				Category category = categories.get(i);
				hql += "a.campaign.category.id= " + category.getId();
				if (i < (categories.size()-1)) hql += " or ";
				
			}
			hql += " or a.campaign.category.id is null";
			hql += ") "; 
		}
		
		hql += "order by a.campaign.status.id ASC";
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("accountId",  accountId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getUserActiveCampaignsWithCategories(Long accountId, List<Category> categories) {
		String hql = "select a.campaign from AccountCampaign a where a.user.id =:accountId and (a.campaign.status.code = 'ACTIVE' or a.campaign.status.code='IN_PROGRESS') ";
		
		if (!categories.isEmpty()){
			hql += " and (";
			for (int i = 0; i < categories.size(); i++){
				Category category = categories.get(i);
				hql += "a.campaign.category.id= " + category.getId();
				if (i < (categories.size()-1)) hql += " or ";
				
			}
			hql += " or a.campaign.category.id is null";
			hql += ") "; 
		}
		
		hql += "order by a.campaign.status.id ASC";
		
		Query query = entityManager.createQuery(hql);
		query.setParameter("accountId",  accountId);
		return (List<Campaign>) query.getResultList();
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getUserCampaigns(Long accountId) {
		Query query = entityManager.createQuery("select a.campaign from AccountCampaign a where a.user.id =:accountId and (a.campaign.status.code = 'ACTIVE' or a.campaign.status.code = 'COMPLETED' or a.campaign.status.code='IN_PROGRESS') order by a.campaign.status.id ASC");
		query.setParameter("accountId",  accountId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getUserActiveCampaigns(Long accountId, Long categoryId) {
		Query query = entityManager.createQuery("select a.campaign from AccountCampaign a where a.user.id =:accountId and a.campaign.category.id = :categoryId and (a.campaign.status.code = 'ACTIVE' or a.campaign.status.code='IN_PROGRESS') order by a.campaign.status.id ASC");
		query.setParameter("accountId",  accountId);
		query.setParameter("categoryId",  categoryId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getGroupActiveCampaigns(Long groupId, Long categoryId) {
		Query query = entityManager.createQuery("select g.campaign from GroupCampaign g where g.groups.id =:groupId and g.campaign.campaignType.type = 'GROUP' and g.campaign.category.id = :categoryId and (g.campaign.status.code = 'ACTIVE' or g.campaign.status.code='IN_PROGRESS') order by g.campaign.status.id ASC");
		query.setParameter("groupId",  groupId);
		query.setParameter("categoryId",  categoryId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Campaign> getAllCampaigns(Long communityId, Long categoryId, Long segmentId) {

		Query query = entityManager.createQuery(
				"select p from Campaign p where ((p.segment.id is NULL and p.communityId = :communityId ) or (p.segment is not null and p.segment.id = :segmentId)) and p.category.id = :categoryId and (p.status.code = 'ACTIVE' or p.status.code='IN_PROGRESS')");
		query.setParameter("communityId", communityId);
		query.setParameter("categoryId", categoryId);
		query.setParameter("segmentId", segmentId);
		return (List<Campaign>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<AccountCampaign> getAccountCampaignsByGroupId(Long groupId) {
		Query query = entityManager.createQuery("select a from AccountCampaign a where a.groups.id =:groupId");
		query.setParameter("groupId",  groupId);
		return (List<AccountCampaign>) query.getResultList();
	}
	
	//************************************************End of User***************************************************************************
		
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<MediaStream> findMediaStream(String streamCode, Long communityId) {
		Query query = entityManager.createQuery("select t from MediaStream t where t.streamCode=:streamCode and t.communityId = :communityId");
		query.setParameter("streamCode",  streamCode);
		query.setParameter("communityId",  communityId);
		return (List<MediaStream>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<MediaScreenList> findMediaScreenList(Long streamId) {
		Query query = entityManager.createQuery("select t from MediaScreenList t where t.streamId=:streamId order by screenOrder ASC");
		query.setParameter("streamId",  streamId);
		return (List<MediaScreenList>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<MediaScreenList> findMediaScreenList(Long streamId, String screenType, Integer screenOrder) {
		Query query = entityManager.createQuery("select t from MediaScreenList t where t.streamId=:streamId and t.mediaScreen.mediaScreenType.type = :screenType and t.screenOrder = :screenOrder order by screenOrder ASC");
		query.setParameter("streamId",  streamId);
		query.setParameter("screenType",  screenType);
		query.setParameter("screenOrder",  screenOrder);
		return (List<MediaScreenList>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<MediaScreenList> findMediaScreenList(Long streamId, Long screenId) {
		Query query = entityManager.createQuery("select t from MediaScreenList t where t.streamId=:streamId and t.mediaScreen.id = :screenId order by screenOrder ASC");
		query.setParameter("streamId",  streamId);
		query.setParameter("screenId",  screenId);
		return (List<MediaScreenList>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<MediaScreen> findMediaScreen(String screenURL) {
		Query query = entityManager.createQuery("select ms from MediaScreen ms where ms.url = :screenURL");
		query.setParameter("screenURL",  screenURL);
		return (List<MediaScreen>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<MediaScreenAttributeValue> findMediaScreenAttributeValues(Long screenListId) {
		Query query = entityManager.createQuery("select t from MediaScreenAttributeValue t where t.mediaScreenList.id = :screenListId");
		query.setParameter("screenListId",  screenListId);
		return (List<MediaScreenAttributeValue>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Tweet> findTweet(Long communityId) {
		Query query = entityManager.createQuery("select t from Tweet t where t.communityId = :communityId");
		query.setParameter("communityId",  communityId);
		return (List<Tweet>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<AccountSource> findAccountSourceBYAccountAndSource(Long accountId, Long sourceId) {
		Query query = entityManager.createQuery("select a from AccountSource a where a.user.id = :accountId and a.source.id = :sourceId");
		query.setParameter("accountId",  accountId);
		query.setParameter("sourceId",  sourceId);
		return (List<AccountSource>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Source findSourceBySourceCode(String sourceCode) {
		Query query = entityManager.createQuery("select s from Source s where s.code = :sourceCode");
		query.setParameter("sourceCode",  sourceCode);
		try {
			return (Source)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Source findSourceBySourceCodeAndType(String sourceCode, String typeCode) {
		Query query = entityManager.createQuery("select s from Source s where s.code = :sourceCode and s.sourceType.code = :typeCode");
		query.setParameter("sourceCode",  sourceCode);
		query.setParameter("typeCode",  typeCode);
		try {
			return (Source)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public SourceUnit findSourceUnitByType(String sourceType) {
		Query query = entityManager.createQuery("select s from SourceUnit s where s.sourceType.code = :sourceType");
		query.setParameter("sourceType",  sourceType);
		try {
			return (SourceUnit)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<ElectricityDataMinute> findElectricityDataBySource(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select e from ElectricityDataMinute e where e.source.id = :sourceId and e.created between :startDate and :endDate order by created ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return (List<ElectricityDataMinute>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public ElectricityDataMinute findLatestElectricityDataBySource(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select e from ElectricityDataMinute e where e.source.id = :sourceId and e.created between :startDate and :endDate order by created DESC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		query.setMaxResults(1);
		try {
			return (ElectricityDataMinute) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<MediaImage> findMediaImages(Long communityId, Long sourceTypeId, Long imageTypeId, String locale) {
		//Query query = entityManager.createQuery("select m from MediaImage m where m.communityId = -1 and m.locale= :locale and m.sourceType.id = :sourceTypeId and m.imageTypeId = :imageTypeId order by displayOrder ASC");
		
		Query query = entityManager.createQuery(
				
				"select m from MediaImage m where m.community.id = :communityId and m.locale= :locale and m.sourceType.id = :sourceTypeId and m.imageTypeId = :imageTypeId "+
				"or m.id in ("+
				//locale is en_CA and community is not NULL
				"select mi.id from MediaImage mi where mi.community.id = :communityId and mi.locale='en_CA' and mi.sourceType.id = :sourceTypeId and mi.imageTypeId = :imageTypeId and mi.displayOrder not in (" +
					"select displayOrder from MediaImage where community.id = :communityId and locale= :locale and sourceType.id = :sourceTypeId and imageTypeId = :imageTypeId)) "+
				"or m.id in ("+
				//locale is en_CA community is Null
				"select mi.id from MediaImage mi where mi.community.id is NULL and mi.locale='en_CA' and mi.sourceType.id= :sourceTypeId and mi.imageTypeId = :imageTypeId and mi.displayOrder not in ("+
					"select m3.displayOrder from MediaImage m3 where m3.community.id = :communityId and m3.locale= :locale and m3.sourceType.id = :sourceTypeId and m3.imageTypeId = :imageTypeId  "+
						"or m3.id in ("+
							"select mi.id from MediaImage mi where mi.community.id = :communityId and mi.locale='en_CA' and mi.sourceType.id = :sourceTypeId and mi.imageTypeId = :imageTypeId and mi.displayOrder not in (" +
								"select displayOrder from MediaImage where community.id = :communityId and locale= :locale and sourceType.id = :sourceTypeId and imageTypeId = :imageTypeId)))) "+	
				"order by displayOrder ASC");
			
		query.setParameter("communityId",  communityId);
		query.setParameter("sourceTypeId",  sourceTypeId);
		query.setParameter("imageTypeId",  imageTypeId);
		query.setParameter("locale",  locale);
		return (List<MediaImage>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Integer findPrintDataSum(Long sourceId, Date startDate, Date endDate) {
		Query query = entityManager.createQuery("select sum(p.paperUsage) from PrintDataMinute p where p.source.id = :sourceId and p.created between :startDate and :endDate order by created ASC");
		query.setParameter("sourceId",  sourceId);
		query.setParameter("startDate",  startDate);
		query.setParameter("endDate",  endDate);
		return Integer.parseInt(query.getSingleResult().toString());
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Factor findFactor(Long sourceTypeId, Long attributeId) {
		Query query = entityManager.createQuery("select f from Factor f where f.sourceType.id = :sourceTypeId and f.factorAttribute.id = :attributeId");
		query.setParameter("sourceTypeId",  sourceTypeId);
		query.setParameter("attributeId",  attributeId);
		return (Factor) query.getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Source> findSourceByAccount(Long accountId) {
		Query query = entityManager.createQuery("select a.source from AccountSource a where a.user.id = :accountId order by a.source.id ASC");
		query.setParameter("accountId",  accountId);
		return (List<Source>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Source> findDefaultSourceByAccountAndType(Long accountId, String sourceType) {
		Query query = entityManager.createQuery("select a.source from AccountSource a where a.user.id = :accountId and a.source.sourceType.code = :sourceType and a.status.code = 'ACTIVE' and a.defaultSource = true order by a.source.id ASC");
		query.setParameter("accountId",  accountId);
		query.setParameter("sourceType",  sourceType);
		return (List<Source>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public Source findMasterSourceByAccountAndType(Long memberId, String sourceType) {
		Query query = entityManager.createQuery("select a.source from AccountSource a where a.user.id = :memberId and a.source.sourceType.code = :sourceType and a.status.code = 'ACTIVE' and a.source.sourceMode.code = 'MASTER' order by a.source.id ASC");
		query.setParameter("memberId",  memberId);
		query.setParameter("sourceType",  sourceType);
		try{
			return (Source) query.getResultList().get(0);
		}catch(Exception e){
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<AccountSource> findAccountSourceByAccountAndType(Long accountId, String sourceType) {
		Query query = entityManager.createQuery("select a from AccountSource a where a.user.id = :accountId and a.source.sourceType.code = :sourceType and a.status.code = 'ACTIVE' order by a.source.id ASC");
		query.setParameter("accountId",  accountId);
		query.setParameter("sourceType",  sourceType);
		return (List<AccountSource>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Source> findSourceByAccountAndType(Long accountId, String sourceType) {
		Query query = entityManager.createQuery("select a.source from AccountSource a where a.user.id = :accountId and a.source.sourceType.code = :sourceType and a.status.code = 'ACTIVE' order by a.source.id ASC");
		query.setParameter("accountId",  accountId);
		query.setParameter("sourceType",  sourceType);
		return (List<Source>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<Source> findAllSourceByAccountAndType(Long accountId, String sourceType) {
		Query query = entityManager.createQuery("select a.source from AccountSource a where a.user.id = :accountId and a.source.sourceType.code = :sourceType order by a.source.id ASC");
		query.setParameter("accountId",  accountId);
		query.setParameter("sourceType",  sourceType);
		return (List<Source>) query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public AccountSource findAccountSource(Long accountId, Long sourceId) {
		Query query = entityManager.createQuery("select a from AccountSource a where a.user.id = :accountId and a.source.id = :sourceId");
		query.setParameter("accountId",  accountId);
		query.setParameter("sourceId",  sourceId);
		try {
			return (AccountSource)query.getResultList().get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public void deleteAccountSource(Long accountSourceId) {
		Query query = entityManager.createQuery("delete from AccountSource a where a.id = :accountSourceId");
		query.setParameter("accountSourceId",  accountSourceId);
		query.executeUpdate();
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public Class<T> getType() {
		return this.type;
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { IllegalArgumentException.class })
	public T findById(long id) {
		T object = (T) entityManager.find(type, id);
		if (object == null) {
			return null;
			//throw new IllegalArgumentException(String.format("Can't find domain object (%s) for id : %s", type.getName(), id));
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<T> findById(List<Long> ids) {

		Query query = entityManager.createQuery("select o from " + type.getName() + " o where o.userId IN( :ids ) ").setParameter("ids", ids);
		return (List<T>) query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<T> getSiteAndPlants(long userId) {

		Query query = entityManager.createQuery("select p from plantsitedetail p");
		// where p.userId = :userId.setParameter("userId", userId );
		return (List<T>) query.getResultList();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T save(T obj) {

		try {
			entityManager.merge(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public T save(T obj, boolean flush) {

		try {
			entityManager.merge(obj);
			if (flush) {
				entityManager.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void remove(T obj) {
		entityManager.remove(obj);
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public List<T> findByUserName(List<String> names) {
		if (names == null) {
			throw new NoResultException(String.format("No entity %s found for query "));
		}
		Query query = entityManager.createQuery("select o from " + type.getName() + " o where o.userName IN (:names) ").setParameter("name", names);
		return (List<T>) query.getResultList();

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public T findByName(String name, Long communityId, Integer mode) {
		if (name == null) {
			throw new NoResultException(String.format("No entity %s found for query email='%s'", type.getName(), name));
		}
		Query query = entityManager.createQuery("select o from " + type.getName() + " o where o.email = :email and o.defaultCommunity = :communityId and o.mode = :mode and o.status.code = 'ACTIVE'");
		query.setParameter("email", name);
		query.setParameter("communityId", communityId);
		query.setParameter("mode", mode);
		T object = (T) query.getSingleResult();
		return object;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public T findByPasswordResetCode(String email, String code) {
		if (code == null) {
			throw new NoResultException(String.format("No entity %s found for query passwordResetCode='%s'", type.getName(), code));
		}
		Query query = entityManager.createQuery("select o from " + type.getName() + " o where o.email = :email and o.passwordResetCode = :code and o.status.code = 'ACTIVE'");
		query.setParameter("code", code);
		query.setParameter("email", email);
		T object = (T) query.getSingleResult();
		return object;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public T findUserForLogin(String name, Integer mode) {
		if (name == null) {
			throw new NoResultException(String.format("No entity %s found for query email='%s'", type.getName(), name));
		}
		Query query = entityManager.createQuery("select o from " + type.getName() + " o, Account_Authority aa where o.id = aa.user.id and aa.userRole.authority='USER' and o.email = :email and o.mode = :mode and o.status.code = 'ACTIVE'");
		query.setParameter("email", name);
		query.setParameter("mode", mode);
		T object = (T) query.getSingleResult();
		return object;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public T findByNameCommunity(String name) {
		if (name == null) {
			throw new NoResultException(String.format("No entity %s found for query email='%s'", type.getName(), name));
		}
		Query query = entityManager.createQuery("select o from " + type.getName() + " o where o.email = :email");
		query.setParameter("email", name);
		T object = (T) query.getSingleResult();
		return object;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public T findByNameCommunityForLogin(String email) {
		if (email == null) {
			throw new NoResultException(String.format("No entity %s found for query email='%s'", type.getName(), email));
		}
		Query query = entityManager.createQuery("select o from " + type.getName() + " o, Account_Authority aa where o.mode = 1 and o.id = aa.user.id and aa.userRole.authority='USER' and o.email = :email");
		query.setParameter("email", email);
		T object = (T) query.getSingleResult();
		return object;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public T findByTitle(String name) {
		Query query = entityManager.createQuery("select o from " + type.getName() + " o where o.title = :title ").setParameter("title", name);
		T object = (T) query.getSingleResult();
		return object;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeById(long id) {
		entityManager.remove(entityManager.getReference(type, id));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Set<T> save(Collection<T> objects) {
		System.out.println("********saving dao******");
		try {
			Set<T> result = new HashSet<T>();
			for (T obj : objects) {
				result.add(entityManager.merge(obj));

			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();

		}
		return new HashSet<T>();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.EntityNotFoundException.class })
	public List<T> loadAll() {
		Query query = entityManager.createQuery("select distinct o from " + type.getName() + " o ");
		return (List<T>) query.getResultList();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void refresh(T obj) {
		entityManager.refresh(obj);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void flush() {
		entityManager.flush();
	}

	public void deleteById(Long id) {
		System.out.println("firing query");
		try {
			Query query = entityManager.createQuery("delete  from " + type.getName() + " o where o.id = :id ").setParameter("id", id);
			query.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS, noRollbackFor = { javax.persistence.NoResultException.class })
	public boolean checkName(String name) {
		if (name == null) {
			throw new NoResultException(String.format("No entity %s found for query name='%s'", type.getName(), name));
		}
		Query query = entityManager.createQuery("select o from " + type.getName() + " o where o.name = :name ").setParameter("name", name);
		if (query.getResultList().size() == 0) {
			return true;
		}
		return false;
	}


	public String getCharacterMaximumLength(String schemaName, String tableName, String columnName) {
		
		if (schemaName==null || tableName==null || columnName==null) return null;
		
		//CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		javax.persistence.Query query = entityManager.createNativeQuery("select CHARACTER_MAXIMUM_LENGTH from information_schema.COLUMNS t where " +
				"t.TABLE_SCHEMA=:schemaName and t.TABLE_NAME=:tableName and t.COLUMN_NAME=:columnName");
		query.setParameter("schemaName", schemaName);
		query.setParameter("tableName", tableName);
		query.setParameter("columnName", columnName);
		return query.getSingleResult().toString();
		
	}

}
