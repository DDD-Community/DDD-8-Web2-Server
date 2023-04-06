package ddd.caffeine.ratrip.module.place.feign.naver.model;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.module.place.domain.Blog;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FeignBlogModel {
	private List<BlogInfo> infos;

	public List<Blog> toBlogs() {
		List<Blog> blogs = new ArrayList<>();
		for (BlogInfo info : infos) {
			blogs.add(info.toEntity());
		}
		return blogs;
	}
}
