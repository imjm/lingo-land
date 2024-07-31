package com.ssafy.a603.lingoland.global.error.exception;

import com.ssafy.a603.lingoland.global.error.entity.ErrorCode;

public class IllegalParameterException extends BaseException {
	public IllegalParameterException(ErrorCode errorCode) {
		super(errorCode.getMessage(), errorCode);
	}

	public IllegalParameterException() {
		super(ErrorCode.INVALID_INPUT_VALUE);
	}
}
