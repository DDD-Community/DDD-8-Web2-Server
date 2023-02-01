package ddd.caffeine.ratrip.module.place.feign.naver.model;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.sub_domain.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeignBlogModel {
	private List<BlogItem> items;

	public List<Blog> readBlogs() {
		List<Blog> blogs = new ArrayList<>();
		for (BlogItem item : items) {
			blogs.add(item.mapByBlogEntity());
		}
		return blogs;
	}
}
