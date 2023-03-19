package ddd.caffeine.ratrip.common.util;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ddd.caffeine.ratrip.common.exception.domain.CommonException;
import ddd.caffeine.ratrip.module.travel_plan.domain.repository.dao.PlaceNameLongitudeLatitudeDao;
import ddd.caffeine.ratrip.module.travel_plan.presentation.dto.response.PlaceNameResponse;

public class ShortestPathCalculator {
	static final int EARTH_RADIUS = 6371000;

	public static List<PlaceNameResponse> byFloydWarshall(final UUID id,
		final List<PlaceNameLongitudeLatitudeDao> places) {

		PlaceNameLongitudeLatitudeDao startPlace = findStartPlace(id, places);
		return floydWarshall(initDistanceMatrix(places), startPlace, places);
	}

	private static double[][] initDistanceMatrix(List<PlaceNameLongitudeLatitudeDao> places) {
		double[][] distances = new double[places.size()][places.size()];

		for (int i = 0; i < places.size(); i++) {
			for (int j = 0; j < places.size(); j++) {
				distances[i][j] = haversineDistance(places.get(i), places.get(j));
			}
		}
		return distances;
	}

	private static List<PlaceNameResponse> mapToPlaceDistanceResponse(
		List<PlaceNameLongitudeLatitudeDao> visitOrder) {
		List<PlaceNameResponse> placeNameRespons = new ArrayList<>();
		for (PlaceNameLongitudeLatitudeDao place : visitOrder) {
			placeNameRespons.add(PlaceNameResponse.of(place.getName()));
		}

		return placeNameRespons;
	}

	private static List<PlaceNameResponse> floydWarshall(double[][] distanceMatrix,
		PlaceNameLongitudeLatitudeDao startPlace, List<PlaceNameLongitudeLatitudeDao> places) {
		List<PlaceNameLongitudeLatitudeDao> visitOrder = new ArrayList<>(); // 방문 순서를 저장할 리스트

		int n = distanceMatrix.length;
		boolean[] visited = new boolean[n];

		int currentPlaceIndex = findPlaceIndexById(places, startPlace.getId()); // 출발지 인덱스 구하기

		visited[currentPlaceIndex] = true; // 출발지 방문 처리
		visitOrder.add(places.get(currentPlaceIndex));

		for (int i = 0; i < n - 1; i++) { // 현재 위치로부터 가장 가까운 장소 찾기
			double shortestDistance = Double.MAX_VALUE;
			int closestPlaceIndex = -1;

			for (int j = 0; j < n; j++) {
				if (!visited[j] && distanceMatrix[currentPlaceIndex][j] < shortestDistance) {
					closestPlaceIndex = j;
					shortestDistance = distanceMatrix[currentPlaceIndex][j];
				}
			}

			visited[closestPlaceIndex] = true;
			visitOrder.add(places.get(closestPlaceIndex));
			currentPlaceIndex = closestPlaceIndex;
		}
		return mapToPlaceDistanceResponse(visitOrder);
	}

	private static PlaceNameLongitudeLatitudeDao findStartPlace(final UUID id,
		final List<PlaceNameLongitudeLatitudeDao> places) {

		return places.stream()
			.filter(p -> p.getId().equals(id))
			.findFirst()
			.orElseThrow(() -> new CommonException(NOT_FOUND_PLACE_EXCEPTION));
	}

	private static double haversineDistance(final PlaceNameLongitudeLatitudeDao originPlace,
		final PlaceNameLongitudeLatitudeDao place) {

		double deltaLatitude = Math.toRadians(place.getLatitude() - originPlace.getLatitude());
		double deltaLongitude = Math.toRadians(place.getLongitude() - originPlace.getLongitude());

		double latitudeOriginRadians = Math.toRadians(originPlace.getLatitude());
		double latitudeCompareRadians = Math.toRadians(place.getLatitude());

		// 두 좌표 사이의 거리 계산 (haversine 공식 사용)
		double a = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2) + Math.cos(latitudeOriginRadians)
			* Math.cos(latitudeCompareRadians) * Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c;
	}

	private static int findPlaceIndexById(List<PlaceNameLongitudeLatitudeDao> places, UUID id) {
		for (int i = 0; i < places.size(); i++) {
			if (places.get(i).getId().equals(id)) {
				return i;
			}
		}
		return -1;
	}
}
