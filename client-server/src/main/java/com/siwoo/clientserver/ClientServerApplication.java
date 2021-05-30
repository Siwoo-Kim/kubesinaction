package com.siwoo.clientserver;

import com.google.common.collect.ImmutableMap;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URL;

@SpringBootApplication
public class ClientServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServerApplication.class, args);
	}

	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient();
	}
	
	@Controller
	public static class ClientController {
//		private static final String backendURL = System.getenv("KUBIA_SERVICE_HOST");
//		private static final int backendPORT = Integer.parseInt(System.getenv("KUBIA_SERVICE_PORT"));
		private static final String KUBIA_SVC_HOST = "kubia.dev";
		private static final int KUBIA_SVC_PORT = 80;
		
		private @Autowired OkHttpClient client;
		
		@SneakyThrows
		@GetMapping("/req")
		public ResponseEntity<?> simpleReq() {
			Request req = new Request.Builder().url(
					new URL("http", KUBIA_SVC_HOST, KUBIA_SVC_PORT, "/")
			).build();
			String body = client.newCall(req).execute().body().string();
			return ResponseEntity.ok().body(ImmutableMap.of("cascadeMsg", body));
		}
	}
}
