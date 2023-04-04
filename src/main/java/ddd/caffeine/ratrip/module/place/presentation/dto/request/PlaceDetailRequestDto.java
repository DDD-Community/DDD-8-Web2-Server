package ddd.caffeine.ratrip.module.place.presentation.dto.request;

import javax.validation.constraints.NotBlank;

import ddd.caffeine.ratrip.module.place.application.dto.PlaceDetailDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceDetailRequestDto {

	@NotBlank(message = "id must not be blank")
	private String id;

	@NotBlank(message = "PlaceName must not be blank")
	private String name;

	@NotBlank(message = "Address must not be blank")
	private String address;

	public PlaceDetailDto toServiceDto() {
		return PlaceDetailDto.of(id, name, address);
	}
}
