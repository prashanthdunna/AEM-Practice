package com.aem.core.servlets;

import java.io.IOException;

import javax.jcr.Node;
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

@Component(service = Servlet.class,

		property = { "sling.servlet.methods =" + HttpConstants.METHOD_POST, "sling.servlet.paths =" + "/bin/addNode" }

)

public class AddNode extends SlingAllMethodsServlet {

	@Reference
	private NodeCrudOperation nco;

	@Reference
	private FetchResponseService frs;

	@Override
	protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		String body = IOUtils.toString(request.getReader());
		try {
			JSONObject json = new JSONObject(body);
			Node node = nco.createNode(json.getString("one"), json.getString("nodeName"));

			response.getWriter().print(node.getPath());
			response.getWriter().print(node.getNode("nodeName"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
