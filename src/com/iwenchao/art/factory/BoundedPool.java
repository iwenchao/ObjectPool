package com.iwenchao.art.factory;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

import com.iwenchao.art.interfaces.IObjectFactory;
import com.iwenchao.art.interfaces.IValidator;
 /**
  * 一个非阻塞的对象池的实现，这个实现和前面的唯一不同就是即使对象不可用，它也不会让客户端阻塞，而是直接返回null。
  * @param <T>
  */
public class BoundedPool < T > extends AbstractPool < T >{
    private int size;
    private Queue < T > objects;
    private IValidator < T > validator;
    private IObjectFactory < T > objectFactory;
    private Semaphore permits;
    private volatile boolean shutdownCalled;
 
    public BoundedPool(
        int size,
        IValidator < T > validator,
        IObjectFactory < T > objectFactory)
        {
        super();
        this.objectFactory = objectFactory;
        this.size = size;
        this.validator = validator;
        objects = new LinkedList < T >();
        initializeObjects();
        shutdownCalled = false;
    }
 
    @Override
    public T get()
    {
        T t = null;
        if(!shutdownCalled)
        {
            if(permits.tryAcquire())
            {
                t = objects.poll();
            }
         }
         else
         {
             throw new IllegalStateException("Object pool already shutdown");
         }
         return t;
     }
     @Override
     public void shutdown()
     {
         shutdownCalled = true;
         clearResources();
     }
     private void clearResources()
     {
         for(T t : objects)
         {
             validator.invalidate(t);
         }
     }
     @Override
     protected void returnToPool(T t)
     {
         boolean added = objects.add(t);
         if(added)
         {
             permits.release();
         }
     }
     @Override
     protected void handleInvalidReturn(T t)
     {
     }
     @Override
     protected boolean isValid(T t)
     {
         return validator.isValid(t);
     }
 
     private void initializeObjects()
     {
         for(int i = 0; i < size; i++)
         {
             objects.add(objectFactory.createNew());
         }
     }
}
