package ddd.caffeine.ratrip.module.place.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	@NotNull
	@Enumerated(EnumType.STRING)
	private Region region;

	@NotNull
	@Column(name = "detailed_address", columnDefinition = "VARCHAR(100)")
	private String detailed;

	//address 예시 : "제주특별자치도 제주시 외도일동 640-2"
	public static Address of(String addressName) {
		Region region = Region.of(addressName.split(" ")[0]);
		return new Address(region, addressName);
	}
}
