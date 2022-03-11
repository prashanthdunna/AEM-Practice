package com.aem.core.services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = MyCodeService.class)
public class MyCodeService {

	private static final Logger Log = LoggerFactory.getLogger(MyCodeService.class);

	public JSONObject readJsonData() throws JSONException {
		JSONObject response = new JSONObject();
		String urldata = "https://gorest.co.in/public/v2/users";
		StringBuffer sb = new StringBuffer();
		try {
			String line = "";
			URL url = new URL(urldata);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.connect();
			int responsecode = con.getResponseCode();
			response.put("status", responsecode);
			if (responsecode == 200) {
				InputStream in = con.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}
				response.put("data", sb.toString());
			}
		} catch (Exception e) {
			response.put("error", "ünable to fetch the data");
			e.printStackTrace();
		}
		return response;
	}
}