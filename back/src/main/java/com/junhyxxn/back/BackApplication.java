package com.junhyxxn.back;

import java.time.ZoneId;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // Auditing 기능 사용
public class BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

	@PostConstruct
	private void started() {
		// Asia/Seoul 로 TimeZone 세팅
		TimeZone.setDefault(
				TimeZone.getTimeZone(ZoneId.of("Asia/Seoul"))
		);
	}

}
