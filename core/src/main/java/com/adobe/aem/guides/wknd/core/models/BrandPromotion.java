package com.adobe.aem.guides.wknd.core.models;

import com.adobe.cq.export.json.ComponentExporter;

import java.util.List;
import java.util.Map;

public interface BrandPromotion extends ComponentExporter {
	List<Map<String,Object>> getPortfolios();
}
