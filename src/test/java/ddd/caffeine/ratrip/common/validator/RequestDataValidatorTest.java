package ddd.caffeine.ratrip.common.validator;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.fasterxml.uuid.Generators;

import ddd.caffeine.ratrip.common.exception.domain.FeignException;
import ddd.caffeine.ratrip.common.exception.domain.PlaceException;

class RequestDataValidatorTest {
	RequestDataValidator requestValidator;

	@Test
	@DisplayName("UUID 형식 정상 검증 테스트")
	void validateUUIDFormTest() {
		//given
		String uuid = generateUUID();
		//then
		requestValidator.validateUUIDForm(uuid);
	}

	@ParameterizedTest
	@DisplayName("올바르지 않은 지번주소(도로명주소) 일 때 예외 동작 테스트")
	@ValueSource(strings = {"경기 양평군 양서면 북한강로89번길 16", "인천 중구 마시란로 163", "경기 광주시 남한산성면 검복길 82"})
	void validateLotNumberAddressThrowException(String address) {
		//then
		assertThatThrownBy(() ->
			requestValidator.validateLotNumberAddress(address))
			.isInstanceOf(PlaceException.class)
			.hasMessage("올바르지 않은 주소 형식입니다.");
	}

	@ParameterizedTest
	@DisplayName("올바르지 않은 도로명 주소(지번주소) 일 때 예외 동작 테스트")
	@ValueSource(strings = {"충남 천안시 서북구 쌍용동 541-4", "경기 화성시 장지동 479", "충북 단양군 가곡면 사평리 246-33"})
	void validateLotNumberAddress(String address) {
		//then
		requestValidator.validateLotNumberAddress(address);
	}

	@ParameterizedTest
	@DisplayName("올바른 도로명 주소 일때 정상 동작 테스트")
	@ValueSource(strings = {"경기 양평군 양서면 북한강로89번길 16", "인천 중구 마시란로 163", "경기 광주시 남한산성면 검복길 82"})
	void validateRoadNameAddressTest(String address) {
		//then
		requestValidator.validateRoadNameAddress(address);
	}

	@ParameterizedTest
	@DisplayName("올바르지 않은 도로명주소(지번주소) 일 때 예외 동작 테스트")
	@ValueSource(strings = {"충남 천안시 서북구 쌍용동 541-4", "경기 화성시 장지동 479", "충북 단양군 가곡면 사평리 246-33"})
	void validateRoadNameAddressThrowException(String address) {
		//then
		assertThatThrownBy(() ->
			requestValidator.validateRoadNameAddress(address))
			.isInstanceOf(PlaceException.class)
			.hasMessage("올바르지 않은 주소 형식입니다.");
	}

	@ParameterizedTest
	@DisplayName("페이징 사이즈 유효성 검사 테스트")
	@ValueSource(ints = {-1, 0, 46, 1000000})
	void validatePageSizeTest(int pageSize) {
		//then
		assertThatThrownBy(() ->
			requestValidator.validatePageSize(pageSize))
			.isInstanceOf(FeignException.class)
			.hasMessage("Page 는 1 이상 45 이하 여야 합니다.");
	}

	@ParameterizedTest
	@DisplayName("위도 범위 유효성 검사 테스트")
	@ValueSource(strings = {"100", "-90.01", "90.93"})
	void validateLatitudeSize(String latitude) {
		//then
		assertThatThrownBy(() ->
			requestValidator.validateRangeLatitude(latitude))
			.isInstanceOf(PlaceException.class)
			.hasMessage("위도는 -90 ~ 90 까지 범위안에 존재해야합니다.");
	}

	@ParameterizedTest
	@DisplayName("경도 범위 유효성 검사 테스트")
	@ValueSource(strings = {"190", "-180.01", "180.93"})
	void validateLongitudeSize(String longitude) {
		//then
		assertThatThrownBy(() ->
			requestValidator.validateRangeLongitude(longitude))
			.isInstanceOf(PlaceException.class)
			.hasMessage("경도는 -180 ~ 180 까지 범위안에 존재해야합니다.");
	}

	@ParameterizedTest
	@DisplayName("좌표 유효성 검사 테스트")
	@ValueSource(strings = {"test", "3..321341.", "한국"})
	void validateCoordinate(String coordinate) {
		//then
		assertThatThrownBy(() ->
			requestValidator.validateRangeLatitude(coordinate))
			.isInstanceOf(PlaceException.class)
			.hasMessage("좌표는 숫자여야합니다.");
	}

	private String generateUUID() {
		UUID uuid = Generators.timeBasedGenerator().generate();
		String[] uuidArr = uuid.toString().split("-");
		String uuidStr = uuidArr[2] + uuidArr[1] + uuidArr[0] + uuidArr[3] + uuidArr[4];
		StringBuilder sb = new StringBuilder(uuidStr);
		sb.insert(8, "-");
		sb.insert(13, "-");
		sb.insert(18, "-");
		sb.insert(23, "-");
		return sb.toString();
	}
}