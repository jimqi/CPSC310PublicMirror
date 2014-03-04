/**
 * RPC calls, use for storing data on server
 */
package client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("Dataset")
public interface DataService extends RemoteService{
	 public String[] getColNames(String url) ;
	 public String[][] get2DArray(String url);
}
