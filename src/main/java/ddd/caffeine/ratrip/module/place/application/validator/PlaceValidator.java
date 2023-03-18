package ddd.caffeine.ratrip.module.place.application.validator;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.Optional;

import org.springframework.stereotype.Component;

import ddd.caffeine.ratrip.common.exception.domain.PlaceException;
import ddd.caffeine.ratrip.module.place.domain.Place;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.PlaceBookmarkDao;
import ddd.caffeine.ratrip.module.place.domain.repository.dao.PlaceDetailBookmarkDao;

@Component
public class PlaceValidator {
	public Place validateExistPlace(Optional<Place> place) {
		return place.orElseThrow(() -> new PlaceException(NOT_FOUND_PLACE_EXCEPTION));
	}

	public void validateExistPlaceDetailDao(PlaceDetailBookmarkDao place) {
		if (place == null) {
			throw new PlaceException(NOT_FOUND_PLACE_EXCEPTION);
		}
	}

	public void validateExistPlaceDao(PlaceBookmarkDao place) {
		if (place == null) {
			throw new PlaceException(NOT_FOUND_PLACE_EXCEPTION);
		}
	}
}
