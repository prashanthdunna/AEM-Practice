package com.aem.core.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.osgi.service.component.annotations.Component;

@Component(service = TestService.class)
public class TestService {

	public void displayContent() throws IOException, JSONException {

		
		JSONObject response = new JSONObject(); 

		String link = "https://gorest.co.in/public/v2/todos";

		StringBuffer sb = new StringBuffer();

		String line = "";

		URL mylink = new URL(link);

		HttpURLConnection con = (HttpURLConnection) mylink.openConnection();

		con.connect();

		int responsecode = con.getResponseCode();


			response.put("status", responsecode);
		
		InputStream is = con.getInputStream();

		BufferedReader br = new BufferedReader(new InputStreamReader(is));

	}

}
