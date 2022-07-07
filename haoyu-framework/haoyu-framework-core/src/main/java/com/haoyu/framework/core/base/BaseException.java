package com.haoyu.framework.core.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 异常基类
 * </p>
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BaseException extends RuntimeException {
	private Integer code;
	private String message;
	private Object data;

	public BaseException(BaseResultCode status) {
		super(status.getMessage());
		this.code = status.getCode();
		this.message = status.getMessage();
	}

	public BaseException(BaseResultCode status, Object data) {
		this(status);
		this.data = data;
	}

	public BaseException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BaseException(Integer code, String message, Object data) {
		this(code, message);
		this.data = data;
	}
}
