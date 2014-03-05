/**
 * Used for storage RPC callback
 */
package client;
//TODO: Kevin

/**
 * @author Charles
 *
 */

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.core.client.GWT;


public interface StorageServiceAsync {
	
	public void addVisited(String place, AsyncCallback<Void> async);
	public void removeVisited(String place, AsyncCallback<Void> async);
	public void getVisited(AsyncCallback<String[]> async);
	
	public void addFav(String place, AsyncCallback<Void> async);
	public void removeFav(String place, AsyncCallback<Void> async);
	public void getFavs(AsyncCallback<String[]> async);
	
	public void setRating(String place, int rating, AsyncCallback<Void> async);
	  
}
