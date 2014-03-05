/**
 * Exception File 
 */
package client;
// TODO: Anastasiya
/**
 * @author Charles
 *test
 */
import java.io.Serializable;

public class NotLoggedInException extends Exception implements Serializable {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1272695093530393910L;

public NotLoggedInException() {
    super();
  }

  public NotLoggedInException(String message) {
    super(message);
  }

}
