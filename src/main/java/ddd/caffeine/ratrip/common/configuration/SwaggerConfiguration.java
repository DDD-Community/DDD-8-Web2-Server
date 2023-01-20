package ddd.caffeine.ratrip.common.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfiguration {
	@Bean
	public OpenAPI openApi() {
		SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

		return new OpenAPI()
			.info(new Info()
				.title("Ratrip API")
				.description("[인증]이 붙은 API는 요청 헤더에 Key: Authorization, Value: Bearer {token}을 포함해야 합니다."))
			.components(new Components()
				.addSecuritySchemes("bearerAuth",
					new SecurityScheme().type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")
						.in(SecurityScheme.In.HEADER)
						.name("Authorization")))
			.security(List.of(securityRequirement));
	}
}
