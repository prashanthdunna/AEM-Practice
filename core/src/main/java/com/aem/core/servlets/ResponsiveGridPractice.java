package com.aem.core.servlets;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
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

@Component(service = Servlet.class, property = { "sling.servlet.methods =" + HttpConstants.METHOD_GET,
		"sling.servlet.paths =" + "/bin/Responsive" })

public class ResponsiveGridPractice extends SlingSafeMethodsServlet {

	private static final Logger Log = LoggerFactory.getLogger(ResponsiveGridPractice.class);

	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

		res.setContentType("application/json");

		ResourceResolver resolver = req.getResourceResolver();

		Resource resource = resolver.getResource("/content/mycode/us/en/myPage/jcr:content/root/responsivegrid");

		Node currentNode = resource.adaptTo(Node.class);

		// Log.info(currentNode.toString());

		JSONObject obj = new JSONObject();

		try {
			if (currentNode.hasNodes()) {

				NodeIterator dataitems = currentNode.getNodes();

				while (dataitems.hasNext()) {

					Node data = dataitems.nextNode();

					PropertyIterator pr = data.getProperties();

					while (pr.hasNext()) {

						Property pr1 = pr.nextProperty();

						obj.put(pr1.getName(), pr1.getValue());

					}

					obj.put("=================", "===============");

				}

			}
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
