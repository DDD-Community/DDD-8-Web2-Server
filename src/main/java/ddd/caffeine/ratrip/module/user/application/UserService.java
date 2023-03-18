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

	public User findUserIdBySocialIdAndSocialType(final SignUpUserDto request) {
		return findUserBySocialInfo(request);
	}

	public UserNameResponseDto findUserName(final User user) {
		return new UserNameResponseDto(user.getName());
	}

	public UserNameUpdateResponseDto updateName(final User user, final UpdateUserNameDto request) {
		user.updateName(request.getNewName());
		return new UserNameUpdateResponseDto(userRepository.save(user).getName());
	}

	public UserStatus withdrawal(final User user) {
		travelPlanService.deleteAllTravelPlan(user);
		placeService.deleteAllBookmark(user);
		user.delete();
		return userRepository.save(user).getStatus();
	}

	private User findUserBySocialInfo(final SignUpUserDto request) {
		return userRepository.findUserBySocialInfo(SocialInfo.of(request.getSocialId(), request.getSocialType()))
			.orElse(signUpUser(request));
	}

	private User signUpUser(final SignUpUserDto request) {
		return userRepository.save(
			User.of(request.getName(), request.getEmail(), UserStatus.ACTIVE, request.getSocialId(),
				request.getSocialType()));
	}

	@Override
	public User loadUserByUsername(final String userId) {
		return userRepository.findById(UUID.fromString(userId))
			.orElseThrow(() -> new UserException(NOT_FOUND_USER_EXCEPTION));
	}
}
