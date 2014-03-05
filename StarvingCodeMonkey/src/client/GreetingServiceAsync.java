/**
 * 
 */
package client;

import com.google.gwt.user.client.rpc.AsyncCallback;

//TODO: Jim
/**
 * @author Charles
 *
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}