package com.google.gwt.killers.entity;

import java.net.HttpURLConnection;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.http.client.URL;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder; 
import com.google.gwt.http.client.RequestCallback; 
import com.google.gwt.http.client.RequestException; 
import com.google.gwt.http.client.Response;


public class UserLocation {

	
	String address;
	double lat;
	double longit;
	String endReq = "Vancouver,+BC&sensor=false&key=API_KEY";

	
	

	
	public UserLocation(String addr){
	
	//lat = parseLat();
	//longit = parseLongit();
		
	}


    	public String getUserLocInfo(String num, String roadName){	
    		
    		String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+"+"+num+"+"+roadName+"+"+endReq;
    		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
    	
    		try {
    		  Request request = builder.sendRequest(null, new RequestCallback() {
    		    public void onError(Request request, Throwable exception) {
    		       // Couldn't connect to server (could be timeout, SOP violation, etc.)
    		    }

    		    public void onResponseReceived(Request request, Response response) {
    		      if (200 == response.getStatusCode()) {
    		          // Process the response in response.getText()
    		    	  
    		    	  //
    		      } else {
    		        // Handle the error.  Can get the status text from response.getStatusText()
    		      }
    		    }
    		  });
    		} catch (RequestException e) {
    		  // Couldn't connect to server
    		}
    		return null;
    	}
	
	
	
	
	
	public void setLong(String address){
		return;
	}
	
	public void setLat(String address){
		return;
	}

	
	public double getLong(){
		return longit;
	}

	public double getLat(){
		return lat;
	}
	
}