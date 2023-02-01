package ddd.caffeine.ratrip.module.place.domain.sub_domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.Getter;

/**
 * 장소에 대한 카테고리를 나타내는 enum 클래스.
 *
 * @Todo : 카테고리 코드 없을 가능성 생각.
 */
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

	//TODO - 인자를 Enum 타입으로 받는 법 알아보기
	public static List<Category> createCategories(List<String> categories) {
		List<Category> response = new ArrayList<>();
		for (String categoryName : categories) {
			Optional<Category> category = Arrays.stream(values()).filter(
				c -> c.name().equals(categoryName)).findFirst();

			category.ifPresent(c -> response.add(c));
		}
		return response;
	}

	public static Category createByCode(String code) {
		Optional<Category> category = Arrays.stream(Category.values())
			.filter(c -> c.code.contains(code))
			.findFirst();
		if (category.isPresent()) {
			return category.get();
		}
		return Category.ETC;
	}
}
