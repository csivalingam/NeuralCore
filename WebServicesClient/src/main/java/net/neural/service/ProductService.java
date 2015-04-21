package net.zfp.service;

import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.zfp.entity.category.MerchandisingCategory;
import net.zfp.entity.product.ProductBanner;
import net.zfp.view.MerchandisingCategoryView;
import net.zfp.view.MerchandisingCategoryViews;
import net.zfp.view.ProductBannerView;
import net.zfp.view.ProductBannerViews;
import net.zfp.view.ProductListView;
import net.zfp.view.ProductView;

/**
 * Provide any information about product catalogue.
 * 
 * @author Youngwook Yoo
 * @since 5.0
 *
 */
@Path("/catalogue")
public interface ProductService {
	
	/**
	 * Get given catalogue's detail
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param UPC
	 * 
	 * @return catalogue's detail
	 */
	@GET
	@Path("getproductdetail")
	@Produces("application/json")
	ProductView getProductByUPC(@QueryParam("UPC") String UPC);
	
	/**
	 * Get limited catalogue based on merchandising category
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param domainName
	 * @param merchandisingCategory
	 * @param productType
	 * @param page
	 * @param limit
	 * @param sortBy
	 * 
	 * @return limited catalogue list
	 */
	@GET
	@Path("getproducts")
	@Produces("application/json")
	ProductListView getProductListByMerchandiseCategory( @QueryParam("memberid") Long memberId, @QueryParam("domain") String domainName, 
			@QueryParam("category") Integer merchandisingCategory, @QueryParam("producttype") String productType, @QueryParam("page") Integer page,
			@QueryParam("offset") Integer limit, @QueryParam("sortBy") String sortBy);

	/**
	 * Get limited catalogues
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param memberId
	 * @param domainName
	 * @param productType
	 * @param topNumber
	 * 
	 * @return limited catalogue list
	 */
	@GET
	@Path("gettopcatalogue")
	@Produces("application/json")
	ProductListView getTopCatalogue( @QueryParam("memberid") Long memberId, @QueryParam("domain") String domainName, @QueryParam("producttype") String productType, @QueryParam("topnumber") Integer topNumber);
	
	/**
	 * Get catalogue banners 
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @param domainName
	 * @param productType
	 * @param memberId
	 * 
	 * @return catalogue banners
	 */
	@GET
	@Path("getcataloguebanner")
	@Produces("application/json")
	ProductBannerViews getProductBanners(@QueryParam("domain") String domainName, @QueryParam("producttype") String productType, @QueryParam("memberid") Long memberId);
	
	/**
	 * Get list of merchandising category
	 * 
	 * @author Youngwook Yoo
	 * 
	 * @return list of merchandising category
	 */
	@GET
	@Path("getmerchandisingcategory")
	@Produces("application/json")
	MerchandisingCategoryViews getMerchandisingCategory();
	
	
	//------
	@POST
	@Path("getConnectorProductsByCategoryCode")
	@Produces("text/xml")
	List<ProductView> getConnectorProductsByCategoryCode(@FormParam("code")Integer code);
	
	@POST
	@Path("productbyId")
	@Produces("text/xml")
	ProductView getProductById(@FormParam("productId") Long productId);
	
	@POST
	@Path("products")
	@Produces("text/xml")
	List<ProductView> getProducts(@FormParam("domainName") String domainName, @FormParam("productTypeId") Long productTypeId, @FormParam("categoryType") Integer categoryType);
	
	@POST
	@Path("getRewardsProducts")
	@Produces("text/xml")
	List<ProductView> getRewardsProducts(@FormParam("communitycode") String communitycode, @FormParam("limit") Integer limit, @FormParam("page") Integer page);
	
	@POST
	@Path("getRewardsProductCounts")
	@Produces("text/xml")
	Integer getRewardsProductCounts(@FormParam("communitycode") String communitycode, @FormParam("limit") Integer limit);
	
	@POST
	@Path("productsbycategory")
	@Produces("text/xml")
	List<ProductView> getProductsByCategoryCode(@FormParam("domainName") String domainName, @FormParam("categoryCode") String categoryCode, @FormParam("productType") String productType);
	
	@POST
	@Path("productsbycategories")
	@Produces("text/xml")
	List<ProductView> getProductsByCategoryType(@FormParam("domainName") String domainName, @FormParam("categoryType") Integer categoryType,  @FormParam("isCashProduct") Boolean isCashProduct, @FormParam("productType") String productType, @FormParam("memberId") Long memberId);
	
	
	@POST
	@Path("getfeaturedproducts")
	@Produces("text/xml")
	List<ProductView> getFeaturedProducts(@FormParam("domainName") String domainName, @FormParam("productType") String productType, @FormParam("memberId") Long memberId);
	
	
}
