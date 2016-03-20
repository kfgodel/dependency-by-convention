package ar.com.kfgodel.dependencies.testobjects;

/**
 * This type is a test implementation of dependency interface
 * Created by kfgodel on 20/03/16.
 */
public class StringTestImplementation implements TestInterface {

  @Override
  public Object doStuff() {
    return "Hola";
  }

  public static StringTestImplementation create() {
    StringTestImplementation implementation = new StringTestImplementation();
    return implementation;
  }

}
