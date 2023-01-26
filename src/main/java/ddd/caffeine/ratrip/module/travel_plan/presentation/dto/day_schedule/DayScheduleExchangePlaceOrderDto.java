package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ddd.caffeine.ratrip.common.validator.annotation.UUIDFormat;
import lombok.Getter;

@Getter
public class DayScheduleExchangePlaceOrderDto {
	@UUIDFormat
	private String id;
	@UUIDFormat
	private String exchangeId;

	public DayScheduleExchangePlaceOrderDto(String id, String exchangeId) {
		this.id = id;
		this.exchangeId = exchangeId;
	}

	public List<UUID> readPlaceUUIDs() {
		List<UUID> uuids = new ArrayList<>();
		uuids.add(UUID.fromString(id));
		uuids.add(UUID.fromString(exchangeId));

		return uuids;
	}
}
