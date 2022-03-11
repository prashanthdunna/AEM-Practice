package com.aem.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, property = {

		"sling.servlet.methods =" + HttpConstants.METHOD_GET, "sling.servlet.paths =" + "/bin/demo"

})
public class DemoServlet extends SlingSafeMethodsServlet {

	private static final Logger Log = LoggerFactory.getLogger(DemoServlet.class);

	@Override
	protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
//
//			String name = request.getParameter("name");
//		

		int num = Integer.parseInt(request.getParameter("num"));

		response.getWriter().print("Hello" + num);
	}

}
