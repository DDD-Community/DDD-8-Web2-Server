package ddd.caffeine.ratrip.module.place.feign.naver.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import ddd.caffeine.ratrip.module.place.domain.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FeignBlogModel {
	private List<BlogInfo> items;

	public List<Blog> toBlogs() {
		List<Blog> blogs = new ArrayList<>();
		for (BlogInfo info : items) {
			blogs.add(info.toEntity());
		}
		return blogs;
	}
}
