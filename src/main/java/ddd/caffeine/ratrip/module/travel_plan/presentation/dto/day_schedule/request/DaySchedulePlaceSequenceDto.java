package ddd.caffeine.ratrip.module.travel_plan.presentation.dto.day_schedule.request;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ddd.caffeine.ratrip.common.validator.RequestDataValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DaySchedulePlaceSequenceDto {
	private List<String> ids;

	public List<UUID> readUUIDs() {
		List<UUID> uuids = new ArrayList<>();
		for (String id : this.ids) {
			RequestDataValidator.validateUUIDForm(id);
			uuids.add(UUID.fromString(id));
		}
		return uuids;
	}
}
