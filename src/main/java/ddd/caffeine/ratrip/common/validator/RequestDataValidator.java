package ddd.caffeine.ratrip.common.validator;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.regex.Pattern;

import ddd.caffeine.ratrip.common.exception.domain.FeignException;
import ddd.caffeine.ratrip.common.exception.domain.PlaceException;

public class RequestDataValidator {
	public static void validateLotNumberAddress(String address) {
		final Pattern LOT_NUMBER_ADDRESS = Pattern.compile("(.+[가-힣A-Za-z·\\d\\-\\.]{2,}(읍|면|동|리).[\\d\\-]+)");

		if (!(LOT_NUMBER_ADDRESS.matcher(address).matches())) {
			throw new PlaceException(INVALID_ADDRESS_EXCEPTION);
		}
	}

	public static void validateRoadNameAddress(String address) {
		final Pattern ROAD_NAME_ADDRESS = Pattern.compile("(.+[가-힣A-Za-z·\\d\\-\\.]{2,}(로|길).\\d+)");

		if (!(ROAD_NAME_ADDRESS.matcher(address).matches())) {
			throw new PlaceException(INVALID_ADDRESS_EXCEPTION);
		}
	}

	public static void validateIsNumber(String number) {
		try {
			Long.parseLong(number);
		} catch (NumberFormatException e) {
			throw new PlaceException(INVALID_THIRD_PARTY_ID_EXCEPTION);
		}
	}

	public static void validateUUIDForm(String uuid) {
		final Pattern UUID_PATTERN = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
		if (!(UUID_PATTERN.matcher(uuid).matches())) {
			throw new PlaceException(INVALID_UUID_FORM_EXCEPTION);
		}
	}

	public static void validatePageSize(int page) {
		final int MIN_PAGE = 1;
		final int MAX_PAGE = 45;
		if (page < MIN_PAGE || page > MAX_PAGE) {
			throw new FeignException(KAKAO_PAGE_NUMBER_EXCEPTION);
		}
	}

	public static void validateRangeLatitude(String lat) {
		double latitude = validateTypeCastDouble(lat);
		if (!(-90 <= latitude && latitude <= 90)) {
			throw new PlaceException(INVALID_LATITUDE_RANGE_EXCEPTION);
		}
	}

	public static void validateRangeLongitude(String lng) {
		double longitude = validateTypeCastDouble(lng);
		if (!(-180 <= longitude && longitude <= 180)) {
			throw new PlaceException(INVALID_LONGITUDE_RANGE_EXCEPTION);
		}
	}

	private static double validateTypeCastDouble(String param) {
		try {
			return Double.parseDouble(param);
		} catch (NumberFormatException e) {
			throw new PlaceException(INVALID_COORDINATE_EXCEPTION);
		}
	}
}
