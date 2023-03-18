package ddd.caffeine.ratrip.module.auth.feign.kakao.model;

import lombok.Getter;

@Getter
public class KakaoAccount {
	private Profile profile;
	private String email;
}