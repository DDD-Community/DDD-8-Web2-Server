package ddd.caffeine.ratrip.module.place.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.place.feign.kakao.model.FeignPlaceMetaData;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceSearchResponseDto {
	private List<PlaceSearchResponse> thirdPartyModel;
	private boolean isEnd;
	private int pageableCount;
	private int totalCount;

	@Builder
	public PlaceSearchResponseDto(List<PlaceSearchResponse> placeSearchResponses, FeignPlaceMetaData metaData) {
		this.thirdPartyModel = placeSearchResponses;
		this.isEnd = metaData.isEnd();
		this.pageableCount = metaData.getPageableCount();
		this.totalCount = metaData.getTotalCount();
	}
}
