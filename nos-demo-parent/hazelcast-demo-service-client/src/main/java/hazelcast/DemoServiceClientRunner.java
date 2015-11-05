package hazelcast;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import nos.EmbeddedServletContainerPortRangeConfig;
import nos.FeignRibbonClientConfig;

@EnableCircuitBreaker
@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
public class DemoServiceClientRunner {

    public static void main(final String[] args) throws Exception {
        final ConfigurableApplicationContext context = new SpringApplicationBuilder(DemoServiceClientRunner.class,
                EmbeddedServletContainerPortRangeConfig.class, FeignRibbonClientConfig.class).web(true).run(args);
        try {
            final DemoServiceClientCommandExecutor executor = context.getBean(DemoServiceClientCommandExecutor.class);
            executor.invoke();
        } finally {
            context.stop();
            System.exit(0);
        }
    }
}
