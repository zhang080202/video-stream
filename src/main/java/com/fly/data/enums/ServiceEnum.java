package com.fly.data.enums;

public enum ServiceEnum {
	CHECK_FAIL(501, "验证失败"), BUSINESS_FAIL(500);

	private int		code;

	private String	msg;

	private ServiceEnum(int code) {
		this.code = code;
	}

	private ServiceEnum(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}
}
