package ddd.caffeine.ratrip.module.place.presentation.dto.request;

import javax.validation.constraints.NotNull;

import ddd.caffeine.ratrip.common.RequestDataValidator;
import ddd.caffeine.ratrip.module.place.application.dto.PlaceByCoordinateDto;
import lombok.Getter;

@Getter
public class PlaceCoordinateRequestDto {
	@NotNull(message = "Latitude must not be null")
	private Double latitude;

	@NotNull(message = "Longitude must not be null")
	private Double longitude;

	public PlaceCoordinateRequestDto(Double latitude, Double longitude) {
		validateParameters(latitude, longitude);
		this.latitude = latitude;
		this.longitude = longitude;
	}

	private void validateParameters(double latitude, double longitude) {
		RequestDataValidator.validateRangeLatitude(latitude);
		RequestDataValidator.validateRangeLongitude(longitude);
	}

	public PlaceByCoordinateDto toServiceDto() {
		return PlaceByCoordinateDto.of(latitude, longitude);
	}
}
