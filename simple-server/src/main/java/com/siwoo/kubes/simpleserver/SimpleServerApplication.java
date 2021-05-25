package com.siwoo.kubes.simpleserver;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class SimpleServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleServerApplication.class, args);
	}

	@Slf4j
	@RestController
	private static class OpenController {
		@Value("${KUBE_TOKEN_PATH}")
		private String TOKEN_PATH;
		
		@GetMapping("/")
		public ResponseEntity<?> index(HttpServletRequest req) {
			log.info(String.format("Received request from %s", req.getRemoteAddr()));
			String os = System.getProperty("os.name");
			return ResponseEntity.ok(ImmutableMap.of("message", 
					String.format("You've hit %s.", os)));
		}
		
		@GetMapping("/token")
		public ResponseEntity<?> getToken(HttpServletRequest req) throws IOException {
			log.info(String.format("Received request from %s", req.getRemoteAddr()));
			String token = String.join("\n", Files.readAllLines(Paths.get(TOKEN_PATH)));
			return ResponseEntity.ok(ImmutableMap.of("token",
					String.format("%s.", token)));
		}
	}
}
