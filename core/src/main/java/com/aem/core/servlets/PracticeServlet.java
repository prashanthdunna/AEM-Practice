package com.aem.core.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.Servlet;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;

@Component(service = Servlet.class, property = { "sling.servlet.methods=" + HttpConstants.METHOD_GET,
		"sling.servlet.paths=" + "/bin/practiceServlet" })
public class PracticeServlet extends SlingSafeMethodsServlet {

	protected void doGet(SlingHttpServletRequest req, SlingHttpServletResponse res) throws IOException {

		res.setContentType("application/json");

		JSONObject response = new JSONObject();

		String urlLink = "https://gorest.co.in/public/v2/todos";

		StringBuffer sb = new StringBuffer();

		try {
			String line = "";

			URL url = new URL(urlLink);

			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

			con.connect();

			int responsecode = con.getResponseCode();

			response.put("staus", responsecode);

			if (responsecode == 200) {

				InputStream is = con.getInputStream();

				BufferedReader br = new BufferedReader(new InputStreamReader(is));

				while ((line = br.readLine()) != null) {

					sb.append(line);

				}

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		res.getWriter().print(sb);

	}

}
