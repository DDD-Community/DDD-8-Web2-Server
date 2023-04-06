package ddd.caffeine.ratrip.module.place.feign.naver.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeignImageModel {
	private List<ImageItem> items;

	public String toImageLink() {
		final int INDEX = 0;

		if (this.items.isEmpty()) {
			return null;
		}
		ImageItem imageItem = this.items.get(INDEX);
		return imageItem.getLink();
	}
}
