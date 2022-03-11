package com.aem.core.services;

import java.util.HashMap;
import java.util.Map;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(service = FetchResponseService.class)

public class FetchResponseService {

	@Reference
	private ResourceResolverFactory factory;
	private static final String SUB_SERVICE_USER = "mycodecore";

	public ResourceResolver getRsrc() throws LoginException {

		final Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put(ResourceResolverFactory.SUBSERVICE, SUB_SERVICE_USER);

		ResourceResolver resourceresolver = factory.getServiceResourceResolver(paramMap);

		return resourceresolver;

	}

}
