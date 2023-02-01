package ddd.caffeine.ratrip.module.place.presentation.dto.bookmark;

import java.util.UUID;

import ddd.caffeine.ratrip.module.place.domain.bookmark.repository.dao.BookMarkPlaceDao;
import ddd.caffeine.ratrip.module.place.domain.sub_domain.Category;
import lombok.Getter;

@Getter
public class BookmarkPlaceResponse {
	private UUID id;
	private String name;
	private String detailAddress;
	private String imageUrl;
	private Category category;

	public BookmarkPlaceResponse(BookMarkPlaceDao dao) {
		this.id = dao.getId();
		this.name = dao.getName();
		this.detailAddress = dao.getDetailAddress();
		this.imageUrl = dao.getImageUrl();
		this.category = dao.getCategory();
	}
}
