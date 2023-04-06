package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.repository.dao.RecommendByLocationDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendByLocationResponseDto {
	private List<RecommendByLocationDao> places;
	private boolean hasNext;

	public static RecommendByLocationResponseDto of(List<RecommendByLocationDao> places, boolean hasNext) {
		return new RecommendByLocationResponseDto(places, hasNext);
	}
}
