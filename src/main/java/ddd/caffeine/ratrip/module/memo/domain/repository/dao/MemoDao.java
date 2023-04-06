package ddd.caffeine.ratrip.module.memo.domain.repository.dao;

import com.querydsl.core.annotations.QueryProjection;

import ddd.caffeine.ratrip.module.place.domain.Category;
import lombok.Getter;

@Getter
public class MemoDao {
	private final Long id;
	private final String name;
	private final Category category;
	private final String content;
	private final int sequence;

	@QueryProjection
	public MemoDao(Long id, String name, Category category, String content, int sequence) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.content = content;
		this.sequence = sequence;
	}
}
