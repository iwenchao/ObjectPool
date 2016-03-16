package com.iwenchao.art.factory;

import com.iwenchao.art.interfaces.IObjectFactory;
import com.iwenchao.art.interfaces.IPool;
import com.iwenchao.art.interfaces.IValidator;
/**
 * 用户通过工厂用具体的名称来创建不同的对象池
 *
 */
public final class PoolFactory {
	private PoolFactory() {
	}

	public static <T> IPool<T> newBoundedBlockingPool(int size,
			IObjectFactory<T> factory, IValidator<T> validator) {
		return new BoundedBlockingPool<T>(size, validator, factory);
	}

	public static <T> IPool<T> newBoundedNonBlockingPool(int size,
			IObjectFactory<T> factory, IValidator<T> validator) {
		return new BoundedPool<T>(size, validator, factory);
	}
}
