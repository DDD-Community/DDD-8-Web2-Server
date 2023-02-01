package ddd.caffeine.ratrip.common.exception.domain;

import ddd.caffeine.ratrip.common.exception.BaseException;
import ddd.caffeine.ratrip.common.exception.ExceptionInformation;
import lombok.Getter;

@Getter
public class DaySchedulePlaceException extends BaseException {

	public DaySchedulePlaceException(int status, String errorCode, String message) {
		super(status, errorCode, message);
	}

	public DaySchedulePlaceException(ExceptionInformation information) {
		super(information);
	}
}
