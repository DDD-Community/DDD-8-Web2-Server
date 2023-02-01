package ddd.caffeine.ratrip.module.place.feign.naver.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import ddd.caffeine.ratrip.module.place.domain.sub_domain.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Naver API 를 통해 받아오는 블로그 객체.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCaseStrategy.class)
public class BlogItem {
	private String title;
	private String link;

	public Blog mapByBlogEntity() {
		return new Blog(title, link);
	}
}
