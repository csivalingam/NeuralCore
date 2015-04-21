package net.zfp.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.NoResultException;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.Domain;
import net.zfp.entity.News;
import net.zfp.entity.PortalType;
import net.zfp.entity.Source;
import net.zfp.entity.SourceType;
import net.zfp.entity.User;
import net.zfp.entity.carbon.CarbonFootprint;
import net.zfp.entity.tips.Tips;
import net.zfp.service.NewsService;
import net.zfp.util.AppConstants;
import net.zfp.view.NewsView;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class NewsServiceImpl implements NewsService {

	
	@Resource
	private EntityDao<Tips> tipDao;
	@Resource
	private EntityDao<Domain> domainDao;
	@Resource
	private EntityDao<User> userDao;
	@Resource
	private EntityDao<News> newsDao;
	@Resource
	private EntityDao<CarbonFootprint> carbonFootprintDao;
	@Resource
	private EntityDao<PortalType> portalTypeDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public NewsView getLatestNews(String domainName, String portalName){
		//Get communityId
				Long communityId = getCommunityId(domainName);
				
				Long portalTypeId = null;
				if (portalName != null) portalTypeId = portalTypeDao.findPortalTypeListId(portalName);
				
				List<News> newses = newsDao.getNews(communityId, portalTypeId);
				List<NewsView> nvs = new ArrayList<NewsView>();
				
				if (newses.size()>0){
					News news = newses.get(0);
					NewsView nv = new NewsView();
					nv.setCreated(news.getCreated());
					nv.setHeader(news.getHeader());
					nv.setDescription(news.getDescription());
					
					return nv;
				}
				return null;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<NewsView> getNews(String domainName, String portalName){
		
		//Get communityId
		Long communityId = getCommunityId(domainName);
		
		Long portalTypeId = null;
		if (portalName != null) portalTypeId = portalTypeDao.findPortalTypeListId(portalName);
		
		List<News> newses = newsDao.getNews(communityId, portalTypeId);
		List<NewsView> nvs = new ArrayList<NewsView>();
		
		for(News news :newses){
			NewsView nv = new NewsView();
			nv.setCreated(news.getCreated());
			nv.setHeader(news.getHeader());
			nv.setDescription(news.getDescription());
			
			nvs.add(nv);
		}
		return nvs;
	}
	
	private Long getCommunityId(String serverName){
		Long communityId = null;
		try {
			communityId = domainDao.getCommunitId(serverName);
		}catch (NoResultException ne){
			System.out.println("************ Community no found.");
		}catch (Exception e) {
			System.out.println("************ Get error in MediaStreamServiceImpl for Community. Error: " + e);
		}
		
		return communityId;
	}
}
