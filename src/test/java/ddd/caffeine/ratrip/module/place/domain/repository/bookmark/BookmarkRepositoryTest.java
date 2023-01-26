package ddd.caffeine.ratrip.module.place.domain.repository.bookmark;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import ddd.caffeine.ratrip.TestConfig;
import ddd.caffeine.ratrip.module.place.domain.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.repository.PlaceRepository;
import ddd.caffeine.ratrip.module.user.domain.User;
import ddd.caffeine.ratrip.module.user.domain.UserSocialType;
import ddd.caffeine.ratrip.module.user.domain.UserStatus;
import ddd.caffeine.ratrip.module.user.domain.repository.UserRepository;

@DataJpaTest
@Import(TestConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookmarkRepositoryTest {

	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	PlaceRepository placeRepository;
	@Autowired
	UserRepository userRepository;

	@Test
	@DisplayName("찾고자 하는 북마크가 존재 하지 않을 경우")
	void findByUserAndPlaceIfNULLTest() {
		//given
		User user = User.of("name", "email", UserStatus.ACTIVE, "socialId", UserSocialType.KAKAO);
		Place place = createPlace("testId", "양재 스타벅스", "서울 양재동 스타벅스 까페", "CF7", 1, 1,
			"testLink", "testLink", "testPhoneNumber");
		userRepository.save(user);
		placeRepository.save(place);

		//when
		Bookmark bookmark = bookmarkRepository.findByUserAndPlace(user, place);

		//then
		assertThat(bookmark).isNull();
	}

	@Test
	@DisplayName("북마크 삭제 정상 동작 테스트")
	void deleteBookMarkTest() {
		//given
		User user = User.of("name", "email", UserStatus.ACTIVE, "socialId", UserSocialType.KAKAO);
		Place place = createPlace("testId", "양재 스타벅스", "서울 양재동 스타벅스 까페", "CF7", 1, 1,
			"testLink", "testLink", "testPhoneNumber");
		userRepository.save(user);
		placeRepository.save(place);
		Bookmark bookmark = Bookmark.of(user, place);
		bookmarkRepository.save(bookmark);

		//when
		Long execute = bookmarkRepository.deleteBookMark(bookmark);

		//then
		assertThat(execute).isEqualTo(1);
	}

	/**
	 * place 엔티티 생성 메서드.
	 */
	private Place createPlace(String kakaoId, String name, String address, String categoryCode,
		double x, double y, String imageLink, String additionalInfoLink, String telephone) {
		Place place = Place.builder()
			.kakaoId(kakaoId)
			.name(name)
			.additionalInfoLink(additionalInfoLink)
			.telephone(telephone)
			.build();

		place.injectImageLink(imageLink);
		place.setPlaceCategory(categoryCode);
		place.setAddress(address);
		place.setLocation(y, x);

		return place;
	}
}