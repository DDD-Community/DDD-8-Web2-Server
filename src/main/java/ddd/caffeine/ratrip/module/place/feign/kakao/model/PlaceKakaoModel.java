package ddd.caffeine.ratrip.module.place.feign.kakao.model;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.common.exception.domain.PlaceException;
import ddd.caffeine.ratrip.module.place.model.Place;
import ddd.caffeine.ratrip.module.place.presentation.dto.search.PlaceSearchModel;
import ddd.caffeine.ratrip.module.place.presentation.dto.search.PlaceSearchResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Todo: 디자인 분들과 프론트 분들 필요한 정보 받아서 최적화 할 예정
 */
@Getter
@NoArgsConstructor
public class PlaceKakaoModel {
	List<PlaceKakaoData> documents;
	KakaoFeignMetaData meta;

	public PlaceSearchResponseDto mapByPlaceSearchResponseDto() {
		List<PlaceSearchModel> searchModels = new ArrayList<>();
		for (PlaceKakaoData document : documents) {

			PlaceSearchModel model = PlaceSearchModel.builder()
				.placeKakaoId(document.getId())
				.placeName(document.getPlaceName())
				.longitude(document.getX())
				.latitude(document.getY())
				.address(document.getRoadAddressName())
				.build();

			searchModels.add(model);
		}
		return new PlaceSearchResponseDto(searchModels);
	}

	public Place mapByPlaceEntity() {
		PlaceKakaoData placeKakaoData = readOne();
		return placeKakaoData.mapByPlaceEntity();
	}

	public PlaceKakaoData readOne() {
		final int PLACE_INDEX = 0;
		if (this.documents.isEmpty()) {
			throw new PlaceException(NOT_FOUND_PLACE_EXCEPTION);
		}
		return this.documents.get(PLACE_INDEX);
	}

}
