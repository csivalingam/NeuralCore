package net.zfp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.banner.MarketingBanner;
import net.zfp.entity.banner.MarketingBannerType;
import net.zfp.entity.banner.ProgramBanner;
import net.zfp.entity.community.Community;
import net.zfp.view.BannerView;
import net.zfp.view.DeviceView;
import net.zfp.view.ProgramBannerView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


public class BannerServiceImpl implements BannerService {

	@Resource
    private SegmentService segmentService;
	@Resource
    private CommunityService communityService;
	
	@Resource
	private EntityDao<MarketingBanner> marketingBannerDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<BannerView> getMarketingBanners(String domainName, String bannerType, Integer limit){
		System.out.println("Get Marketing Banner : " + new Date());
		List<BannerView> bvs = new ArrayList<BannerView>();
		
		List<Community> communities = communityService.getMyCommunities(domainName);
		
		if (!communities.isEmpty() && bannerType != null){
			
			//Get BannerType
			MarketingBannerType mbt = marketingBannerDao.getMarketingBannerType(bannerType);
			
			int counter = 0;
			for (Community community : communities){
				System.out.println("Banner community : " + community.getId() + " : "+ communities.size() + " : " + mbt.getId());
				//For each community get MarketingBanner
				List<MarketingBanner> mbs = marketingBannerDao.getMarketingBanner(community.getId(), mbt.getId());
				
				if (!mbs.isEmpty()){
					for (MarketingBanner mb : mbs){
						
						if (counter >= limit) break;
						
						System.out.println("Marketing banner : " + mb.getName() + " : " + mbs.size());
						BannerView bv = new BannerView(mb);
						bvs.add(bv);
						
						counter++;
					}
				}
			}
		}
		
		return bvs;
	}
	
	/**
	 * 
	 * Returns limited amount of Program Banner Views for a specified community.
	 * @param domainName
	 * @param limit
	 * @Author Youngwook Yoo
	 * @return
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ProgramBannerView> getProgramBanners(String domainName, Integer limit){
		System.out.println("Get Program Banners : " + new Date());
		List<ProgramBannerView> pbvs = new ArrayList<ProgramBannerView>();
		
		List<Community> communities = communityService.getMyCommunities(domainName);
		
		List<ProgramBanner> pbs = new ArrayList<ProgramBanner>();
		if (!communities.isEmpty()){
			for (Community community : communities){
				pbs.addAll(marketingBannerDao.getProgramBanner(community.getId()));
				
				if (pbs.size() >= limit) break;
			}
		}
		
		if (!pbs.isEmpty()){
			//Comparator device views rank
			Comparator<ProgramBanner> comparator = new Comparator<ProgramBanner>(){
				public int compare(ProgramBanner deviceView1, ProgramBanner deviceView2){
					return deviceView1.getRank().compareTo(deviceView2.getRank());
				}
			};
			Collections.sort(pbs, comparator);
			
			
			//Here we want the community first
			List<ProgramBanner> mycommunityProgramBannerView = new ArrayList<ProgramBanner>();
			List<ProgramBanner> nonmycommunityProgramBannerView = new ArrayList<ProgramBanner>();
			
			for (ProgramBanner pb : pbs){
				if (pb.getCommunity().equals(communities.get(0))) mycommunityProgramBannerView.add(pb);
				else nonmycommunityProgramBannerView.add(pb);
			}
			
			pbs.clear();
			pbs.addAll(mycommunityProgramBannerView);
			pbs.addAll(nonmycommunityProgramBannerView);
			
			for (ProgramBanner pb : pbs){
				ProgramBannerView pbv = new ProgramBannerView(pb);
				pbvs.add(pbv);
				if (pbvs.size() >= limit) break;
			}
		}
		
		return pbvs;
	}
	
}
