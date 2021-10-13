package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.CommonUtils;
import com.adobe.aem.guides.wknd.ContentFragmentUtils;
import com.adobe.aem.guides.wknd.core.models.BrandPromotion;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.day.cq.wcm.api.Page;

@Model(adaptables = SlingHttpServletRequest.class, adapters = { BrandPromotion.class,
        ComponentExporter.class }, resourceType = BrandPromotionImpl.RESOURCE_TYPE, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
@Exporter(name = ExporterConstants.SLING_MODEL_EXPORTER_NAME, extensions = ExporterConstants.SLING_MODEL_EXTENSION)
public class BrandPromotionImpl implements BrandPromotion{
	 
	private static final String PORTFOLIOS_LIST = "includedPortfolios";

	private static final String TAG = "tag";

	private static final String SECTION_NAME = "sectionName";
	
	private static final String SEE_ALL_TEXT = "seeAllText";
	
	private static final String SEE_ALL_LINK = "seeAllLink";
	
	private static final String SEE_ALL_OPEN_IN_NEW_TAB = "seeAllOpenInNewTab";
	
	private static final String DESCRIPTION = "description";
	
	private static final String PRODUCT_DEEP_LINK = "productDeepLink";
	
	private static final String LOGO_IMAGE = "portfolioLogo";
	
	private static final String IMAGE = "portfolioImage";
	
	private static final String URL = "url";
	
	private static final String BG_COLOR = "bgColor";
	
	private static final String OPEN_IN_NEW_TAB = "openInNewTab";

	private static final Logger LOG = LoggerFactory.getLogger(BrandPromotionImpl.class);

    static final String RESOURCE_TYPE = "wknd/components/portfolioslist";
    
    private Resource fragmentResource;
    
    private Resource itemResource;
    
    @ValueMapValue
    private String portfoliolistpath;

    @Inject
    private Page currentPage;
    
    @Inject
    ResourceResolver resourceResolver;

    private Optional<ContentFragment> contentFragment;

    @PostConstruct
    protected void init(){
    	LOG.debug("init of BrandPromotionImpl");
        if (portfoliolistpath != null) {
            if (fragmentResource == null) fragmentResource = resourceResolver.getResource(portfoliolistpath);
            if (fragmentResource != null) contentFragment = Optional.ofNullable(fragmentResource.adaptTo(ContentFragment.class));
            LOG.debug("Fragment Path:" + fragmentResource.getPath());
        }
    }

    public List<Map<String,Object>> getPortfolios() {
    	List<Map<String,Object>> banners = new ArrayList<>();
    	
        if (portfoliolistpath != null) {

           List<String> BrandPromotionFragment = ContentFragmentUtils.getCFListContent(contentFragment, PORTFOLIOS_LIST);
           
           if (!BrandPromotionFragment.isEmpty()) {
				for (String itemPath : BrandPromotionFragment) {
					Map<String, Object> bannerMap = new HashMap<>();
					Map<String, String> logoImageObj = new HashMap<>();
					Map<String, String> imageObj = new HashMap<>();

					Resource itemFragmentResource = resourceResolver.getResource(itemPath);
					if (itemFragmentResource == null) itemFragmentResource = itemResource;
					if (itemFragmentResource != null) {
						Optional<ContentFragment> contentFragmentItem = Optional
								.ofNullable(itemFragmentResource.adaptTo(ContentFragment.class));
						String description = ContentFragmentUtils.getCFStringContent(contentFragmentItem, DESCRIPTION);
						String logoImage = ContentFragmentUtils.getCFStringContent(contentFragmentItem, LOGO_IMAGE);
                        String image = ContentFragmentUtils.getCFStringContent(contentFragmentItem, IMAGE);
						logoImageObj.put("logopath", logoImage);
                        imageObj.put("imagepath", image);
                        bannerMap.put(DESCRIPTION, description);
                        bannerMap.put(LOGO_IMAGE, logoImage);
						bannerMap.put(IMAGE, image);
						banners.add(bannerMap);
					}
				}
           }
           return banners;
        }
        return banners;
    }

   
    @Override
    public String getExportedType() {
        return BrandPromotionImpl.RESOURCE_TYPE;
    }

	
	public List<Map<String, Object>> getPortfolios1() {
    	List<Map<String,Object>> banners = new ArrayList<>();
    	
        if (portfoliolistpath != null) {

           List<String> BrandPromotionFragment = ContentFragmentUtils.getCFListContent(contentFragment, PORTFOLIOS_LIST);
           
           if (!BrandPromotionFragment.isEmpty()) {
				for (String itemPath : BrandPromotionFragment) {
					Map<String, Object> bannerMap = new HashMap<>();
					Map<String, String> imageObj = new HashMap<>();
                    LOG.debug("ItemPath:" + itemPath);
					Resource itemFragmentResource = resourceResolver.getResource(itemPath);
					if (itemFragmentResource == null) itemFragmentResource = itemResource;
					if (itemFragmentResource != null) {
						Optional<ContentFragment> contentFragmentItem = Optional
								.ofNullable(itemFragmentResource.adaptTo(ContentFragment.class));
						String deepLinkForApp = ContentFragmentUtils.getCFStringContent(contentFragmentItem, DESCRIPTION);
						String logoImage = ContentFragmentUtils.getCFStringContent(contentFragmentItem, LOGO_IMAGE);
						Boolean openInNewTab = ContentFragmentUtils.getCFBooleanContent(contentFragmentItem, OPEN_IN_NEW_TAB);
						String url = ContentFragmentUtils.getCFStringContent(contentFragmentItem, URL);
						imageObj.put("_path", logoImage);

						bannerMap.put(URL, url);
						bannerMap.put(OPEN_IN_NEW_TAB, openInNewTab);
						bannerMap.put(PRODUCT_DEEP_LINK, deepLinkForApp);
						bannerMap.put(IMAGE, imageObj);
						banners.add(bannerMap);
					}
				}
           }
           return banners;
        }
        return banners;
	}

	
}