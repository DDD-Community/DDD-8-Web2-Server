package ddd.caffeine.ratrip.module.travel_plan;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import ddd.caffeine.ratrip.module.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * TravelPlan 과 User 의 N:M 관계를 해소하기 위한 매핑 테이블 입니다.
 */
@Getter
@NoArgsConstructor
@Entity
public class TravelPlanUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "travel_plan_id", columnDefinition = "BINARY(16)")
	private TravelPlan travelPlan;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", columnDefinition = "BINARY(16)")
	private User user;
}
