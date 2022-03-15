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

		property = {

				"sling.servlet.methods =" + HttpConstants.METHOD_POST, "sling.servlet.paths =" + "/bin/updateNode" })
public class UpdateNode extends SlingAllMethodsServlet {

	@Reference
	private NodeCrudOperation nco;

	@Override
	protected void doPost(SlingHttpServletRequest req, SlingHttpServletResponse res)
			throws ServletException, IOException {

		res.setContentType("application/json");

		String body = IOUtils.toString(req.getReader());

		JSONObject json;
		try {
			json = new JSONObject(body);

			nco.updateNodeName(json.getString("one"), json.getString("newName"));

			res.getWriter().print("Updated Successfully");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
