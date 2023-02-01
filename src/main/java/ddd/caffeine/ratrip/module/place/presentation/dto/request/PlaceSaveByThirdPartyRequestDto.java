package ddd.caffeine.ratrip.module.place.presentation.dto.request;

import javax.validation.constraints.NotBlank;

import ddd.caffeine.ratrip.common.validator.annotation.Number;
import ddd.caffeine.ratrip.module.place.domain.ThirdPartyDetailSearchOption;
import lombok.Getter;

@Getter
public class PlaceSaveByThirdPartyRequestDto {

	@Number
	@NotBlank(message = "id must not be blank")
	private String id;

	@NotBlank(message = "PlaceName must not be blank")
	private String name;

	@NotBlank(message = "Address must not be blank")
	private String address;

	public PlaceSaveByThirdPartyRequestDto(String thirdPartyId, String placeName,
		String address) {
		validateParameters(address);
		this.id = thirdPartyId;
		this.name = placeName;
		this.address = address;
	}

	public ThirdPartyDetailSearchOption mapByThirdPartyDetailSearchOption() {
		return ThirdPartyDetailSearchOption.builder()
			.id(this.id)
			.placeName(this.name)
			.address(this.address)
			.build();
	}

	private void validateParameters(String address) {
		//@ToDo : 지번주소인지 도로명주소인지 정하기 -> 01.11 회의 추후 정해서 알려주신다고 함.
	}
}
