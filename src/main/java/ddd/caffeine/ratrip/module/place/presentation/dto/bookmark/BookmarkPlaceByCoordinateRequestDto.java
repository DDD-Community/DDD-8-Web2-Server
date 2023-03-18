package ddd.caffeine.ratrip.module.place.presentation.dto.bookmark;

import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.common.validator.RequestDataValidator;
import ddd.caffeine.ratrip.module.place.application.dto.BookmarkPlaceByRegionDto;
import lombok.Getter;

@Getter
public class BookmarkPlaceByCoordinateRequestDto {
	@NotNull(message = "Latitude must not be null")
	private final double latitude;

	@NotNull(message = "Longitude must not be null")
	private final double longitude;

	public BookmarkPlaceByCoordinateRequestDto(double latitude, double longitude) {
		validateParameters(latitude, longitude);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public BookmarkPlaceByRegionDto toServiceDto() {
		return BookmarkPlaceByRegionDto.of(latitude, longitude);
	}

	private void validateParameters(double latitude, double longitude) {
		RequestDataValidator.validateRangeLatitude(latitude);
		RequestDataValidator.validateRangeLongitude(longitude);
	}
}
