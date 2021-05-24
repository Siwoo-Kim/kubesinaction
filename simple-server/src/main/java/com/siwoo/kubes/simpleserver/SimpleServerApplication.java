package com.siwoo.kubes.simpleserver;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@SpringBootApplication
public class SimpleServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleServerApplication.class, args);
	}

	@Slf4j
	@RestController
	private static class OpenController {
		
		@GetMapping("/")
		public ResponseEntity<?> index(HttpServletRequest req) {
			log.info(String.format("Received request from %s", req.getRemoteAddr()));
			String os = System.getProperty("os.name");
			return ResponseEntity.ok(ImmutableMap.of("message", 
					String.format("You've hit %s.", os)));
		}
	}
}
