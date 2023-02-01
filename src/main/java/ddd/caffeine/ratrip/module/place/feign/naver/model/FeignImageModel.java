package ddd.caffeine.ratrip.module.place.feign.naver.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeignImageModel {
	private List<ImageItem> items;

	public String readImageLinkByIndex(int index) {
		if (this.items.isEmpty()) {
			return null;
		}
		ImageItem imageItem = this.items.get(index);
		return imageItem.getLink();
	}
}
