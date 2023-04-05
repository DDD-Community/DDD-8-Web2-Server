package ddd.caffeine.ratrip.common.exception.domain;

import ddd.caffeine.ratrip.common.exception.BaseException;
import ddd.caffeine.ratrip.common.exception.ExceptionInformation;
import lombok.Getter;

@Getter
public class MemoException extends BaseException {

	public MemoException(int status, String errorCode, String message) {
		super(status, errorCode, message);
	}

	public MemoException(ExceptionInformation information) {
		super(information);
	}
}
