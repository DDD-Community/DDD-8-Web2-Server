package ddd.caffeine.ratrip.module.notification.application;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.module.notification.application.dto.CreateNotificationDto;
import ddd.caffeine.ratrip.module.notification.application.validator.NotificationValidator;
import ddd.caffeine.ratrip.module.notification.domain.Notification;
import ddd.caffeine.ratrip.module.notification.domain.respository.NotificationRepository;
import ddd.caffeine.ratrip.module.notification.presentation.dto.response.NotificationCreateResponseDto;
import ddd.caffeine.ratrip.module.notification.presentation.dto.response.NotificationDetailResponseDto;
import ddd.caffeine.ratrip.module.notification.presentation.dto.response.NotificationDto;
import ddd.caffeine.ratrip.module.notification.presentation.dto.response.NotificationsResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
	private final NotificationRepository notificationRepository;
	private final NotificationValidator notificationValidator;

	public NotificationCreateResponseDto createNotification(CreateNotificationDto request) {
		Notification notification = Notification.of(request.getTitle(), request.getContent());
		Long notificationId = notificationRepository.save(notification)
			.getId(); //TODO - 쿼리 최적화 필요 / 다른 save 문들 쿼리 확인해보기
		return new NotificationCreateResponseDto(notificationId);
	}

	@Transactional(readOnly = true)
	public NotificationsResponseDto getNotifications(final Pageable pageable) {
		Slice<NotificationDto> notificationDtos = notificationRepository.findNotificationsUsingSlice(pageable);
		return new NotificationsResponseDto(notificationDtos.getContent(), notificationDtos.hasNext());
	}

	@Transactional(readOnly = true)
	public NotificationDetailResponseDto getNotificationDetail(final Long id) {
		Notification notification = notificationRepository.findNotificationById(id);
		notificationValidator.validateExistNotification(notification);

		return new NotificationDetailResponseDto(notification.getId(), notification.getTitle(),
			notification.getContent());
	}
}
