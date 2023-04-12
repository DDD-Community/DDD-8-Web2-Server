package ddd.caffeine.ratrip.module.memo.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.memo.domain.repository.dao.ShortestPathDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationPathResponseDto {
	List<ShortestPathDao> places;

	public static RecommendationPathResponseDto of(List<ShortestPathDao> places) {
		return new RecommendationPathResponseDto(places);
	}
}
