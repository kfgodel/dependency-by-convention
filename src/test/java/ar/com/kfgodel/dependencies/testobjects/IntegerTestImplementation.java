package ar.com.kfgodel.dependencies.testobjects;

/**
 * This type is a test implementation to validate correct injection
 * Created by kfgodel on 20/03/16.
 */
public class IntegerTestImplementation implements TestInterface {
  @Override
  public Object doStuff() {
    return 3;
  }

  public static IntegerTestImplementation create() {
    IntegerTestImplementation implementation = new IntegerTestImplementation();
    return implementation;
  }

}
