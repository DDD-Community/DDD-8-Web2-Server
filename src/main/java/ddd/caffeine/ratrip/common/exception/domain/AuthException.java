package ddd.caffeine.ratrip.common.exception.domain;

import ddd.caffeine.ratrip.common.exception.BaseException;
import ddd.caffeine.ratrip.common.exception.ExceptionInformation;

public class AuthException extends BaseException {
	public AuthException(int status, String errorCode, String message) {
		super(status, errorCode, message);
	}
	
	public AuthException(ExceptionInformation information) {
		super(information);
	}

}
