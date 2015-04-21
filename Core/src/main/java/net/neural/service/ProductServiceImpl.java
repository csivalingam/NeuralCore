package net.zfp.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import net.zfp.dao.EntityDao;
import net.zfp.entity.User;
import net.zfp.entity.category.Category;
import net.zfp.entity.category.MerchandisingCategory;
import net.zfp.entity.community.Community;
import net.zfp.entity.community.CommunityProduct;
import net.zfp.entity.offer.Offer;
import net.zfp.entity.offer.OfferBehavior;
import net.zfp.entity.product.Product;
import net.zfp.entity.product.ProductBanner;
import net.zfp.entity.product.ProductSKU;
import net.zfp.entity.segment.Segment;
import net.zfp.service.ProductService;
import net.zfp.service.UtilityService;
import net.zfp.util.AppConstants;
import net.zfp.util.TextUtil;
import net.zfp.view.MerchandisingCategoryView;
import net.zfp.view.MerchandisingCategoryViews;
import net.zfp.view.NewsContentView;
import net.zfp.view.OfferView;
import net.zfp.view.ProductBannerView;
import net.zfp.view.ProductBannerViews;
import net.zfp.view.ProductListView;
import net.zfp.view.ProductView;
import net.zfp.view.ResultView;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Resource
public class ProductServiceImpl implements ProductService {
	@Resource
    private UtilityService utilityService;
	@Resource
    private CommunityService communityService;
	@Resource
	private EntityDao<Community> communityDao;
	
	@Resource
	private EntityDao<Category> categoryDao;
	
	@Resource
	private EntityDao<Product> productDao;
	
	@Resource
	private EntityDao<Offer> offerDao;
	
	@Resource
	private EntityDao<User> userDao;
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ProductView> getConnectorProductsByCategoryCode(Integer code){
		List<ProductView> pvs = new ArrayList<ProductView>();
		
		List<String> UPCs = null;
		switch(code){
		case 1:
			//Electricity
			UPCs = AppConstants.PRODUCT_CONNECTOR_ELECTRICITY;
			break;
		case 2:
			//Natural Gas
			UPCs = AppConstants.PRODUCT_CONNECTOR_NATURAL_GAS;
			break;
		case 3:
			//Water
			UPCs = AppConstants.PRODUCT_CONNECTOR_WATER;
			break;
		case 4:
			//HEALTH
			UPCs = AppConstants.PRODUCT_CONNECTOR_PERSONAL_MOTION;
			break;
		case 5:
			//DRIVING
			UPCs = AppConstants.PRODUCT_CONNECTOR_DRIVING;
			break;
			
		}
		
		System.out.println("UPC SIZE :: " + UPCs.size());
		if (UPCs != null && UPCs.size() > 0){
			for (String UPC : UPCs){
				Product product = productDao.getProductByUPC(UPC);
				if ( product != null){
					
					product.setName(TextUtil.parseString(product.getName()));
					product.setShortDescription(TextUtil.parseString(product.getShortDescription()));
					product.setLongDescription(TextUtil.parseString(product.getLongDescription()));
					product.setLargeImageUrl(TextUtil.parseString(product.getLargeImageUrl()));
					product.setSmallImageUrl(TextUtil.parseString(product.getSmallImageUrl()));
					ProductView pv = new ProductView(product);
					//Check if it has buy and get an offer
					//Offer Behaviour
					Offer offer = offerDao.getProductOffer(UPC, AppConstants.BEHAVIOR_ATTRIBUTE_UPC, AppConstants.OFFER_TYPE_BUY_AND_GET);
					if (offer != null) pv.setOfferPoints(offer.getValue());
					pvs.add(pv);
				}
			}
			
		}
		System.out.println("PVS SIZE :: " + pvs.size());
		return pvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ProductView getProductByUPC(String UPC){
		ProductView pv = new ProductView();
		ResultView rv = new ResultView();
		if (UPC == null){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			pv.setResult(rv);
			return pv;
		}
		
		Product product = productDao.getProductByUPC(UPC);
		if (product != null){
			product.setName(TextUtil.parseString(product.getName()));
			product.setShortDescription(TextUtil.parseString(product.getShortDescription()));
			product.setLongDescription(TextUtil.parseHTMLString(product.getLongDescription()));
			pv = new ProductView(product);
			if (pv.getUnitPoints() == null) pv.setUnitPoints(0);
			if (pv.getUnitPrice() == null) pv.setUnitPrice(0.0);
		}else{
			rv.fill(AppConstants.FAILURE, "UPC is not valid");
			pv.setResult(rv);
		}
			
		return pv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ProductView getProductById(Long productId){
		ProductView pv = null;
		Product product = productDao.getProduct(productId);
		
		
		if (product != null){
			product.setMobileSmallImageUrl(AppConstants.APACHE_IMAGE_LINK + product.getMobileSmallImageUrl());
			product.setMobileLargeImageUrl(AppConstants.APACHE_IMAGE_LINK + product.getMobileLargeImageUrl());
			product.setName(TextUtil.parseString(product.getName()));
			product.setShortDescription(TextUtil.parseString(product.getShortDescription()));
			product.setLongDescription(TextUtil.parseString(product.getLongDescription()));
			pv = new ProductView(product);
		}
			
		return pv;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ProductView> getProductsByCategoryCode(String domainName, String categoryCode, String productType){
		List<ProductView> pvs = new ArrayList<ProductView>();
		
		System.out.println("ProductCategoryCode");
		Category category = categoryDao.getCategoryByCode(categoryCode, AppConstants.CATEGORYTYPE_PRODUCT);
		List<Community> communities = communityService.getMyCommunities(domainName);
		
		if(category != null){
			//Check each for product category
			List<Product> products = productDao.getProductByCategoryAndCommunity(category.getId(), productType, communities);
			
			if(products != null){
				for (Product product: products){
					product.setName(TextUtil.parseString(product.getName()));
					product.setShortDescription(TextUtil.parseString(product.getShortDescription()));
					product.setLongDescription(TextUtil.parseString(product.getLongDescription()));
					product.setProductBenefits(TextUtil.parseString(product.getProductBenefits()));
					ProductView pv = new ProductView(product);
					List<ProductSKU> productSKUs = productDao.getProductSKUByProduct(product.getId()); 
					//For each product sku
					if (productSKUs != null && productSKUs.size() > 0){
						pv.setSku(productSKUs.get(0).getSKUId());
					}else{
						pv.setSku("");
					}
					pvs.add(pv);
				}
			}
		}
		return pvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ProductListView getTopCatalogue(Long memberId, String domainName, String productType, Integer topNumber){
		ProductListView plv = new ProductListView();
		
		System.out.println(memberId + " : " + domainName + " : " + productType + " : " + topNumber);
		
		ResultView rv = new ResultView();
		if (topNumber == null || productType == null || (memberId == null && domainName == null)){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			plv.setResult(rv);
			return plv;
		}
		
		List<Community> communities = null;
		
		if (memberId != null){
			User user = userDao.findById(memberId);
			if (user != null){
				communities = communityService.getMyCommunitiesById(memberId);
			}else{
				if (memberId != null && domainName == null){
					rv.fill(AppConstants.FAILURE, "Invalid member");
					plv.setResult(rv);
					return plv;
				}
			}
		}
		
		if (communities == null){
			communities = communityService.getMyCommunities(domainName);
			if (communities == null){
				rv.fill(AppConstants.FAILURE, "Invalid domain");
				plv.setResult(rv);
				return plv;
			}
		}
		
		List<Product> products = productDao.getTopNumberProductByCommunity(productType, communities, topNumber);
		
		List<ProductView> productViewList = new ArrayList<ProductView>();
		
		if (!products.isEmpty()){
			
			for (Product product : products){
				
				product.setName(TextUtil.parseHTMLString(product.getName()));
				product.setShortDescription(TextUtil.parseHTMLString(product.getShortDescription()));
				product.setLongDescription(TextUtil.parseString(product.getLongDescription()));
				
				ProductView pv = new ProductView(product);
				List<ProductSKU> productSKUs = productDao.getProductSKUByProduct(product.getId()); 
				//For each product sku
				if (productSKUs != null && productSKUs.size() > 0){
					pv.setSku(productSKUs.get(0).getSKUId());
				}else{
					pv.setSku("");
				}
				
				//Check if a product has an offer
				OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_BUY, AppConstants.BEHAVIOR_TRACKERTYPE_UPC);
				if (ob != null){
					Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), product.getUPC(), new Date());
					if (offer != null){
						OfferView offerView = new OfferView(offer);
						pv.setOffer(offerView);
					}
				}
				
				productViewList.add(pv);
				
			}
			
			plv.setProductViews(productViewList);
		}
		
		return plv;
	}
	
	/**
	 * See documentations in net.zfp.service.getProductListByMerchandiseCategory
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ProductListView getProductListByMerchandiseCategory(Long memberId, String domainName, Integer merchandisingCategory, String productType, Integer page, Integer limit, String sortBy){
		ProductListView plv = new ProductListView();
		
		ResultView rv = new ResultView();
		if (merchandisingCategory == null || productType == null || page == null || limit == null || (memberId == null && domainName == null)){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			plv.setResult(rv);
			return plv;
		}
		
		List<Community> communities = null;
		
		if (memberId != null){
			User user = userDao.findById(memberId);
			if (user != null){
				communities = communityService.getMyCommunitiesById(memberId);
			}else{
				if (memberId != null && domainName == null){
					rv.fill(AppConstants.FAILURE, "Invalid member");
					plv.setResult(rv);
					return plv;
				}
			}
		}
		
		if (communities == null){
			communities = communityService.getMyCommunities(domainName);
			if (communities == null){
				rv.fill(AppConstants.FAILURE, "Invalid domain");
				plv.setResult(rv);
				return plv;
			}
		}
		
		String sort = "HTL";
		if (sortBy == null || sortBy.equals("HTL")) sort = "unitPrice DESC";
		else if (sortBy.equals("LTH")) sort = "unitPrice ASC";
		
		List<Product> totalProducts = new ArrayList<Product>();
		if (merchandisingCategory > 0)
			totalProducts = productDao.getAllProductByMerchandisingCategoryAndCommunity(merchandisingCategory, productType, communities, sort);
		else{
			OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_BUY, AppConstants.BEHAVIOR_TRACKERTYPE_UPC);
			//Check if any upc 
			totalProducts = productDao.getOfferedProducts(ob.getId(), productType, communities, sort);
		}
		
		Integer productCounts = 0;
		if (totalProducts != null) productCounts = totalProducts.size();
		
		//Check how many items are there..
		plv.setTotalProduct(productCounts);
		
		if (page <= 0 ) page = 1;
		
		
		//Check how many pages are there..
		int totalPage = (int)Math.ceil((double)productCounts/(double)limit);
		if (totalPage > 0)
			plv.setTotalPage(totalPage);
		else
			plv.setTotalPage(1);
		
		plv.setCurrentPage(page);
		
		if (page > totalPage) page = totalPage;
				
		List<ProductView> productViewList = new ArrayList<ProductView>();
		
		if (!totalProducts.isEmpty()){
			//Get right pages
			for (int i= ((page-1) * limit); i< (page * limit); i++){
				if (i <totalProducts.size()){
					Product product = totalProducts.get(i);
					
					product.setName(TextUtil.parseHTMLString(product.getName()));
					product.setShortDescription(TextUtil.parseHTMLString(product.getShortDescription()));
					product.setLongDescription(TextUtil.parseString(product.getLongDescription()));
					ProductView pv = new ProductView(product);
					List<ProductSKU> productSKUs = productDao.getProductSKUByProduct(product.getId()); 
					//For each product sku
					if (productSKUs != null && productSKUs.size() > 0){
						pv.setSku(productSKUs.get(0).getSKUId());
					}else{
						pv.setSku("");
					}
					
					//Check if a product has an offer
					OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_BUY, AppConstants.BEHAVIOR_TRACKERTYPE_UPC);
					if (ob != null){
						Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), product.getUPC(), new Date());
						if (offer != null){
							OfferView offerView = new OfferView(offer);
							pv.setOffer(offerView);
						}
					}
					
					productViewList.add(pv);
				}
				else break;
			}
		}else{
			rv.fill(AppConstants.FAILURE, "Currently products are empty");
			plv.setResult(rv);
			return plv;
		}
		
		plv.setProductViews(productViewList);
		
		return plv;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ProductView> getFeaturedProducts(String domainName, String productType, Long memberId){
		List<ProductView> pvs = new ArrayList<ProductView>();
		
		List<Community> communities = null;
		if (memberId != null){
			communities = communityService.getMyCommunitiesById(memberId);
		}else{
			communities = communityService.getMyCommunities(domainName);
		}
		
		//Need to get BUY AND GET OFFER products...
		
		String sort = "HTL";
		
		//GET BUY AND GET Offer behavior id
		OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_BUY, AppConstants.BEHAVIOR_TRACKERTYPE_UPC);
		//Check if any upc 
		List<Product> products = productDao.getOfferedProducts(ob.getId(), productType, communities, sort);
		
		if(products != null){
			System.out.println("Product size : " + products.size());
			for (Product product: products){
				product.setName(TextUtil.parseString(product.getName()));
				product.setShortDescription(TextUtil.parseString(product.getShortDescription()));
				product.setLongDescription(TextUtil.parseString(product.getLongDescription()));
				ProductView pv = new ProductView(product);
				List<ProductSKU> productSKUs = productDao.getProductSKUByProduct(product.getId()); 
				//For each product sku
				if (productSKUs != null && productSKUs.size() > 0){
					pv.setSku(productSKUs.get(0).getSKUId());
				}else{
					pv.setSku("");
				}
				pvs.add(pv);
			}
		}
		
		return pvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public ProductBannerViews getProductBanners(String domainName, String productType, Long memberId){
		
		ProductBannerViews pbvs = new ProductBannerViews();
		
		List<ProductBannerView> productBannerViews = new ArrayList<ProductBannerView>();
		ResultView rv = new ResultView();
		
		if (productType == null || (domainName == null && memberId == null)){
			rv.fill(AppConstants.FAILURE, AppConstants.WEB_SERVICE_REPLY_MISSING_PARAMETER);
			pbvs.setResult(rv);
			return pbvs;
		}
		
		List<Community> communities = null;
		
		if (memberId != null){
			User user = userDao.findById(memberId);
			if (user != null){
				communities = communityService.getMyCommunitiesById(memberId);
			}else{
				if (domainName == null){
					rv.fill(AppConstants.FAILURE, "Invalid member");
					pbvs.setResult(rv);
					return pbvs;
				}
			}
		}
		
		if (communities == null){
			communities = communityService.getMyCommunities(domainName);
			if (communities == null){
				rv.fill(AppConstants.FAILURE, "Invalid domain");
				ProductBannerView pbv = new ProductBannerView();
				pbvs.setResult(rv);
				return pbvs;
			}
		}
		List<ProductBanner> productBanners = productDao.getProductBanners(productType, communities);
		
		if (productBanners.isEmpty()){
			rv.fill(AppConstants.FAILURE, "Currently there are no product banners");
			ProductBannerView pbv = new ProductBannerView();
			pbvs.setResult(rv);
		}else{
			for (ProductBanner productBanner : productBanners){
				ProductBannerView pbv = new ProductBannerView(productBanner);
				productBannerViews.add(pbv);
			}
			pbvs.setProductBanners(productBannerViews);
		}
		return pbvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public MerchandisingCategoryViews getMerchandisingCategory(){
		
		MerchandisingCategoryViews mcvs = new MerchandisingCategoryViews();
		
		
		List<MerchandisingCategory> mcs = categoryDao.getMerchandisingCategories();
		
		if (mcs.isEmpty()){
			ResultView rv = new ResultView();
			rv.fill(AppConstants.FAILURE, "Currently there are no merchandising category");
			mcvs.setResult(rv);
			
		}else{
			
			System.out.println("mcs size " + mcs.size());
			List<MerchandisingCategoryView> merchandisingCategoryViews = new ArrayList<MerchandisingCategoryView>();
			for (MerchandisingCategory mc : mcs){
				MerchandisingCategoryView mcv = new MerchandisingCategoryView(mc);
				merchandisingCategoryViews.add(mcv);
				
			}
			
			mcvs.setMerchandisingCategoryViews(merchandisingCategoryViews);
			
		}
		
		return mcvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ProductView> getProductsByCategoryType(String domainName, Integer categoryTypeId, Boolean isCashProduct, String productType, Long memberId){
		//System.out.println("Get Products By Category Type :: " + new Date());
		List<ProductView> pvs = new ArrayList<ProductView>();
		
		List<Category> categories = new ArrayList<Category>();
		Community community = communityDao.getCommunity(domainName);
		Long communityId = community.getId();
		
		Category giftCard = categoryDao.getCategoryByCode(AppConstants.CATEGORY_CODE_GIFT_CARD, 2);
		
		if (isCashProduct){
			categories.add(giftCard);
		}else{
			categories = categoryDao.getCategories(categoryTypeId);
			categories.remove(giftCard);
			
		}
		
		List<Community> communities = null;
		if (memberId != null){
			communities = communityService.getMyCommunitiesById(memberId);
		}else{
			communities = communityService.getMyCommunities(domainName);
		}
		
		if (categories != null){
			for (Category category: categories){
				//Check each for product category
				System.out.println("category  " + category.getId() + " productType : "  + productType);
				List<Product> products = productDao.getProductByCategoryAndCommunity(category.getId(), productType, communities);
				
				if(products != null){
					System.out.println("Product size : " + products.size());
					for (Product product: products){
						product.setName(TextUtil.parseString(product.getName()));
						product.setShortDescription(TextUtil.parseString(product.getShortDescription()));
						product.setLongDescription(TextUtil.parseString(product.getLongDescription()));
						
						ProductView pv = new ProductView(product);
						
						List<ProductSKU> productSKUs = productDao.getProductSKUByProduct(product.getId()); 
						//For each product sku
						if (productSKUs != null && productSKUs.size() > 0){
							pv.setSku(productSKUs.get(0).getSKUId());
						}else{
							pv.setSku("");
						}
						
						//Check if a product has an offer
						OfferBehavior ob = offerDao.getOfferBehavior(AppConstants.BEHAVIOR_ACTION_BUY, AppConstants.BEHAVIOR_TRACKERTYPE_UPC);
						if (ob != null){
							Offer offer = offerDao.getOfferByBehaviorAndAttribute(ob.getId(), product.getUPC(), new Date());
							if (offer != null){
								OfferView offerView = new OfferView(offer);
								pv.setOffer(offerView);
							}
								
						}
						
						pvs.add(pv);
					}
				}
			}
		}
		
		//Sort!\
		Comparator<ProductView> productComparator = new Comparator<ProductView>(){
			public int compare(ProductView product1, ProductView product2){
				//return campaign1.getName().compareTo(campaign2.getName());
				return (product1.getSaleStartDate().compareTo(product2.getSaleEndDate())) * -1;
			}
		};
		
		Collections.sort(pvs, productComparator);
		
		return pvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ProductView> getProducts(String domainName, Long productTypeId, Integer categoryTypeId) {
		
		System.out.println("GET PRODUCT Service");
		List<ProductView> pvs = new ArrayList<ProductView>();
		//Get communityCode
		Community community = communityDao.getCommunity(domainName);
		String communityCode = community.getCode();
		//Get list of sku based on Community Code
		List<CommunityProduct> cps = communityDao.getCommunityProducts(communityCode);
		//get Product list based on Product
		for (CommunityProduct cp : cps){
			
			Product product = productDao.getProduct(cp.getUPC(), productTypeId); 
			if (product != null){
				ProductView pv = new ProductView(product);
				pvs.add(pv);
			
			}
			
		}
		
		
		return pvs;
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public List<ProductView> getRewardsProducts(String communitycode, Integer limit, Integer page){
		System.out.println("GET Rewards Service");
		List<ProductView> pvs = new ArrayList<ProductView>();
				
				//Check each for product category
				List<Product> products = productDao.getProductByTypeWithOffset(AppConstants.PRODUCT_REWARDS, limit, (page-1));
				
				if(products != null){
					System.out.println(products.size());
					for (Product product: products){
						product.setMobileSmallImageUrl(AppConstants.APACHE_IMAGE_LINK + product.getMobileSmallImageUrl());
						product.setMobileLargeImageUrl(AppConstants.APACHE_IMAGE_LINK + product.getMobileLargeImageUrl());
						product.setName(TextUtil.parseString(product.getName()));
						product.setShortDescription(TextUtil.parseString(product.getShortDescription()));
						product.setLongDescription(TextUtil.parseString(product.getLongDescription()));
						ProductView pv = new ProductView(product);
						List<ProductSKU> productSKUs = productDao.getProductSKUByProduct(product.getId()); 
						//For each product sku
						if (productSKUs != null && productSKUs.size() > 0){
							pv.setSku(productSKUs.get(0).getSKUId());
						}else{
							pv.setSku("");
						}
						pvs.add(pv);
					}
				}
		
		return pvs;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Integer getRewardsProductCounts(String communitycode, Integer limit){
		System.out.println("GET Rewards Service");
		List<Product> products = productDao.getProductByType(AppConstants.PRODUCT_REWARDS);
		
		if (products != null && products.size() > 0){
			return products.size();
		}else{
			return 0;
		}
	}
}


