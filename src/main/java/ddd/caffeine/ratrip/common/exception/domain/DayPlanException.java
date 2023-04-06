package ddd.caffeine.ratrip.common.exception.domain;

import ddd.caffeine.ratrip.common.exception.BaseException;
import ddd.caffeine.ratrip.common.exception.ExceptionInformation;
import lombok.Getter;

@Getter
public class DayPlanException extends BaseException {

	public DayPlanException(int status, String errorCode, String message) {
		super(status, errorCode, message);
	}

	public DayPlanException(ExceptionInformation information) {
		super(information);
	}
}
