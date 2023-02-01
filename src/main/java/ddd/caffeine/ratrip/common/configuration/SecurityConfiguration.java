package ddd.caffeine.ratrip.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ddd.caffeine.ratrip.common.filter.JwtAuthenticationFilter;
import ddd.caffeine.ratrip.common.filter.JwtExceptionFilter;
import ddd.caffeine.ratrip.common.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	private final JwtUtil jwtUtil;
	private final CorsConfigurationSource corsConfigurationSource;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.authorizeRequests().antMatchers("/**").permitAll()//auth 관련 요청은 인증 없이 접근을 허용한다. ->
			.and()
			.httpBasic().disable() //사용자 인증방법인 HTTP Basic Authentication을 사용하지 않는다.
			.formLogin().disable() //formLogin 검증 방법은 사용하지 않겠다.
			.logout().disable() //logout 방식은 사용하지 않는다.
			.csrf().disable()
			.cors().configurationSource(corsConfigurationSource)
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.headers()
			.frameOptions().sameOrigin()
			.httpStrictTransportSecurity().disable()
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class)
			.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		//https://ddd-web2-map
		configuration.addAllowedOriginPattern("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

}
