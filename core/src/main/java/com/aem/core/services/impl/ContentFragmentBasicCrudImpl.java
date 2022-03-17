package com.aem.core.services.impl;

import java.util.Iterator;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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

@Component(service = ContentFragmentBasicCrud.class)
public class ContentFragmentBasicCrudImpl implements ContentFragmentBasicCrud {

	private static final Logger Log = LoggerFactory.getLogger(ContentFragmentBasicCrud.class);

	JSONObject resp = new JSONObject();
	JSONObject element = new JSONObject();
	JSONObject variation = new JSONObject();
	@Reference
	private FetchResponseService frs;

	@Override
	public JSONObject getContentFragmentData(String contentFragmenName) {

		ResourceResolver resolver;
		try {
			resolver = frs.getRsrc();

			Resource resource = resolver
					.getResource(PracticeConstants.FRAGMENT_PARENT_RESOURCE + "/" + contentFragmenName);

			if (resource != null) {

				ContentFragment fragment = resource.adaptTo(ContentFragment.class);

				resp.put(PracticeConstants.CONTENT_FRAGMENT_NAME, fragment.getName());
				resp.put(PracticeConstants.CONTENT_FRAGMENT_TITLE, fragment.getTitle());
				resp.put(PracticeConstants.CONTENT_FRAGMENT_DESC, fragment.getDescription());

				Iterator<ContentElement> ce = fragment.getElements();

				while (ce.hasNext()) {

					ContentElement contentElement = ce.next();
					Log.info(contentElement.toString());

					element.put(PracticeConstants.CONTENT_FRAGMENT_TITLE, contentElement.getTitle());

					element.put(PracticeConstants.CONTENT_FRAGMENT_TYPE, contentElement.getContentType());

					FragmentData fragvalue = contentElement.getValue();
					element.put(PracticeConstants.CONTENT_FRAGMENT_VALUE, fragvalue.getValue().toString());

					DataType fragdata = fragvalue.getDataType();
					element.put(PracticeConstants.CONTENT_FRAGMENT_DATATYPE, fragdata.getTypeString());

					element.put(contentElement.getName(), fragvalue.getValue());

//
//					Iterator<ContentVariation> contentVariations = contentElement.getVariations();
//
//					while (contentVariations.hasNext()) {
//
//						ContentVariation varDetails = contentVariations.next();
//
//						Log.info(varDetails.toString());
//
//						variation.put(PracticeConstants.CONTENT_FRAGMENT_TITLE, varDetails.getTitle());
//
//						variation.put(PracticeConstants.CONTENT_FRAGMENT_DESC, varDetails.getDescription());
//
//						variation.put(PracticeConstants.CONTENT_FRAGMENT_TYPE, varDetails.getContentType());
//
//						FragmentData variationData = varDetails.getValue();
//						variation.put(PracticeConstants.CONTENT_FRAGMENT_VALUE, varDetails.getValue().toString());
//
//						DataType data = variationData.getDataType();
//						variation.put(PracticeConstants.CONTENT_FRAGMENT_DATATYPE, variationData.getDataType());
//
//					}
//
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return element;
	}
}
