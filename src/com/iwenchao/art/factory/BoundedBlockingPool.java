package com.iwenchao.art.factory;

import java.util.concurrent.BlockingQueue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.iwenchao.art.interfaces.IBlockingPool;
import com.iwenchao.art.interfaces.IObjectFactory;
import com.iwenchao.art.interfaces.IValidator;
@SuppressWarnings("rawtypes")

public final class BoundedBlockingPool<T> extends AbstractPool<T> implements
		IBlockingPool<T> {
	private int size;
	private BlockingQueue<T> objects;
	
	private IValidator<T> validator;
	private IObjectFactory objectFactory;
	private ExecutorService executor = Executors.newCachedThreadPool();
	private volatile boolean shutdownCalled;

	public BoundedBlockingPool(int size, IValidator<T> validator,
			IObjectFactory objectFactory) {
		super();
		this.objectFactory = objectFactory;
		this.size = size;
		this.validator = validator;
		objects = new LinkedBlockingQueue<T>(size);
		initializeObjects();
		shutdownCalled = false;
	}

	public T get(long timeOut, TimeUnit unit) {
		if (!shutdownCalled) {
			T t = null;
			try {
				t = (T) objects.poll(timeOut, unit);
				return t;
			} catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			return t;
		}
		throw new IllegalStateException("Object pool is already shutdown");
	}

	public T get() {
		if (!shutdownCalled) {
			T t = null;
			try {
				t = (T) objects.take();
			}

			catch (InterruptedException ie) {
				Thread.currentThread().interrupt();
			}
			return t;
		}

		throw new IllegalStateException("Object pool is already shutdown");
	}

	public void shutdown() {
		shutdownCalled = true;
		executor.shutdownNow();
		clearResources();
	}

	private void clearResources() {
		for (T t : objects) {
			validator.invalidate(t);
		}
	}

	@Override
	protected void returnToPool(T t) {
		if (validator.isValid(t)) {
			executor.submit(new ObjectReturner(objects, t));
		}
	}

	@Override
	protected void handleInvalidReturn(T t) {
	}

	@Override
	protected boolean isValid(T t) {
		return validator.isValid(t);
	}

	private void initializeObjects() {
		for (int i = 0; i < size; i++) {
			objects.add((T) objectFactory.createNew());
		}
	}

	private class ObjectReturner<E> implements Callable<E> {
		private BlockingQueue queue;
		private E e;

		public ObjectReturner(BlockingQueue queue, E e) {
			this.queue = queue;
			this.e = e;
		}

		public E call() {
			while (true) {
				try {
					queue.put(e);
					break;
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
			}
			return null;
		}

	}

}
