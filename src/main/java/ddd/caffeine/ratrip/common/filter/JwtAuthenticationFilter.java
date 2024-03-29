package ddd.caffeine.ratrip.common.filter;

import static ddd.caffeine.ratrip.common.exception.ExceptionInformation.*;
import static ddd.caffeine.ratrip.common.util.HttpHeaderUtils.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import ddd.caffeine.ratrip.common.exception.domain.CommonException;
import ddd.caffeine.ratrip.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private static final List<String> ALLOW_LIST = List.of("/auth", "/swagger-ui", "/api-docs", "/health-check",
		"/notifications");
	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		if (!isAllowList(request.getRequestURI())) {
			String bearerToken = request.getHeader(AUTHORIZATION_HEADER_PREFIX);
			UUID userId = validateHeaderAndGetUserId(bearerToken);
			setAuthentication(userId);
		}

		filterChain.doFilter(request, response);
	}

	private boolean isAllowList(String requestURI) {
		return ALLOW_LIST.stream().anyMatch(requestURI::contains);
	}

	private UUID validateHeaderAndGetUserId(String bearerToken) {
		validateHasText(bearerToken);
		validateStartWithBearer(bearerToken);
		return validateAccessToken(getAccessTokenFromBearer(bearerToken));
	}

	private void validateHasText(String bearerToken) {
		if (!StringUtils.hasText(bearerToken)) {
			throw new CommonException(EMPTY_HEADER_EXCEPTION);
		}
	}

	private void validateStartWithBearer(String bearerToken) {
		if (!bearerToken.startsWith(BEARER_PREFIX)) {
			throw new CommonException(INVALID_BEARER_FORMAT_EXCEPTION);
		}
	}

	private UUID validateAccessToken(String accessToken) {
		return jwtUtil.validateAccessToken(accessToken);
	}

	private String getAccessTokenFromBearer(String bearerToken) {
		return bearerToken.substring(BEARER_PREFIX.length());
	}

	private void setAuthentication(UUID userId) {
		SecurityContextHolder.getContext().setAuthentication(jwtUtil.getAuthentication(userId));
	}
}
