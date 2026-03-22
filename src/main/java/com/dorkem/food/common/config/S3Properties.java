package com.dorkem.food.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "cloud.aws.s3") //yml값을 담는 어노테이션
public class S3Properties {
	private String bucket;
	private String defaultProfileImage;
	private String defaultMenuImage;
	private String defaultStoreImage;
}
