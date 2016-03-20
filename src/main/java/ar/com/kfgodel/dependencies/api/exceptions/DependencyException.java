package ar.com.kfgodel.dependencies.api.exceptions;

/**
 * This type represents an error on the dependency resolution
 * Created by kfgodel on 20/03/16.
 */
public class DependencyException extends RuntimeException {

  public DependencyException(String message) {
    super(message);
  }

  public DependencyException(String message, Throwable cause) {
    super(message, cause);
  }

  public DependencyException(Throwable cause) {
    super(cause);
  }
}
