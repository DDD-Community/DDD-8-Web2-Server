package ddd.caffeine.ratrip.module.place.presentation.dto.detail;

import javax.validation.constraints.NotBlank;

import ddd.caffeine.ratrip.common.validator.RequestDataValidator;
import ddd.caffeine.ratrip.module.place.model.ThirdPartyDetailSearchOption;
import lombok.Getter;

@Getter
public class PlaceDetailsByThirdPartyRequestDto {

	@NotBlank(message = "Id must not be blank")
	private String thirdPartyId;

	@NotBlank(message = "PlaceName must not be blank")
	private String placeName;

	@NotBlank(message = "Address must not be blank")
	private String address;

	public PlaceDetailsByThirdPartyRequestDto(String thirdPartyId, String placeName,
		String address) {
		validateParameters(thirdPartyId, address);
		this.thirdPartyId = thirdPartyId;
		this.placeName = placeName;
		this.address = address;
	}

	public ThirdPartyDetailSearchOption mapByThirdPartyDetailSearchOption() {
		return ThirdPartyDetailSearchOption.builder()
			.id(this.thirdPartyId)
			.placeName(this.placeName)
			.address(this.address)
			.build();
	}

	private void validateParameters(String id, String address) {
		RequestDataValidator.validateIsNumber(id);
		//@ToDo : 지번주소인지 도로명주소인지 정하기 -> 01.11 회의 추후 정해서 알려주신다고 함.
	}
}
