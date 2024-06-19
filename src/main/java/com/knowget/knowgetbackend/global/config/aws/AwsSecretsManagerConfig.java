package com.knowget.knowgetbackend.global.config.aws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;

@Configuration
public class AwsSecretsManagerConfig {
	@Bean
	public SecretsManagerClient secretsManagerClient() {
		return SecretsManagerClient.builder()
			.region(Region.of("ap-northeast-2"))
			.credentialsProvider(DefaultCredentialsProvider.create())
			.build();

	}
}
