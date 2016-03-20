package ar.com.kfgodel.dependencies.impl;

import com.google.inject.AbstractModule;

import java.util.Map;

/**
 * This type is an adapter that takes mapping definitions and binds them
 * when asked to guice module definitions
 *
 * Created by kfgodel on 20/03/16.
 */
public class MappingBasedModule extends AbstractModule {

  private Map<Class<?>, Object> instanceMappings;

  @Override
  protected void configure() {
    instanceMappings.entrySet().forEach((entry)->{
      Class expectedType = entry.getKey();
      Object boundInstance = entry.getValue();
      bind(expectedType).toInstance(boundInstance);
    });

  }

  public static MappingBasedModule create(Map<Class<?>, Object> instanceMappings) {
    MappingBasedModule module = new MappingBasedModule();
    module.instanceMappings = instanceMappings;
    return module;
  }

}
