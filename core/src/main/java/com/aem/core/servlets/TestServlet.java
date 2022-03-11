package com.aem.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;

import org.apache.jackrabbit.vault.util.JcrConstants;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.resourceTypes=" + "aemgeeks/components/structure/geeks-home",
		"sling.servlet.extensions=" + "txt" })
@ServiceDescription("Practice Demo Servlet")
public class TestServlet extends SlingSafeMethodsServlet {

	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse res) throws IOException {

		Resource resource = req.getResource();

		res.setContentType("text/plain");

		res.getWriter().write("Title = " + resource.getValueMap().get(JcrConstants.JCR_TITLE));

	}

}
