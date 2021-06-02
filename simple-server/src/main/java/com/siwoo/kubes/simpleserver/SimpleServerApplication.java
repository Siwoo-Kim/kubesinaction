package com.siwoo.kubes.simpleserver;

import com.google.common.collect.ImmutableMap;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@SpringBootApplication
public class SimpleServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleServerApplication.class, args);
	}
	
	@Bean
	public KubernetesClient kubernetesClient() {
		return new DefaultKubernetesClient();
	}
	
	@Slf4j
	@RestController
	private static class OpenController {
		@Value("${KUBE_TOKEN_PATH}")
		private String TOKEN_PATH;
		@Autowired
		private KubernetesClient kubeClient;
		
		@GetMapping("/")
		public ResponseEntity<?> index(HttpServletRequest req) {
			log.info(String.format("Received request from %s", req.getRemoteAddr()));
			String os = System.getenv("HOSTNAME");
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
		
		@GetMapping("/pods")
		public ResponseEntity<?> getAPIs() {
			String ns = System.getenv("NAMESPACE");
			PodList pods = kubeClient.pods().inNamespace(ns).list();
			String names = pods.getItems().stream()
					.map(s -> s.getMetadata().getName())
					.collect(Collectors.joining(","));
			return ResponseEntity.ok(names);
		}
	}
}
