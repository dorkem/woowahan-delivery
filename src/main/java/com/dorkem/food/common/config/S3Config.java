package com.dorkem.food.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(S3Properties.class) //스프링이 Properties를 못찾으니 이 클래스를 빈으로 등록
public class S3Config {

	// TODO: AmazonS3Client 빈 등록코드
}
