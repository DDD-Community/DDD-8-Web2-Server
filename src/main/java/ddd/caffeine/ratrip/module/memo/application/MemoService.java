package ddd.caffeine.ratrip.module.memo.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ddd.caffeine.ratrip.module.day_plan.application.DayPlanService;
import ddd.caffeine.ratrip.module.day_plan.domain.DayPlan;
import ddd.caffeine.ratrip.module.memo.application.dto.CreateMemoDto;
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
		DayPlan dayPlan = validateExistDayPlan(user, request);
		memoRepository.save(request.toEntity(dayPlan, user));
	}

	private DayPlan validateExistDayPlan(User user, CreateMemoDto request) {
		return dayPlanService.validateExistDayPlan(user, request.getDayPlanId());
	}
}
