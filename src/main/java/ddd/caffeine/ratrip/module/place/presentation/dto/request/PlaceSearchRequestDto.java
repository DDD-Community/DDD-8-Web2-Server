package ddd.caffeine.ratrip.module.place.presentation.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.common.validator.RequestDataValidator;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartySearchOption;
import lombok.Getter;

@Getter
public class PlaceSearchRequestDto {
	@NotBlank(message = "Keyword must not be blank")
	private String keyword;

	@NotNull(message = "Latitude must not be null")
	private double latitude;

	@NotNull(message = "Latitude must not be null")
	private double longitude;

	@Min(1)
	private Integer page;

	private PlaceSearchRequestDto(String keyword, double latitude, double longitude, Integer page) {
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

	private void validateParameters(double latitude, double longitude, int page) {
		RequestDataValidator.validateRangeLatitude(latitude);
		RequestDataValidator.validateRangeLongitude(longitude);
		RequestDataValidator.validatePageSize(page);
	}

	private void initPage(Integer page) {
		final int DEFAULT_PAGE = 1;
		if (page == null || page < DEFAULT_PAGE) {
			this.page = DEFAULT_PAGE;
			return;
		}
		this.page = page;
	}
}
