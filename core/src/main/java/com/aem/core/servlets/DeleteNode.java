package com.aem.core.servlets;

import java.io.IOException;

import javax.jcr.Node;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.tika.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.aem.core.services.NodeCrudOperation;

@Component(service = Servlet.class,

		property = { "sling.servlet.methods =" + HttpConstants.METHOD_POST, "sling.servlet.paths =" + "/bin/delNode" })
public class DeleteNode extends SlingAllMethodsServlet {

	@Reference
	private NodeCrudOperation nco;

	@Override
	protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("application/json");

		try {
			String resbody = IOUtils.toString(req.getReader());

			JSONObject json = new JSONObject(resbody);

			boolean chckNode = nco.deleteNode(json.getString("one"));

			if (chckNode) {

				res.getWriter().print("Node deleted Successfully");
			} else {

				res.getWriter().print("Node not deleted Successfully");
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
