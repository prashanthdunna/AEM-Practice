package com.aem.core.servlets;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;
import java.net.URLConnection;

import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class,

		property = {

				"sling.servlet.methods =" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/mycode"

		})
public class ApiServletPractice extends SlingSafeMethodsServlet {

	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

		URL url = new URL("https://gorest.co.in/public/v2/users");

		URLConnection urlicon = url.openConnection();

		InputStream istream = urlicon.getInputStream();

		int i;

		while ((i = istream.read()) != -1) {

			res.getWriter().write(i);

		}

	}

}
