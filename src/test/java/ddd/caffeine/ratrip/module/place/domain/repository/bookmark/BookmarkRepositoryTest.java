package ddd.caffeine.ratrip.module.place.domain.repository.bookmark;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import ddd.caffeine.ratrip.TestConfig;
import ddd.caffeine.ratrip.module.place.domain.Bookmark;
import ddd.caffeine.ratrip.module.place.domain.BookmarkId;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.repository.BookmarkRepository;
import ddd.caffeine.ratrip.module.place.domain.repository.PlaceRepository;
import ddd.caffeine.ratrip.module.user.domain.User;
import ddd.caffeine.ratrip.module.user.domain.UserSocialType;
import ddd.caffeine.ratrip.module.user.domain.UserStatus;
import ddd.caffeine.ratrip.module.user.domain.repository.UserRepository;

@DataJpaTest
@Import(TestConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookmarkRepositoryTest {
	@Autowired
	BookmarkRepository bookmarkRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PlaceRepository placeRepository;

	User TEST_USER;
	Place TEST_PLACE;

	@BeforeAll
	@DisplayName("TEST_USER, TEST_PLACE 초기화 및 DB 저장")
	void init() {
		TEST_USER = createUser("name", "email", UserStatus.ACTIVE, "socialId", UserSocialType.KAKAO);
		TEST_PLACE = createPlaceExceptNotNullField("thirdPartyID", "양재 스타벅스", "서울 양재동 스타벅스 까페", "CF7", 1, 1);

		userRepository.save(TEST_USER);
		placeRepository.save(TEST_PLACE);
	}

	@Test
	@DisplayName("찾고자 하는 북마크가 존재 하지 않을 경우")
	void findByIdIfNULLTest() {
		//given
		BookmarkId bookmarkId = new BookmarkId(TEST_USER.getId(), TEST_PLACE.getId());
		//when
		Bookmark entity = bookmarkRepository.findByBookmarkId(bookmarkId);
		//then
		assertThat(entity).isNull();
	}

	@Test
	@DisplayName("validate method test")
	void existsBookmarkTest() {
		//given
		BookmarkId bookmarkId = new BookmarkId(TEST_USER.getId(), TEST_PLACE.getId());
		Bookmark bookmark = createBookmark(TEST_USER, TEST_PLACE);
		bookmarkRepository.save(bookmark);

		//when
		boolean exist = bookmarkRepository.existsByBookmarkId(bookmarkId);

		//then
		assertThat(exist).isTrue();
	}

	private Bookmark createBookmark(User user, Place place) {
		return Bookmark.builder()
			.user(user)
			.place(place)
			.build();
	}

	/**
	 * place 엔티티 생성 메서드 (NotNull 조건 걸린 필드만 생성)
	 */
	private Place createPlaceExceptNotNullField(String thirdPartyID, String name, String address, String categoryCode,
		double x, double y) {
		Place place = Place.builder()
			.kakaoId(thirdPartyID)
			.name(name)
			.build();

		place.setCategoryByCode(categoryCode);
		place.setAddress(address);
		place.setLocation(y, x);
		return place;
	}

	/**
	 * User 엔티티 생성 메서드.
	 */
	private User createUser(String name, String email, UserStatus status, String socialId, UserSocialType socialType) {
		return User.builder()
			.name(name)
			.email(email)
			.status(status)
			.socialId(socialId)
			.socialType(socialType)
			.build();
	}
}