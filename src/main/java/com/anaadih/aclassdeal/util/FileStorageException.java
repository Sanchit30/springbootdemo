package com.anaadih.aclassdeal.util;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = 1240141559060545750L;

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

}
