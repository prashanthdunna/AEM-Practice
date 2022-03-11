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
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.core.services.FetchResponseService;

@Component(service = Servlet.class, property = {

		"sling.servlet.methods =" + HttpConstants.METHOD_GET, "sling.servlet.paths =" + "/bin/fetchResp"

})
public class FetchingResponsiveGrid extends SlingSafeMethodsServlet {

	private static final Logger Log = LoggerFactory.getLogger(FetchingResponsiveGrid.class);

	@Reference
	private FetchResponseService frs;

	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

		res.setContentType("application/json");

		// Log.info(currentDetails.toString());

		JSONObject obj = new JSONObject();

		try {
			ResourceResolver resolver = frs.getRsrc();

			Resource resource = resolver.getResource("/content/mycode/us/en/myPage/jcr:content/root/responsivegrid");

			Node currentDetails = resource.adaptTo(Node.class);
			if (currentDetails.hasNodes()) {

				NodeIterator Nodedetails = currentDetails.getNodes();

				while (Nodedetails.hasNext()) {

					Node data = Nodedetails.nextNode();

					PropertyIterator pr = data.getProperties();

					while (pr.hasNext()) {

						Property mydata = pr.nextProperty();

						obj.put(mydata.getName(), mydata.getValue());

					}

				}

			}
		} catch (Exception e) {
			System.out.println("error = "+e.getMessage());
			e.printStackTrace();
			Log.info("error"+ e.getMessage()+e.getLocalizedMessage());
			

		} // TODO Auto-generated catch block

		res.getWriter().print(obj);
	}

}
