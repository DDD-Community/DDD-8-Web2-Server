package ddd.caffeine.ratrip.module.travel_plan.place.feign.naver.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Todo 프론트 및 디자이너 분들과 추후 필드 추가 및 삭제에 관한 논의
 * <p>
 * Naver API 를 통해 받아오는 이미지 객체.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.LowerCaseStrategy.class)
public class ImageItem {
	private String link;
	private String thumbnail;
	private String sizeHeight;
	private String sizeWidth;
}
