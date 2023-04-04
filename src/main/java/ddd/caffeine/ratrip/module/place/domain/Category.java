package ddd.caffeine.ratrip.module.place.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Getter;

@Getter
public enum Category {
	CAFE(List.of("CE7")),
	RESTAURANT(List.of("FD6")),
	TOURIST_ATTRACTION(List.of("AT4", "CT1")),
	ACCOMMODATION(List.of("AD5")),
	MART(List.of("MT1", "CS2")),
	ETC(List.of("ETC"));

	private List<String> code;

	Category(List<String> code) {
		this.code = code;
	}

	public static Category createByCode(String code) {
		Optional<Category> category = Arrays.stream(Category.values())
			.filter(c -> c.code.contains(code))
			.findFirst();
		return category.orElse(Category.ETC);
	}
}