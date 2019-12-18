package utility;

/**
 * 
 * This class help to raise an exception if the invoker sends an 
 * invalid url like missing http in the RestAsseuredHelper class method
 *
 */

public class IncorrectHttpProtocolException extends Exception {

/**
 * Throw exception with a message if invalid url format is passed
 * @param errorMessage
 */
public IncorrectHttpProtocolException(String errorMessage) {
      super(errorMessage);
  }
}
