/**
 * RPC Login Callback
 */
package client;
//TODO: Anastasiya
/**
 * @author Charles
 *check test test
 */

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {
  public void login(String requestUri, AsyncCallback<LoginInfo> async);
}