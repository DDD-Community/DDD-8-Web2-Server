package ddd.caffeine.ratrip.module.travel_plan.place.presentation.dto.response;

import java.util.List;

import ddd.caffeine.ratrip.module.travel_plan.place.domain.Blog;
import ddd.caffeine.ratrip.module.travel_plan.place.domain.Location;
import ddd.caffeine.ratrip.module.travel_plan.place.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Todo : 프론트 및 디자인분들과 해당 필드 상의
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDetailResponseDto {

	private Long id;
	private String kakaoId;
	private String name;
	private String category;
	private String address;
	private Location location;
	private String imageLink;
	private String placeLink;
	private String telephone;
	private List<Blog> blogs;

	public static PlaceDetailResponseDto of(Place place) {
		return new PlaceDetailResponseDto(
			place.getId(),
			place.getKakaoId(),
			place.getName(),
			place.getCategory().name(),
			place.getAddress().toString(),
			place.getLocation(),
			place.getImageLink(),
			place.getPlaceLink(),
			place.getTelephone(),
			place.getBlogs()
		);
	}
}
