package com.fly.exception;

import com.fly.data.enums.ServiceEnum;

public class ServiceException extends RuntimeException {

	private static final long	serialVersionUID	= 8935805243057842722L;
	
	private Integer code;

	public ServiceException(Integer code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public ServiceException(Integer code, String message) {
		super(message);
		this.code = code;
	}
	
	public ServiceException(String message) {
		this(ServiceEnum.BUSINESS_FAIL.getCode(), message);
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
