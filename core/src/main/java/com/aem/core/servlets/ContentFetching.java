package com.aem.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.tika.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.aem.core.services.ContentFragmentBasicCrud;

@Component(service = Servlet.class, property = {

		"sling.servlet.methods = " + HttpConstants.METHOD_GET, "sling.servlet.paths =" + "/bin/contentFetching"

})
public class ContentFetching extends SlingAllMethodsServlet {

	@Reference
	private ContentFragmentBasicCrud cfbc;

	@Override
	protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse res)
			throws ServletException, IOException {

		String body = IOUtils.toString(req.getReader());

		try {
			JSONObject json = new JSONObject(body);
			JSONObject data = cfbc.getContentFragment(json.getString("path"));
			
			res.getWriter().print(data);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
