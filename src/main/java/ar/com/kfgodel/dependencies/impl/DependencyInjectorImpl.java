package ar.com.kfgodel.dependencies.impl;

import ar.com.kfgodel.dependencies.api.DependencyInjector;
import ar.com.kfgodel.dependencies.api.exceptions.DependencyException;
import ar.com.kfgodel.diamond.api.Diamond;
import ar.com.kfgodel.diamond.api.exceptions.DiamondException;
import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * This type implements the injector
 * Created by kfgodel on 20/03/16.
 */
public class DependencyInjectorImpl implements DependencyInjector {

  private Map<Class<?>, Object> instanceMappings;
  private Optional<Injector> guiceInjector;

  public static DependencyInjectorImpl create() {
    DependencyInjectorImpl injector = new DependencyInjectorImpl();
    injector.instanceMappings = new HashMap<>();
    injector.initialize();
    return injector;
  }

  @Override
  public <T> T getImplementationFor(Class<T> expectedType) {
    Injector injector = getOrCreateInjector();
    try{
      T implementation = injector.getInstance(expectedType);
      return implementation;
    }catch (ConfigurationException e){
      throw new DependencyException("Can't get implementation for ["+expectedType+"]: " + e.getMessage(),e);
    }
  }

  @Override
  public <T> void bindTo(Class<T> type, T instanceImplementation) {
    instanceMappings.put(type, instanceImplementation);
    removeExistingInjector();
  }

  @Override
  public void injectInto(Object instance) {
    Injector injector = getOrCreateInjector();
    try{
      injector.injectMembers(instance);
    }catch (ConfigurationException e){
      throw new DependencyException("Can't inject all dependencies on ["+instance+"]: " + e.getMessage(), e);
    }
  }

  @Override
  public <T> T createInjected(Class<T> expectedType) {
    T createdInstance;
    try{
      createdInstance = (T) Diamond.of(expectedType).newInstance();
    }catch (DiamondException e){
      throw new DependencyException("Can't instantiate ["+expectedType+"]: " + e.getMessage(), e);
    }
    injectInto(createdInstance);
    return createdInstance;
  }

  /**
   * Gets the current injector or creates one with existing mappings
   * @return The injector with current mappings
   */
  private Injector getOrCreateInjector() {
    return guiceInjector
      .orElseGet(this::createInjector);
  }

  /**
   * Creates a new injector with the current mappings and caches it locally as an instance variable
   * @return The created injector
   */
  private Injector createInjector() {
    Injector createdInjector = Guice.createInjector(MappingBasedModule.create(instanceMappings));
    guiceInjector = Optional.of(createdInjector);
    return createdInjector;
  }
  /**
   * Clears the existing injector (if any), to be recreated later when accessed
   */
  private void removeExistingInjector() {
    this.guiceInjector = Optional.empty();
  }

  /**
   * Initializes the state of this instance to be used
   */
  private void initialize() {
    this.removeExistingInjector();
    this.bindTo(DependencyInjector.class, this);
  }

}
