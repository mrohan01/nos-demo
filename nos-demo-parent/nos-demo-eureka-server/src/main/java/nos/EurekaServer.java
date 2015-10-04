package nos;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableDiscoveryClient
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(EurekaServer.class, args);
	}
}
