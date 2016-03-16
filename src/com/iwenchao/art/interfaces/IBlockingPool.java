package com.iwenchao.art.interfaces;

import java.util.concurrent.TimeUnit;

public interface IBlockingPool <T> extends IPool < T >{

	
	T get();

	T get(long time, TimeUnit unit) throws InterruptedException;

}
