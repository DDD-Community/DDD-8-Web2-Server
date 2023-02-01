package ddd.caffeine.ratrip.module.place.feign.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import ddd.caffeine.ratrip.common.configuration.FeignConfiguration;
import ddd.caffeine.ratrip.module.place.feign.kakao.model.FeignPlaceModel;

@FeignClient(name = "KakaoFeignClient", url = "https://dapi.kakao.com", configuration = FeignConfiguration.class)
public interface KakaoFeignClient {
	@GetMapping(value = "v2/local/search/keyword.json")
	FeignPlaceModel readPlacesByKeywordInRadius(
		@RequestHeader("Authorization") String header,
		@RequestParam("query") String query,
		@RequestParam("y") String latitude,
		@RequestParam("x") String longitude,
		@RequestParam("radius") int radius,
		@RequestParam("page") int page
	);

	@GetMapping(value = "v2/local/search/keyword.json")
	FeignPlaceModel readPlaceByKeyword(
		@RequestHeader("Authorization") String header,
		@RequestParam("query") String query,
		@RequestParam("size") int size
	);
}
