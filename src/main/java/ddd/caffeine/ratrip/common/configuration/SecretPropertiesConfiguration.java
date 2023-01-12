package ddd.caffeine.ratrip.common.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import ddd.caffeine.ratrip.common.secret.SecretKeyManager;

@Configuration
@EnableConfigurationProperties(value = {SecretKeyManager.class})
public class SecretPropertiesConfiguration {
}
