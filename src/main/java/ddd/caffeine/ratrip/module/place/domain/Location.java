package ddd.caffeine.ratrip.module.place.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Location {
	@NotNull
	@Column
	private double latitude;

	@NotNull
	@Column
	private double longitude;

	private Location(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public static Location of(String latitude, String longitude) {
		return new Location(Double.parseDouble(latitude), Double.parseDouble(longitude));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Location location = (Location)o;
		return Double.compare(location.latitude, latitude) == 0
			&& Double.compare(location.longitude, longitude) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(latitude, longitude);
	}
}
