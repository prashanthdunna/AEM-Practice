package com.aem.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.core.services.MyCodeService;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/pathBased" })
public class PathBasedServlet extends SlingSafeMethodsServlet {

	private static final Logger Log = LoggerFactory.getLogger(PathBasedServlet.class);

	@Reference
	private MyCodeService myservice;

	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

		res.setContentType("application/json");

		try {
			res.getWriter().print(myservice.readJsonData());
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
