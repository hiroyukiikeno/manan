package com.manandakana.dao;

public class DAOException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DAOException() {
		super();
	}

	public DAOException(Throwable se) {
		super(se);
	}

	public DAOException(String string) {
		super(string);
	}

}
