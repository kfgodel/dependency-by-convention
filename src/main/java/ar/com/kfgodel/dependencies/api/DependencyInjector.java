package ar.com.kfgodel.dependencies.api;

import ar.com.kfgodel.nary.api.Nary;

/**
 * This type represents the main interface to register bindings and get injected instances, or inject an exisiting instance
 *
 * Created by kfgodel on 20/03/16.
 */
public interface DependencyInjector {
  /**
   * Solves the dependency graph and returns an instance of the given class.<br>
   *   It throws an error if the given type cannot be met as dependency
   * @param expectedType The class that represents the type
   * @param <T> The expected instance type
   * @return The instance that meets the expected type defined previously
   */
  <T> Nary<T> getImplementationFor(Class<T> expectedType);

  /**
   * Defines a binding to a specific instance to be used for type resolution.<br>
   *   The given isntance will be used as is
   * @param type The class that represents the expected type
   * @param instanceImplementation The concrete instance implementation to use for that type
   */
  <T> void bindTo(Class<T> type, T instanceImplementation);

  /**
   * Completes the object dependencies by injecting them into the object directly
   * @param instance The instance to inject
   */
  void injectInto(Object instance);

  /**
   * Creates an instance of the given class instantiating it and injecting the needed
   * members
   * @param expectedType The class that represents the expected type
   * @param <T> The type of expected instance
   * @return The created instance
   */
  <T> T createInjected(Class<T> expectedType);
}
