package hazelcast;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.HazelcastInstanceFactory;
import com.hazelcast.spring.cache.HazelcastCacheManager;

import nos.EmbeddedServletContainerPortRangeConfig;

@EnableCaching
@EnableEurekaClient
@SpringBootApplication
public class DemoServiceRunner {

    public static void main(final String[] args) throws Exception {
        new SpringApplicationBuilder(DemoServiceRunner.class, EmbeddedServletContainerPortRangeConfig.class).run(args);
    }

    @Value("${spring.application.instance_id}")
    private String nodeId;

    @Bean
    public CacheManager cacheManager() throws URISyntaxException {

        final Config config = new Config();
        // config.setProperty("hazelcast.logging.type", "slf4j");
        config.getManagementCenterConfig().setEnabled(true).setUrl("http://localhost:8080/mancenter");

        final HazelcastInstance instance = HazelcastInstanceFactory.newHazelcastInstance(config);
        // return new HazelcastServerCacheManager(new
        // HazelcastServerCachingProvider(), instance, null, null, null);
        return new HazelcastCacheManager(instance);
    }

    @Bean
    public KeyGenerator demoCacheKeyGenerator() {
        return (target, method, params) -> "demo";
    }
}
