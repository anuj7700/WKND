/*
 *  Copyright 2019 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.adobe.aem.guides.wknd.core.models.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.apache.sling.models.factory.ModelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.components.ComponentContext;
import com.adobe.aem.guides.wknd.core.models.MyModel;

@Model(
        adaptables = {SlingHttpServletRequest.class},
        adapters = {MyModel.class},
        resourceType = {MyModelImpl.RESOURCE_TYPE},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class MyModelImpl implements MyModel {
    protected static final String RESOURCE_TYPE = "wknd/components/mycomponent";

    @Self
    private SlingHttpServletRequest request;

    @ScriptVariable
    private Resource resource;

    @OSGiService
    private ModelFactory modelFactory;

    @ScriptVariable
    private Page currentPage;

    @ResourcePath(path="/content/wknd/language-masters/en/test/jcr:content/root/container/helloworld")
    private Resource helloWorldResource;

    @ScriptVariable
    protected ComponentContext componentContext;

    @ValueMapValue 
    private String name;

    @ValueMapValue
    private List<String> occupations;

    //private Image image;

    // Add a logger for any errors
    private static final Logger LOGGER = LoggerFactory.getLogger(MyModelImpl.class);

    @PostConstruct
    private void init() {
        LOGGER.info("Resource path " + resource.getPath());
        LOGGER.info("Helloworld resource path 123" + helloWorldResource.getPath());
        
       // image = modelFactory.getModelFromWrappedRequest(request, request.getResource(), Image.class);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getOccupations() {
         if (occupations != null) {
             Collections.sort(occupations);
             return new ArrayList<String>(occupations);
         } else {
             return Collections.emptyList();
         }
    }

    @Override
    public boolean isEmpty() {
        //final Image image = getImage();

        if (StringUtils.isBlank(name)) {
            // Name is missing, but required
            return true;
        } else if (occupations == null || occupations.isEmpty()) {
            // At least one occupation is required
            return true;
        } else {
            // Everything is populated, so this component is not considered empty
            return false;
        }
    }

}