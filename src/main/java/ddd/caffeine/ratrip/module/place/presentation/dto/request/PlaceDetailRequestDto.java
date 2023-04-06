package ddd.caffeine.ratrip.module.place.presentation.dto.request;

import javax.validation.constraints.NotBlank;

import ddd.caffeine.ratrip.module.place.application.dto.PlaceDetailDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PlaceDetailRequestDto {

	@NotBlank(message = "kakaoId must not be blank")
	private String kakaoId;

	@NotBlank(message = "PlaceName must not be blank")
	private String name;

	@NotBlank(message = "Address must not be blank")
	private String address;

	public PlaceDetailDto toServiceDto() {
		return PlaceDetailDto.of(kakaoId, name, address);
	}
}
