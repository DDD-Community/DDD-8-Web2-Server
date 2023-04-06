package ddd.caffeine.ratrip.module.memo.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.MemoException;
import ddd.caffeine.ratrip.common.util.RecommendationPathCalculator;
import ddd.caffeine.ratrip.module.day_plan.application.DayPlanService;
import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.memo.application.dto.ChangeMemoSequenceDto;
import ddd.caffeine.ratrip.module.memo.application.dto.CreateMemoDto;
import ddd.caffeine.ratrip.module.memo.application.dto.MemosDto;
import ddd.caffeine.ratrip.module.memo.application.dto.RecommendationPathDto;
import ddd.caffeine.ratrip.module.memo.application.dto.UpdateMemoDto;
import ddd.caffeine.ratrip.module.memo.domain.Memo;
import ddd.caffeine.ratrip.module.memo.domain.repository.MemoRepository;
import ddd.caffeine.ratrip.module.memo.presentation.dto.response.MemosResponseDto;
import ddd.caffeine.ratrip.module.memo.presentation.dto.response.RecommendationPathResponseDto;
import ddd.caffeine.ratrip.module.place.application.PlaceService;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemoService {
	private final DayPlanService dayPlanService;
	private final PlaceService placeService;
	private final MemoRepository memoRepository;

	public void createMemo(User user, CreateMemoDto request) {
		DayPlan dayPlan = validateExistDayPlan(user, request.getDayPlanId());
		Place place = validateExistPlace(request.getKakaoId());
		validateAlreadyExistMemo(dayPlan, user, place);

		memoRepository.save(request.toEntity(dayPlan, user, place));
	}

	public void changeMemoSequence(User user, ChangeMemoSequenceDto request) {
		DayPlan dayPlan = validateExistDayPlan(user, request.getDayPlanId());
		List<Memo> memos = memoRepository.findByDayPlanIdAndUser(dayPlan.getId(), user);

		List<Long> memoIds = request.getMemoIds();
		Map<Long, Memo> memoMap = memos.stream().collect(Collectors.toMap(Memo::getId, memo -> memo));

		for (int sequence = 0; sequence < memoIds.size(); sequence++) {
			Long memoId = memoIds.get(sequence);

			Memo memo = Optional.ofNullable(memoMap.get(memoId))
				.orElseThrow(() -> new MemoException(NOT_FOUND_MEMO_EXCEPTION));

			memo.changeSequence(sequence);
		}
	}

	public MemosResponseDto getMemos(User user, MemosDto request) {
		DayPlan dayPlan = validateExistDayPlan(user, request.getDayPlanId());
		List<Memo> memos = memoRepository.findByDayPlanIdAndUser(dayPlan.getId(), user);

		return MemosResponseDto.of(memos);
	}

	public void updateMemo(User user, UpdateMemoDto request) {
		DayPlan dayPlan = validateExistDayPlan(user, request.getDayPlanId());
		Memo memo = validateExistMemo(user, dayPlan.getId());

		memo.updateContent(request.getContent());
	}

	public RecommendationPathResponseDto getRecommendationPath(User user, RecommendationPathDto request) {
		DayPlan dayPlan = validateExistDayPlan(user, request.getDayPlanId());
		List<Memo> memos = memoRepository.findByDayPlanIdAndUser(dayPlan.getId(), user);
		List<Place> places = memos.stream().map(Memo::getPlace).toList(); //TODO - check

		return RecommendationPathResponseDto.of(
			RecommendationPathCalculator.byGreedyAlgorithm(request.getPlaceId(), places));
	}

	private Memo validateExistMemo(User user, Long memoId) {
		return memoRepository.findByIdAndUser(memoId, user)
			.orElseThrow(() -> new MemoException(NOT_FOUND_MEMO_EXCEPTION));
	}

	private void validateAlreadyExistMemo(DayPlan dayPlan, User user, Place place) {
		memoRepository.findByDayPlanIdAndPlaceIdAndUser(dayPlan.getId(), user, place)
			.ifPresent(memo -> {
				throw new MemoException(ALREADY_EXIST_MEMO_EXCEPTION);
			});
	}

	private DayPlan validateExistDayPlan(User user, Long dayPlanId) {
		return dayPlanService.validateExistDayPlan(user, dayPlanId);
	}

	private Place validateExistPlace(String placeKakaoId) {
		return placeService.validateExistPlace(placeKakaoId);
	}
}
