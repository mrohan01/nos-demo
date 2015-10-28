package hazelcast;

import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.instance.HazelcastInstanceFactory;
import com.hazelcast.spring.cache.HazelcastCacheManager;

import nos.EmbeddedServletContainerPortRangeConfig;

@EnableCaching
@EnableEurekaClient
@RestController
@SpringBootApplication
@CacheConfig(cacheNames = "demo", keyGenerator = "demoCacheKeyGenerator")
public class DemoService {

    private static void logRequest(final int requestId, final String operation) {
        LoggerFactory.getLogger(DemoService.class).info("[{}] Handling request {}...",
                StringUtils.leftPad(String.valueOf(requestId), 6), operation);
    }

    public static void main(final String[] args) throws Exception {
        new SpringApplicationBuilder(DemoService.class, EmbeddedServletContainerPortRangeConfig.class).run(args);
    }

    @Value("${spring.application.instance_id}")
    private String nodeId;

    private String value = "DEFAULT";

    @Bean
    public CacheManager cacheManager() throws URISyntaxException {
        final HazelcastInstance instance = HazelcastInstanceFactory.newHazelcastInstance(new Config());
        // return new HazelcastServerCacheManager(new
        // HazelcastServerCachingProvider(), instance, null, null, null);
        return new HazelcastCacheManager(instance);
    }

    @Bean
    public KeyGenerator demoCacheKeyGenerator() {
        return (target, method, params) -> "demo";
    }

    @CacheEvict
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void evict(@RequestParam("requestId") final int requestId) {
        logRequest(requestId, "evict");
    }

    @Cacheable
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestParam("requestId") final int requestId) {
        logRequest(requestId, "get");
        return value;
    }

    @CachePut
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public String set(@RequestParam("requestId") final int requestId, @RequestBody final String newValue) {
        logRequest(requestId, "set");
        this.value = newValue;
        return this.value;
    }
}
