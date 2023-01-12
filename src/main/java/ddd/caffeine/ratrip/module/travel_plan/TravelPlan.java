package ddd.caffeine.ratrip.module.travel_plan;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import ddd.caffeine.ratrip.module.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class TravelPlan {

	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@Column
	@Enumerated(EnumType.STRING)
	private Region region;

	@Column
	private int travelDays;
}
