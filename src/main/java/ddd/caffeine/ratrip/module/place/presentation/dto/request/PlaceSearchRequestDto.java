package ddd.caffeine.ratrip.module.place.presentation.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.module.place.application.dto.SearchPlaceDto;
import lombok.Getter;

@Getter
public class PlaceSearchRequestDto {
	@NotBlank(message = "Keyword must not be blank")
	private String keyword;

	@NotNull(message = "Latitude must not be null")
	private Double latitude;

	@NotNull(message = "Latitude must not be null")
	private Double longitude;

	@Min(1)
	private Integer page;

	private PlaceSearchRequestDto(String keyword, Double latitude, Double longitude, Integer page) {
		initPage(page);
		this.keyword = keyword;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	private void initPage(Integer page) {
		final int DEFAULT_PAGE = 1;
		if (page == null || page < DEFAULT_PAGE) {
			this.page = DEFAULT_PAGE;
			return;
		}
		this.page = page;
	}

	public SearchPlaceDto toServiceDto() {
		return SearchPlaceDto.of(keyword, latitude, longitude, page);
	}
}