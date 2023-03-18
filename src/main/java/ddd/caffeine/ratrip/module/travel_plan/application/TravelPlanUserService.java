package ddd.caffeine.ratrip.module.travel_plan.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import ddd.caffeine.ratrip.common.exception.domain.TravelPlanException;
import ddd.caffeine.ratrip.module.place.domain.Region;
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

	//Todo : 개발용 - 추후 삭제할 것
	public void TEMP_deleteTravelPlanUser(User user) {
		List<TravelPlanUser> travelPlanUser = travelPlanUserRepository.findByUser(user);
		travelPlanUserRepository.deleteAllInBatch(travelPlanUser);
	}

	public void deleteTravelPlanUser(TravelPlanUser travelPlanUser) {
		travelPlanUser.delete();
	}

	public void saveTravelPlanWithUser(TravelPlan travelPlan, User user) {
		TravelPlanUser travelPlanUser = new TravelPlanUser(travelPlan, user);
		travelPlanUserRepository.save(travelPlanUser);
	}

	public MyTravelPlanResponseDto readByUserPagination(User user, Pageable pageable) {
		Slice<TravelPlanUser> travelPlanUser = travelPlanUserRepository.findByUserPagination(user, pageable);
		return MyTravelPlanResponseDto.builder()
			.contents(travelPlanUser.getContent())
			.hasNext(travelPlanUser.hasNext())
			.build();
	}

	public List<TravelPlanUser> findByUser(User user) {
		return travelPlanUserRepository.findByUser(user);
	}

	public TravelPlanUser readByUserLatestTravel(User user) {
		return travelPlanUserRepository.findByUserLatestTravel(user);
	}

	public TravelPlanUser readByUserUnfinishedTravel(User user) {
		return travelPlanUserRepository.findByUserUnFinishedTravel(user);
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
