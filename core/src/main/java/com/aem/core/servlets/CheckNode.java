package com.aem.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.aem.core.services.FetchResponseService;
import com.aem.core.services.NodeCrudOperation;

@Component(service = Servlet.class,

		property = { "sling.servlet.methods =" + HttpConstants.METHOD_POST, "sling.servlet.paths =" + "/bin/checkNode" }

)

public class CheckNode extends SlingAllMethodsServlet {

	@Reference
	private NodeCrudOperation nco;

	@Reference
	private FetchResponseService frs;

	@Override
	protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse res)
			throws ServletException, IOException {
		try {

			String body = IOUtils.toString(req.getReader());
			JSONObject json = new JSONObject(body);

			boolean checkNode = nco.checkNode(json.getString("one"));

			res.getWriter().print(checkNode);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
