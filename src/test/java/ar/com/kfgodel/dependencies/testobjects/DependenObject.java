package ar.com.kfgodel.dependencies.testobjects;

import javax.inject.Inject;

/**
 * This type is a test object to verify correct injection
 * Created by kfgodel on 20/03/16.
 */
public class DependenObject {

  @Inject
  private TestInterface dependency;

  public TestInterface getDependency() {
    return dependency;
  }

  public void setDependency(TestInterface dependency) {
    this.dependency = dependency;
  }

  public static DependenObject create() {
    DependenObject object = new DependenObject();
    return object;
  }

  /**
   * Overriden to have a predecible string of every instance
   */
  @Override
  public String toString() {
    return getClass().getSimpleName() + "@1";
  }
}
