package com.google.gwt.killers.entity;


import com.google.gwt.http.client.URL;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder; 
import com.google.gwt.http.client.RequestCallback; 
import com.google.gwt.http.client.RequestException; 
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;



public class UserLocation {

	
	String address;
	double lat;
	double longit;
	
	public static final String endReq = "Vancouver,+BC&sensor=false&key=AIzaSyCdMxEpfdSyXpFuX9uSfOyRjbdh09RNcjY";

	

	
	public UserLocation(String num, String roadName){
	setLatLong(num, roadName);
	}

		//input the house number and road name
	// sets the lat long
	   	public void setLatLong(String num, String roadName){	
    		
    
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
    		    	 
    		    	JSONValue jsv = JSONParser.parse(response.getText());
    		    	JSONObject jso = jsv.isObject();
    		    	JSONArray jsa = jso.get("results").isArray();
    		    	JSONObject resultsJSO = jsa.get(0).isObject();  // GOT THE RESULTS OBJECT!!
    		    	
    		    	JSONObject geometryJSO = resultsJSO.get("geometry").isObject();
    		    	JSONObject locationJSO = geometryJSO.get("location").isObject(); // gets location!!
    		    	lat = locationJSO.get("lat").isNumber().doubleValue();
    		    	longit = locationJSO.get("lng").isNumber().doubleValue();
    		    	
    		      } else {
    		        // Handle the error.  Can get the status text from response.getStatusText()
    		    	response.getStatusText();
    		      }
    		    }
    		  });
    		} catch (RequestException e) {
    		  // Couldn't connect to server
    			e.printStackTrace();
    		}
    	
    	}
	
	
	
	
	
	
	
	public double getLong(){
		return longit;
	}

	public double getLat(){
		return lat;
	}
	
}