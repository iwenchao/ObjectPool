package com.iwenchao.art.interfaces;

public interface IValidator<T> {

	/**
	   * Checks whether the object is valid.
	   *
	   * @param t the object to check.
	   *
	   * @return <code>true</code>
	   * if the object is valid else <code>false</code>.
	   */
	  public boolean isValid(T t);
	  /**
	   * Performs any cleanup activities
	   * before discarding the object.
	   * For example before discarding
	   * database connection objects,
	   * the pool will want to close the connections.
	   * This is done via the
	   * <code>invalidate()</code> method.
	   *
	   * @param t the object to cleanup
	   */
	  public void invalidate(T t);

}
