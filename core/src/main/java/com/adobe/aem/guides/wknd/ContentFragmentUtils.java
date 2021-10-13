package com.adobe.aem.guides.wknd;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.export.json.ComponentExporter;
import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.factory.ModelFactory;

import java.util.*;

public class ContentFragmentUtils {

    /**
     * Returns a map of all given resources mapping their name to their {@link ComponentExporter component exporter}
     * model.
     */
    public static Map<String, ComponentExporter> getComponentExporters(Iterator<Resource> resourceIterator,
                                                                       ModelFactory modelFactory,
                                                                       SlingHttpServletRequest slingHttpServletRequest) {
        final Map<String, ComponentExporter> componentExporterMap = new LinkedHashMap<>();

        while (resourceIterator.hasNext()) {
            Resource resource = resourceIterator.next();
            ComponentExporter exporter =
                    modelFactory.getModelFromWrappedRequest(slingHttpServletRequest, resource, ComponentExporter.class);

            if (exporter != null) {
                String name = resource.getName();
                if (componentExporterMap.put(name, exporter) != null) {
                    throw new IllegalStateException(String.format("Duplicate key '%s'", name));
                }
            }
        }

        return componentExporterMap;
    }

    public static String getCFStringContent(Optional<ContentFragment> contentFragment, String fieldName) {
        if (contentFragment != null && contentFragment.isPresent()) {
            return  contentFragment
                    .map(cf -> cf.getElement(fieldName))
                    .map(ContentElement::getContent)
                    .orElse(StringUtils.EMPTY);
        }
        return StringUtils.EMPTY;
    }

    public static List<String> getCFListContent(Optional<ContentFragment> contentFragment, String fieldName) {
        List<String> list = new ArrayList<>();
        if (contentFragment != null && contentFragment.isPresent()) {
            String item = contentFragment
                    .map(cf -> cf.getElement(fieldName))
                    .map(ContentElement::getContent)
                    .orElse(StringUtils.EMPTY);

            //split by new lines
            String[] lines = item.split("\\r?\\n");
            list = Arrays.asList(lines);
        }
        return list;
    }

    public static Map<String, String> getCFMapContent(Optional<ContentFragment> contentFragment, String fieldName) {
        if (contentFragment != null && contentFragment.isPresent()) {
            Map<String, String> map = new HashMap<String, String>();

            String path = contentFragment
                    .map(cf -> cf.getElement(fieldName))
                    .map(ContentElement::getContent)
                    .orElse(StringUtils.EMPTY);

            map.put("_path", path);

            return map;
        }
        return null;
    }

    public static Boolean getCFBooleanContent(Optional<ContentFragment> contentFragment, String fieldName) {
        if (contentFragment != null && contentFragment.isPresent()) {
            return Boolean.valueOf( contentFragment
                    .map(cf -> cf.getElement(fieldName))
                    .map(ContentElement::getContent)
                    .orElse(StringUtils.EMPTY));
        }
        return null;
    }
}
