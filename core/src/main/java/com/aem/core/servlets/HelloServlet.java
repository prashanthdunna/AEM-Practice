package com.aem.core.servlets;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class,

		property = {

				"sling.servlet.methods =" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/myPath"

		})
public class HelloServlet extends SlingSafeMethodsServlet {

	private static final Logger Log = LoggerFactory.getLogger(HelloServlet.class);

	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

		res.setContentType("application/json");

		JSONObject obj = new JSONObject();

		String result = "";

		ResourceResolver resolver = req.getResourceResolver();

		Resource resource = resolver
				.getResource("/content/mycode/us/en/myPage/jcr:content/root/responsivegrid/text_comp");

		Node currentNode = resource.adaptTo(Node.class);

		Log.info(currentNode.toString());

		try {
			obj.put("title", currentNode.getProperty("title").getString());
			obj.put("description", currentNode.getProperty("description").getString());
		} catch (ValueFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PathNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		res.getWriter().print(obj);

	}
}
