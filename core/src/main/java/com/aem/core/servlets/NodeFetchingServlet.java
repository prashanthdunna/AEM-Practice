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
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = Servlet.class, property = { "sling.servlet.methods =" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/test"

})
public class NodeFetchingServlet extends SlingSafeMethodsServlet {

	private static final Logger Log = LoggerFactory.getLogger(NodeFetchingServlet.class);

	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

		res.setContentType("application/json");

		JSONObject data = new JSONObject();

		ResourceResolver resolver = req.getResourceResolver();

		Resource resource = resolver
				.getResource("/content/mycode/us/en/myPage/jcr:content/root/responsivegrid/text_comp");

		Node currentNode = resource.adaptTo(Node.class);

//		Log.info(currentNode.toString());

		try {
			data.put("title", currentNode.getProperty("title").getString());
			data.put("description", currentNode.getProperty("description").getString());
		} catch (JSONException | RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		res.getWriter().print(data);
	}

}
