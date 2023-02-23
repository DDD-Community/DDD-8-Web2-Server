package ddd.caffeine.ratrip.module.user.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.UserException;
import ddd.caffeine.ratrip.module.notification.application.dto.UpdateUserNameDto;
import ddd.caffeine.ratrip.module.place.application.PlaceService;
import ddd.caffeine.ratrip.module.travel_plan.application.TravelPlanService;
import ddd.caffeine.ratrip.module.user.application.dto.SignUpUserDto;
import ddd.caffeine.ratrip.module.user.domain.SocialInfo;
import ddd.caffeine.ratrip.module.user.domain.User;
import ddd.caffeine.ratrip.module.user.domain.UserStatus;
import ddd.caffeine.ratrip.module.user.domain.repository.UserRepository;
import ddd.caffeine.ratrip.module.user.presentation.dto.response.UserNameResponseDto;
import ddd.caffeine.ratrip.module.user.presentation.dto.response.UserNameUpdateResponseDto;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
	private final UserRepository userRepository;
	private final TravelPlanService travelPlanService;
	private final PlaceService placeService;
	private final UserValidator userValidator;

	public UUID findUserIdBySocialIdAndSocialType(SignUpUserDto request) {
		User user = findUserBySocialInfo(SocialInfo.of(request.getSocialId(), request.getSocialType()));
		return signUpUserIfAbsentAndGetUserId(request, user);
	}

	public UserNameResponseDto findUserName(User user) {
		return new UserNameResponseDto(user.getName());
	}

	public UserNameUpdateResponseDto updateName(User user, UpdateUserNameDto request) {
		user.updateName(request.getNewName());
		return new UserNameUpdateResponseDto(userRepository.save(user).getName());
	}

	public void withdrawal(User user) {
		travelPlanService.deleteAllTravelPlan(user); //TODO -  조회시 isDeleted 속성 확인하는 부분 추가
		placeService.deleteAllBookmark(user);
		userRepository.delete(user); // 안먹힘
	}

	private User findUserBySocialInfo(SocialInfo socialInfo) {
		return userRepository.findUserBySocialInfo(socialInfo);
	}

	private UUID signUpUserIfAbsentAndGetUserId(SignUpUserDto request, User user) {
		if (userExist(user)) {
			return user.getId();
		}
		return signUpUserAndGetUserId(request);
	}

	private boolean userExist(User user) {
		return user != null;
	}

	private UUID signUpUserAndGetUserId(SignUpUserDto request) {
		User user = userRepository.save(
			User.of(request.getName(), request.getEmail(), UserStatus.ACTIVE, request.getSocialId(),
				request.getSocialType()));

		return user.getId();
	}

	@Override
	public User loadUserByUsername(String userId) {
		return userRepository.findById(UUID.fromString(userId))
			.orElseThrow(() -> new UserException(NOT_FOUND_USER_EXCEPTION));
	}
}
