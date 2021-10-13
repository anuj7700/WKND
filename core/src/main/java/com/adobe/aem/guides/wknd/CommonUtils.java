package com.adobe.aem.guides.wknd;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.dam.api.DamConstants;
import com.day.cq.wcm.api.Page;

public class CommonUtils {

    protected static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    private CommonUtils() {
        // Do nothing
    }

    /**
     * Append appropriate extension for content paths ie /content/dhk/foo/boo if possible use "@ extension = 'html'"
     *
     * @param path
     * @return URL with proper extension
     */
    public static String getURL(final String path) {
        if (StringUtils.isBlank(path)) {
            return StringUtils.EMPTY;
        }

        if (isExternalURL(path) || isHasExtension(path) || StringUtils.endsWith(path, "/") || path.startsWith("/content/dam/")) {
            return path;
        }

        return  StringUtils.startsWith(path, "/content") ? path.concat(".html") : path;
    }

    /**
     * External URL if contains protocol, query or javascript index
     */
    public static boolean isExternalURL(final String path) {
        final int protocolIndex = path.indexOf(":/");
        final int queryIndex = path.indexOf('?');
        final int javascriptIndex = path.indexOf("javascript:");
        final boolean hasHttp = path.startsWith(Constants.DEFAULT_PROTOCOL_SCHEME);
        final boolean hasFtp = path.startsWith("ftp");
        return protocolIndex > -1 || queryIndex > 0 || javascriptIndex > -1 || hasHttp || hasFtp;
    }

    /**
     * External URL if contains protocol, query or javascript index
     */
    public static boolean isHasExtension(final String path) {
        return (path.endsWith(".xml") || path.endsWith(".html") || path.endsWith(".htm")
                || path.contains(".html#") || path.contains(".html?") || path.contains(".htm#"));
    }
    
    /**
     * 
     * @param rootPath
     * @return
     */
    public static Map<String, String> formQueryForFragmen(String rootPath, String categoryID) {
    	Map<String, String> map = new HashMap<>();
    	map.put("type", DamConstants.NT_DAM_ASSET);
    	map.put("path", "/content/dam/aem-cplotusonlinecommerce-project");
    	map.put("1_property", "jcr:content/contentFragment");
    	map.put("1_property.value", "true");
    	map.put("2_property", "jcr:content/data/cq:model");
    	map.put("2_property.value", rootPath);
    	map.put("3_property", "jcr:content/data/master/tagWeb");
    	map.put("3_property.value", categoryID);   	
    	map.put("p.limit", "-1");
		return map;
		
	}
    
    /*
     * Function to split the query string and store it hash map
     */
    public static Map<String, String> getQueryMap(String query) throws UnsupportedEncodingException {  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();

	    for (String param : params) {  
	        String name = param.split("=")[0];  
	        String value = param.split("=").length >= 2 ? param.split("=")[1] : "";
	        map.put(name, value);  
	    }  
	    return map;  
	}

    /****
     * Get language from page.
     * @param page
     * @return the language of the page.
     */
    public static String getPageLanguage(final Page page) {
        String language = Constants.DEFAULT_LANG_CODE;
        if (page != null) {
            InheritanceValueMap inheritValueMap = new HierarchyNodeInheritanceValueMap(page.getContentResource());
            language = inheritValueMap.getInherited(JcrConstants.JCR_LANGUAGE, Constants.DEFAULT_LANG_CODE);
        }
        return language;
    }
}
