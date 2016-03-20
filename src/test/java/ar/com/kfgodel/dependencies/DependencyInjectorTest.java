package ar.com.kfgodel.dependencies;

import ar.com.dgarcia.javaspec.api.JavaSpec;
import ar.com.dgarcia.javaspec.api.JavaSpecRunner;
import ar.com.kfgodel.dependencies.api.exceptions.DependencyException;
import ar.com.kfgodel.dependencies.impl.DependencyInjectorImpl;
import ar.com.kfgodel.dependencies.testobjects.DependenObject;
import ar.com.kfgodel.dependencies.testobjects.StringTestImplementation;
import ar.com.kfgodel.dependencies.testobjects.TestInterface;
import org.junit.runner.RunWith;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

/**
 * This type verifies the behavior of the injector
 * Created by kfgodel on 20/03/16.
 */
@RunWith(JavaSpecRunner.class)
public class DependencyInjectorTest extends JavaSpec<DependencyTestContext> {
  @Override
  public void define() {
    describe("an injector", () -> {
      context().injector(DependencyInjectorImpl::create);

      describe("when asked for a dependency", () -> {
        it("throws an error if the dependency cannot be met",()->{
          try{
            context().injector().getImplementationFor(TestInterface.class);
            failBecauseExceptionWasNotThrown(DependencyException.class);
          }catch (DependencyException e){
            assertThat(e).hasMessage("Can't get implementation for [interface ar.com.kfgodel.dependencies.testobjects.TestInterface]: Guice configuration errors:\n" +
              "\n" +
              "1) No implementation for ar.com.kfgodel.dependencies.testobjects.TestInterface was bound.\n" +
              "  while locating ar.com.kfgodel.dependencies.testobjects.TestInterface\n" +
              "\n" +
              "1 error");
          }
        });

        it("returns the configured dependency if defined",()->{
          StringTestImplementation bindedImplementation = StringTestImplementation.create();
          context().injector().bindTo(TestInterface.class, bindedImplementation);

          TestInterface resolvedImplementation = context().injector().getImplementationFor(TestInterface.class);

          assertThat(resolvedImplementation).isSameAs(bindedImplementation);
        });
      });

      describe("when injecting an existing instance", () -> {
        it("throws an error if any of the instance dependencies are missing",()->{
          DependenObject instance = DependenObject.create();

          try{
            context().injector().injectInto(instance);
            failBecauseExceptionWasNotThrown(DependencyException.class);
          }catch (DependencyException e){
            assertThat(e).hasMessage("Can't inject all dependencies on [DependenObject@1]: Guice configuration errors:\n" +
              "\n" +
              "1) No implementation for ar.com.kfgodel.dependencies.testobjects.TestInterface was bound.\n" +
              "  while locating ar.com.kfgodel.dependencies.testobjects.TestInterface\n" +
              "    for field at ar.com.kfgodel.dependencies.testobjects.DependenObject.dependency(DependenObject.java:19)\n" +
              "  while locating ar.com.kfgodel.dependencies.testobjects.DependenObject\n" +
              "\n" +
              "1 error");
          }
        });

        it("injects the configured dependencyes if defined",()->{
          StringTestImplementation bindedImplementation = StringTestImplementation.create();
          context().injector().bindTo(TestInterface.class, bindedImplementation);

          DependenObject instance = DependenObject.create();
          context().injector().injectInto(instance);

          assertThat(instance.getDependency()).isSameAs(bindedImplementation);
        });
      });

      describe("when instantiating a class", () -> {
        it("throws an error if given class can't be instantiated",()->{
          try{
            context().injector().createInjected(Serializable.class);
            failBecauseExceptionWasNotThrown(DependencyException.class);
          }catch (DependencyException e){
            assertThat(e).hasMessage("Can't instantiate [interface java.io.Serializable]: Type[Serializable @ java.io] doesn't have a no-arg constructor to create the instance from");
          }
        });

        it("throws an error if any of the dependencies are missing",()->{
          try{
            context().injector().createInjected(DependenObject.class);
            failBecauseExceptionWasNotThrown(DependencyException.class);
          }catch (DependencyException e){
            assertThat(e).hasMessage("Can't inject all dependencies on [DependenObject@1]: Guice configuration errors:\n" +
              "\n" +
              "1) No implementation for ar.com.kfgodel.dependencies.testobjects.TestInterface was bound.\n" +
              "  while locating ar.com.kfgodel.dependencies.testobjects.TestInterface\n" +
              "    for field at ar.com.kfgodel.dependencies.testobjects.DependenObject.dependency(DependenObject.java:19)\n" +
              "  while locating ar.com.kfgodel.dependencies.testobjects.DependenObject\n" +
              "\n" +
              "1 error");
          }
        });

        it("injects the configured dependencies if defined",()->{
          StringTestImplementation bindedImplementation = StringTestImplementation.create();
          context().injector().bindTo(TestInterface.class, bindedImplementation);

          DependenObject created = context().injector().createInjected(DependenObject.class);

          assertThat(created.getDependency()).isSameAs(bindedImplementation);
        });
      });


    });

  }
}