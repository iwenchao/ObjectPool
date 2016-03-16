package com.iwenchao.art.interfaces;

/**
 * Represents a cached pool of objects.
 * @param <T> the type of object to pool.
 */
public interface IPool<T>{
 /**
  * Returns an instance from the pool.
  * The call may be a blocking one or a non-blocking one
  * and that is determined by the internal implementation.
  *
  * If the call is a blocking call,
  * the call returns immediately with a valid object
  * if available, else the thread is made to wait
  * until an object becomes available.
  * In case of a blocking call,
  * it is advised that clients react
  * to {@link InterruptedException} which might be thrown
  * when the thread waits for an object to become available.
  *
  * If the call is a non-blocking one,
  * the call returns immediately irrespective of
  * whether an object is available or not.
  * If any object is available the call returns it
  * else the call returns < code >null< /code >.
  *
  * The validity of the objects are determined using the
  * {@link IValidator} interface, such that
  * an object < code >o< /code > is valid if
  * < code > Validator.isValid(o) == true < /code >.
  *
  * @return T one of the pooled objects.
  */
 T get();
 
 /**
  * Releases the object and puts it back to the pool.
  *
  * The mechanism of putting the object back to the pool is
  * generally asynchronous,
  * however future implementations might differ.
  *
  * @param t the object to return to the pool
  */
 
 void release(T t);
 /**
  * Shuts down the pool. In essence this call will not
  * accept any more requests
  * and will release all resources.
  * Releasing resources are done
  * via the < code >invalidate()< /code >
  * method of the {@link IValidator} interface.
  */
 void shutdown();
}
