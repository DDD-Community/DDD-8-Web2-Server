package ddd.caffeine.ratrip.module.place.feign.naver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import ddd.caffeine.ratrip.common.configuration.FeignConfiguration;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignBlogModel;
import ddd.caffeine.ratrip.module.place.feign.naver.model.FeignImageModel;

@FeignClient(name = "NaverFeignClient", url = "https://openapi.naver.com", configuration = FeignConfiguration.class)
public interface NaverFeignClient {
	@GetMapping(value = "v1/search/image", produces = "application/json")
	FeignImageModel readImageModelByPlaceName(
		@RequestHeader("X-Naver-Client-Id") String clientId,
		@RequestHeader("X-Naver-Client-Secret") String clientSecret,
		@RequestParam("query") String placeName,
		@RequestParam("display") int count,
		@RequestParam("sort") String sortType,
		@RequestParam("filter") String sizeFilter
	);

	@GetMapping(value = "v1/search/blog.json", produces = "application/json")
	FeignBlogModel readBlogModelsByKeyword(
		@RequestHeader("X-Naver-Client-Id") String clientId,
		@RequestHeader("X-Naver-Client-Secret") String clientSecret,
		@RequestParam("query") String keyword,
		@RequestParam("display") int count,
		@RequestParam("sort") String sortType
	);
}
