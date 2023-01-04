package ddd.caffeine.ratrip.module.place.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Address {

	@NotNull
	@Enumerated(EnumType.STRING)
	private Region region;

	@NotNull
	@Column(name = "detailed_address", columnDefinition = "VARCHAR(100)")
	private String detailed;

	//예시 : "제주특별자치도 제주시 외도일동 640-2"
	public Address(String address) {
		setAddress(address);
	}

	public String toString() {
		return this.region.name() + " " + detailed;
	}

	private void setAddress(String address) {
		String[] split = address.split(" ");
		this.region = createRegion(split[0]);
		this.detailed = address.replace(split[0] + " ", "");
	}

	private Region createRegion(String keyword) {
		return Region.createRegionIfNotExistReturnEtc(keyword);
	}
}