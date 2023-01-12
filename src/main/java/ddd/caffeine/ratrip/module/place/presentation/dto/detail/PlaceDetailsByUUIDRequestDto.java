package ddd.caffeine.ratrip.module.place.presentation.dto.detail;

import javax.validation.constraints.NotBlank;

import ddd.caffeine.ratrip.common.validator.RequestDataValidator;
import lombok.Getter;

@Getter
public class PlaceDetailsByUUIDRequestDto {
	@NotBlank(message = "Id must not be blank")
	private String id;

	public PlaceDetailsByUUIDRequestDto(String id) {
		validateParameters(id);
		this.id = id;
	}

	private void validateParameters(String id) {
		RequestDataValidator.validateUUIDForm(id);
	}
}
