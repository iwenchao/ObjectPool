package com.iwenchao.art.factory;

import java.sql.Connection;
import java.sql.SQLException;

import com.iwenchao.art.interfaces.IValidator;

public final class JDBCConnectionValidator implements IValidator<Connection> {
	public boolean isValid(Connection con) {
		if (con == null) {
			return false;
		}
		try {
			return !con.isClosed();
		} catch (SQLException se) {
			return false;
		}
	}

	public void invalidate(Connection con) {
		try {
			con.close();
		} catch (SQLException se) {
		}
	}

}
