package com.aem.core.services.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.cq.dam.cfm.ContentElement;
import com.adobe.cq.dam.cfm.ContentFragment;
import com.adobe.cq.dam.cfm.ContentVariation;
import com.adobe.cq.dam.cfm.DataType;
import com.adobe.cq.dam.cfm.FragmentData;
import com.aem.core.services.ContentFragmentBasicCrud;
import com.aem.core.services.FetchResponseService;
import com.aem.core.util.PracticeConstants;

import sun.util.logging.resources.logging;

@Component(service = ContentFragmentBasicCrud.class)
public class ContentFragmentBasicCrudImpl implements ContentFragmentBasicCrud {

	private static final Logger Log = LoggerFactory.getLogger(ContentFragmentBasicCrud.class);

	@Reference
	private FetchResponseService frs;

	@Override
	public JSONObject getContentFragment(String contentFragment) {

		JSONObject response = new JSONObject();
		JSONObject element = new JSONObject();
		JSONArray elements = new JSONArray();
		JSONObject variations = new JSONObject();
		try {
			ResourceResolver resolver = frs.getRsrc();
			Resource resource = resolver
					.getResource(PracticeConstants.FRAGMENT_PARENT_RESOURCE + "/" + contentFragment);
			if (resource != null) {
				ContentFragment fragment = resource.adaptTo(ContentFragment.class);
				Iterator<ContentElement> contentElement = fragment.getElements();
				JSONObject myjson = new JSONObject();
				while (contentElement.hasNext()) {
					JSONArray ele = new JSONArray();
					ContentElement content = contentElement.next();
//					FragmentData fragdata = content.getValue();
					/*if (fragdata.getDataType().isMultiValue()) {
						myjson.put(content.getName(), fetchMultifield(fragdata));
						elements.put(myjson);
					} else {
						myjson.put(content.getName(), fragdata.getValue());
						elements.put(myjson);
					}*/
					Iterator<ContentVariation> contentVariation = content.getVariations();
					while (contentVariation.hasNext()) {
						ContentVariation varContent = contentVariation.next();
						FragmentData varFragment = varContent.getValue();
						JSONObject variation = new JSONObject();
						if (varFragment.getDataType().isMultiValue()) {
							variation.put(varContent.getName(), fetchMultifield(varFragment));
						} else {
							variation.put(varContent.getTitle(), varFragment.getValue());
						}
						ele.put(variation);
					}
					variations.put(content.getTitle(), ele);
				}
				response.put(PracticeConstants.CONTENT_FRAGMENT_VARIATIONS, variations);
			}
		} catch (LoginException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	private List<String> fetchMultifield(FragmentData fragdata) {

		String[] fragValue = (String[]) fragdata.getValue();
		return Arrays.asList(fragValue != null ? fragValue : new String[0]);

	}

}
