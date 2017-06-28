package org.bob.open.api.exception;


import org.bob.open.api.constant.ApiCodeEnum;

/**
 *
 */
public class SystemException extends RuntimeException {

	private static final long serialVersionUID = -73567508601044861L;

	private String code;

	public SystemException() {}

	public SystemException(String code, String message) {
		super(message);
		this.code = code;
	}

	public SystemException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public SystemException(ApiCodeEnum codeEnum) {
		super(codeEnum.getMsg());
		this.code = String.valueOf(codeEnum.getCode());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
