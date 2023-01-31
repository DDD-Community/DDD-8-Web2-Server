package ddd.caffeine.ratrip.module.travel_plan.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.common.exception.domain.TravelPlanException;
import ddd.caffeine.ratrip.common.model.Region;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlan;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanAccessOption;
import ddd.caffeine.ratrip.module.travel_plan.domain.TravelPlanUser;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.TravelPlanUserRepository;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.TravelPlanResponseDto;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.TravelPlanResponseModel;
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

	public TravelPlanResponseDto readByUser(User user, Pageable pageable) {
		List<TravelPlanResponseModel> contents = new ArrayList<>();
		Slice<TravelPlanUser> travelPlanUser = travelPlanUserRepository.findByUser(user, pageable);
		for (TravelPlanUser entity : travelPlanUser.getContent()) {
			contents.add(new TravelPlanResponseModel(entity.getTravelPlan()));
		}
		return TravelPlanResponseDto.builder()
			.contents(contents)
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

	public Region findOngoingTravelPlanUserRegionByUser(User user) {
		return travelPlanUserRepository.findOngoingTravelPlanUserRegionByUser(user);
	}
}
