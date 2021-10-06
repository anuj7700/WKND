package com.adobe.aem.guides.wknd;

public class Constants {

    public static final int DEFAULT_TIMEOUT = 60000;

    //10 minutes in millisecond
    public static final long PRODUCT_LIST_MAGENTO_TIMEOUT = 600000;

    public static final String CONTENT_TYPE = "Content-Type";
    public static final String DEFAULT_HEADER_TYPE = "application/json";

    public static final String QUERY = "query";
    public static final String VARIABLES = "variables";
    public static final String DATA_KEY = "data";
    public static final String ERRORS_KEY = "errors";

    public static final String DEFAULT_LANG_CODE    = "en";

    public static final String PROJECT_APP_PATH    = "/apps/aem-cplotusonlinecommerce-project"; //NOSONAR

    // magento servlet constant
    public static final String CATEGORY_DROPDOWN   = "/category-dropdown";
    public static final String PRODUCT_DROPDOWN    = "/product-dropdown";
    public static final String CATEGORY_URL_KEY_DROPDOWN   = "/category-url-key-dropdown";

    public static final String DEFAULT_PROTOCOL_SCHEME    = "http"; //NOSONAR
    public static final String SECURE_PROTOCOL_SCHEME    = "https"; //NOSONAR
    
    public static final int PAGESIZE = 10000;
    public static final int CURRENT_PAGE =1;
}
