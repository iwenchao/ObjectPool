package com.iwenchao.art;

import java.sql.Connection;

import com.iwenchao.art.factory.BoundedBlockingPool;
import com.iwenchao.art.factory.JDBCConnectionFactory;
import com.iwenchao.art.factory.JDBCConnectionValidator;
import com.iwenchao.art.interfaces.IPool;

public class MainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 IPool <Connection> pool = new BoundedBlockingPool <Connection> (10,
				             new JDBCConnectionValidator(),
				             new JDBCConnectionFactory("", "","", ""));
				         //do whatever you like

	}

}
