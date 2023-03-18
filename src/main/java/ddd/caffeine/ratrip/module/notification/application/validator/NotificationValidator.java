package ddd.caffeine.ratrip.module.notification.application.validator;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import org.springframework.stereotype.Component;

import ddd.caffeine.ratrip.common.exception.domain.NotificationException;
import ddd.caffeine.ratrip.module.notification.domain.Notification;

@Component
public class NotificationValidator {
	public void validateExistNotification(Notification notification) {
		if (notification == null) {
			throw new NotificationException(NOT_FOUND_NOTIFICATION_EXCEPTION);
		}
	}
}
