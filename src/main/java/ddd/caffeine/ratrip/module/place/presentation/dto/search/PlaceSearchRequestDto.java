package ddd.caffeine.ratrip.module.place.presentation.dto.search;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import ddd.caffeine.ratrip.common.validator.RequestValidator;
import ddd.caffeine.ratrip.module.place.model.ThirdPartySearchOption;
import lombok.Getter;

@Getter
public class PlaceSearchRequestDto {
	@NotBlank(message = "Keyword must not be blank")
	private String keyword;

	@NotEmpty(message = "Latitude must not be blank")
	private String latitude;

	@NotEmpty(message = "Longitude must not be blank")
	private String longitude;

	private Integer page;

	private PlaceSearchRequestDto(String keyword, String latitude, String longitude, Integer page) {
		initPage(page);
		validateParameters(latitude, longitude, this.page);
		this.keyword = keyword;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public ThirdPartySearchOption mapByThirdPartySearchOption() {
		return ThirdPartySearchOption.builder()
			.keyword(this.keyword)
			.latitude(this.latitude)
			.longitude(this.longitude)
			.page(this.page)
			.build();
	}

	private void validateParameters(String latitude, String longitude, int page) {
		RequestValidator.validateRangeLatitude(latitude);
		RequestValidator.validateRangeLongitude(longitude);
		RequestValidator.validatePageSize(page);
	}

	private void initPage(Integer page) {
		final int DEFAULT_PAGE = 1;
		if (page == null) {
			this.page = DEFAULT_PAGE;
			return;
		}
		this.page = page;
	}
}
