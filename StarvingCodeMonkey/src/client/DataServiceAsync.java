/**
 * RPC call uses for store data on to server
 */
package client;

/**
 * @author Charles
 *
 */
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DataServiceAsync {
	public void getColNames(String url, AsyncCallback<String[]> callback);
	public void get2DArray(String url, AsyncCallback<String[][]> callback);
}
