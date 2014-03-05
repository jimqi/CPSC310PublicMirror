/**
 * Used for data storage RPC call
 */
package client;
// TODO: Kevin
/**
 * @author Charles
 *
 */

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("user")
public interface StorageService extends RemoteService {
	
	public void addVisited(String place);
	public void removeVisited(String place);
	public String[] getVisited();
	
	public void addFav(String place);
	public void removeFav(String place);
	public String[] getFavs();
	
	public void setRating(String place, int rating);	
}
