package ddd.caffeine.ratrip.common.util;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;

import java.util.ArrayList;
import java.util.List;

import ddd.caffeine.ratrip.common.exception.domain.MemoException;
import ddd.caffeine.ratrip.module.memo.domain.Memo;

public class RecommendationPathCalculator {
	static final int EARTH_RADIUS = 6371000;

	// 위도, 경도로 각 경로간 거리를 계산하고, 그 중 가장 짧은 경로를 Greedy 하게 찾아서 정렬 (A, B, C, D가 있고 출발지가 A라면 A에서 가장 가까운 B를 찾고, B에서 가장 가까운 C를 찾고, C에서 가장 가까운 D를 찾는 방식)
	public static List<Memo> byGreedyAlgorithm(Long memoId, List<Memo> memos) {
		List<Memo> visitOrder = new ArrayList<>();

		int n = memos.size();
		boolean[] visited = new boolean[n];

		Memo startPlace = findStartPlace(memoId, memos);
		int startPlaceIndex = findPlaceIndexById(memos, startPlace.getId());

		visited[startPlaceIndex] = true; // 출발지 방문 처리
		visitOrder.add(memos.get(startPlaceIndex));

		for (int i = 0; i < n - 1; i++) { // 현재 위치로부터 가장 가까운 장소 찾기
			double shortestDistance = Double.MAX_VALUE;
			int closestPlaceIndex = -1;

			for (int j = 0; j < n; j++) {
				if (!visited[j] && haversineDistance(startPlace, memos.get(j)) < shortestDistance) {
					closestPlaceIndex = j;
					shortestDistance = haversineDistance(startPlace, memos.get(j));
				}
			}

			visited[closestPlaceIndex] = true;
			visitOrder.add(memos.get(closestPlaceIndex));
			startPlace = memos.get(closestPlaceIndex);
		}

		return visitOrder;
	}

	private static Memo findStartPlace(Long memoId, List<Memo> memos) {
		return memos.stream()
			.filter(memo -> memo.getId().equals(memoId))
			.findFirst()
			.orElseThrow(() -> new MemoException(NOT_FOUND_MEMO_EXCEPTION));
	}

	private static int findPlaceIndexById(List<Memo> memos, Long id) {
		for (int i = 0; i < memos.size(); i++) {
			if (memos.get(i).getId().equals(id)) {
				return i;
			}
		}

		return -1;
	}

	private static double haversineDistance(Memo originPlace, Memo place) {
		double deltaLatitude = Math.toRadians(
			place.getLocation().getLatitude() - originPlace.getLocation().getLatitude());

		double deltaLongitude = Math.toRadians(
			place.getLocation().getLongitude() - originPlace.getLocation().getLongitude());

		double latitudeOriginRadians = Math.toRadians(originPlace.getLocation().getLatitude());
		double latitudeCompareRadians = Math.toRadians(place.getLocation().getLatitude());

		// 두 좌표 사이의 거리 계산 (haversine 공식 사용)
		double a = Math.sin(deltaLatitude / 2) * Math.sin(deltaLatitude / 2) + Math.cos(latitudeOriginRadians)
			* Math.cos(latitudeCompareRadians) * Math.sin(deltaLongitude / 2) * Math.sin(deltaLongitude / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c;
	}
}
