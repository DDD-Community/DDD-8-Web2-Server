package ddd.caffeine.ratrip.module.user.domain;

import java.util.Collection;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import ddd.caffeine.ratrip.common.jpa.AuditingTimeEntity;
import ddd.caffeine.ratrip.common.util.SequentialUUIDGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AuditingTimeEntity implements UserDetails {
	@Id
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@Column(nullable = false)
	private String name;

	@Column(unique = true)
	private String email;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private UserStatus status;

	@Embedded
	private SocialInfo socialInfo;

	@PrePersist
	public void createUserPrimaryKey() {
		//sequential uuid 생성
		this.id = SequentialUUIDGenerator.generate();
	}

	@Builder
	private User(String name, String email, UserStatus status, String socialId, UserSocialType socialType) {
		this.name = name;
		this.email = email;
		this.status = status;
		this.socialInfo = SocialInfo.of(socialId, socialType);
	}

	public static User of(String name, String email, UserStatus status, String socialId, UserSocialType socialType) {
		return User.builder()
			.name(name)
			.email(email)
			.status(status)
			.socialId(socialId)
			.socialType(socialType)
			.build();
	}

	public void updateName(String name) {
		this.name = name;
	}

	public void delete(User user) {
		this.status = UserStatus.DELETED;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return id.toString();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
