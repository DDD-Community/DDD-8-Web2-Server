package ddd.caffeine.ratrip.module.travel_plan.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.common.exception.domain.TravelPlanException;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanAccessOption;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanUser;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanUserRepository;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.MyTravelPlanResponseDto;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelPlanUserService {

	private final TravelPlanUserRepository travelPlanUserRepository;

	public void saveTravelPlanWithUser(TravelPlan travelPlan, User user) {
		TravelPlanUser travelPlanUser = new TravelPlanUser(travelPlan, user);
		travelPlanUserRepository.save(travelPlanUser);
	}

	public MyTravelPlanResponseDto readByUser(User user, Pageable pageable) {
		Slice<TravelPlanUser> travelPlanUser = travelPlanUserRepository.findByUser(user, pageable);
		return MyTravelPlanResponseDto.builder()
			.contents(travelPlanUser.getContent())
			.hasNext(travelPlanUser.hasNext())
			.build();
	}

	public TravelPlanUser readByUserUnfinishedTravel(User user) {
		return travelPlanUserRepository.findByUserUnfinishedTravel(user);
	}

	public void validateAccessTravelPlan(TravelPlanAccessOption accessOption) {
		if (travelPlanUserRepository.existByUserAndTravelPlanUUID(accessOption.readUser(),
			accessOption.readTravelPlanUUID())) {
			return;
		}
		throw new TravelPlanException(UNAUTHORIZED_ACCESS_TRAVEL_PLAN);
	}

	public void validateMakeTravelPlan(User user) {
		TravelPlanUser travelPlanUser = readByUserUnfinishedTravel(user);
		if (travelPlanUser != null) {
			throw new TravelPlanException(ALREADY_EXIST_TRAVEL_PLAN_EXCEPTION);
		}
	}
}
