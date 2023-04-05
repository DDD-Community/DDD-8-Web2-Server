package ddd.caffeine.ratrip.module.memo.application;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.common.exception.domain.MemoException;
import ddd.caffeine.ratrip.module.day_plan.application.DayPlanService;
import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.memo.application.dto.ChangeMemoSequenceDto;
import ddd.caffeine.ratrip.module.memo.application.dto.CreateMemoDto;
import ddd.caffeine.ratrip.module.memo.domain.Memo;
import ddd.caffeine.ratrip.module.memo.domain.repository.MemoRepository;
import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemoService {
	private final DayPlanService dayPlanService;
	private final MemoRepository memoRepository;

	public void createMemo(User user, CreateMemoDto request) {
		DayPlan dayPlan = validateExistDayPlan(user, request.getDayPlanId());
		memoRepository.save(request.toEntity(dayPlan, user));
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

	private DayPlan validateExistDayPlan(User user, Long dayPlanId) {
		return dayPlanService.validateExistDayPlan(user, dayPlanId);
	}
}
