package ddd.caffeine.ratrip.module.place.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Location {
	@NotNull
	@Column
	private double latitude;

	@NotNull
	@Column
	private double longitude;

	public static Location of(String longitude, String latitude) {
		return new Location(Double.parseDouble(latitude), Double.parseDouble(longitude));
	}
}
