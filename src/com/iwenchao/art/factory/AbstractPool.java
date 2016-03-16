package com.iwenchao.art.factory;

import com.iwenchao.art.interfaces.IPool;

abstract class AbstractPool <T> implements IPool <T>{
 
	 @Override
	 public final void release(T t){
		  if(isValid(t)){
			  
		   returnToPool(t);
		  }else {
			  
		   handleInvalidReturn(t);
		  }
	 }
 
	 protected abstract void handleInvalidReturn(T t);
	 
	 protected abstract void returnToPool(T t);
	 
	 protected abstract boolean isValid(T t);
}
