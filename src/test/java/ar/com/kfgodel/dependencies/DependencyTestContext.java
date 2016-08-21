package ar.com.kfgodel.dependencies;

import ar.com.dgarcia.javaspec.api.contexts.TestContext;
import ar.com.kfgodel.dependencies.api.DependencyInjector;

import java.util.function.Supplier;

/**
 * This type represents the context for test on dependency injection
 * Created by kfgodel on 20/03/16.
 */
public interface DependencyTestContext extends TestContext {

  DependencyInjector injector();
  void injector(Supplier<DependencyInjector> definition);

}
