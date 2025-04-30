package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class DemoApplication {

	private static final String RUN_ID_KEY = "app:run:id";

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public RedisTemplate<String, Long> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Long> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setKeySerializer(new StringRedisSerializer());
		return template;
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(RedisTemplate<String, Long> redisTemplate) {
		return args -> {
			// Get the current value or initialize to 0 if not exists
			Long runId = redisTemplate.opsForValue().increment(RUN_ID_KEY, 1);
			
			System.out.println("====================================");
			System.out.println("   Application Run ID: " + runId);
			System.out.println("====================================");
		};
	}
}
