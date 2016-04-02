package ar.com.kfgodel.dependencies.impl;

import ar.com.kfgodel.dependencies.api.exceptions.DependencyException;
import ar.com.kfgodel.nary.api.Nary;
import ar.com.kfgodel.nary.impl.EmptyNary;

import java.util.NoSuchElementException;

/**
 * This type implements a nary that explains why the value is absent
 * Created by kfgodel on 02/04/16.
 */
public class MissingBindingNary extends EmptyNary {

  private Class<?> unboundType;

  @Override
  public Object get() throws NoSuchElementException {
    throw new DependencyException("Can't get implementation for [" + unboundType + "]: No binding defined for this type");
  }

  public static <T> Nary<T> create(Class<T> unboundType) {
    MissingBindingNary explanation = new MissingBindingNary();
    explanation.unboundType = unboundType;
    return (Nary<T>) explanation;
  }

}
