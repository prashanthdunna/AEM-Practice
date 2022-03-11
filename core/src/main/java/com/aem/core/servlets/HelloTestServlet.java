package com.aem.core.servlets;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class,

		property = {

				"sling.servlet.methods = " + HttpConstants.METHOD_GET,
				"sling.servlet.paths = " + "/bin/HelloTestServlet"

		})
public class HelloTestServlet extends SlingSafeMethodsServlet {

	private static final Logger Log = LoggerFactory.getLogger(HelloTestServlet.class);

	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

		res.setContentType("application/json");

		JSONObject data;

		ResourceResolver resolver = req.getResourceResolver();

		Resource resource = resolver
				.getResource("/content/mycode/us/en/myPage/jcr:content/root/responsivegrid/text_comp");

		Node testNode = resource.adaptTo(Node.class);

		try {

			JSONArray content = new JSONArray();

			if (testNode.hasNodes()) {

				NodeIterator itr = testNode.getNodes();

				while (itr.hasNext()) {

					Node itemNode = itr.nextNode();

					data = new JSONObject();

					data.put("title", itemNode.getProperty("title").getString());
					data.put("description", itemNode.getProperty("description").getString());

					content.put(data);

					Log.info(data.toString());

				}

			}

			String dataItems = content.toString();

			Log.info(dataItems);

			res.getWriter().print(dataItems);

		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
