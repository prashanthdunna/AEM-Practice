package com.aem.core.servlets;

import java.io.IOException;

import javax.jcr.Node;
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

@Component(service = Servlet.class,

		property = {

				"sling.servlet.methods =" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/testPath"

		})

public class NodeServlets extends SlingSafeMethodsServlet {

	private static final Logger Log = LoggerFactory.getLogger(NodeServlets.class);

	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

		res.setContentType("application/json");

		ResourceResolver resolver = req.getResourceResolver();

		Resource resource = resolver
				.getResource("/content/mycode/us/en/myPage/jcr:content/root/responsivegrid/text_comp");

		JSONObject obj = new JSONObject();

		Node currentNode = resource.adaptTo(Node.class);

//		Log.info(currentNode.toString());

		try {
			if (currentNode.hasProperties()) {

				PropertyIterator pr = currentNode.getProperties();

				while (pr.hasNext()) {

					Property prop = pr.nextProperty();
//					Log.info(prop.toString());

//					obj.put(currentNode.getName(), currentNode.getProperty("title").getString());
				
					
					obj.put(prop.getName(),prop.getValue());
					
					

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
