package nos;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCircuitBreaker
@EnableEurekaClient
@EnableFeignClients
@EnableScheduling
@EnableHystrixDashboard
@SpringBootApplication
public class DemoServiceClientRunner {

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(DemoServiceClientRunner.class, EmbeddedServletContainerPortRangeConfig.class,
				FeignRibbonClientConfig.class).web(true).run(args);
	}
}
