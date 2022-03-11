package com.aem.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.aem.core.services.FetchResponseService;
import com.aem.core.services.NodeCrudOperation;

@Component(service = Servlet.class, property = {

		"sling.servlet.methods =" + HttpConstants.METHOD_POST, "sling.servlet.paths =" + "/bin/nodeasParam"

})
public class NodeAsParam extends SlingAllMethodsServlet {

	@Reference
	private FetchResponseService frs;
	@Reference
	private NodeCrudOperation nco;

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		String resourceNode = request.getParameter("path");

		JSONObject obj = nco.getProperties(resourceNode);

		response.getWriter().print(obj);
	}

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		String body = IOUtils.toString(request.getReader());

		JSONObject json = null;
		try {
			
			json = new JSONObject(body);
			JSONObject resObj = nco.getProperties(json.get("path").toString());
			response.getWriter().print(resObj);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
