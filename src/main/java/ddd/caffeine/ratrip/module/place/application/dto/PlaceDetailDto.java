package ddd.caffeine.ratrip.module.place.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PlaceDetailDto {
	private String kakaoId;
	private String name;
	private String address;

	public static PlaceDetailDto of(String kakaoId, String name, String address) {
		return new PlaceDetailDto(kakaoId, name, address);
	}
}
