package hazelcast;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CacheConfig(cacheNames = "demo", keyGenerator = "demoCacheKeyGenerator")
public class DemoService {

    private static void logRequest(final int requestId, final String operation) {
        LoggerFactory.getLogger(DemoService.class).info("[{}] Handling request {}...",
                StringUtils.leftPad(String.valueOf(requestId), 6), operation);
    }

    private String value = "DEFAULT";

    @CacheEvict
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void evict(@RequestHeader("Request-Id") final int requestId) {
        logRequest(requestId, "evict");
    }

    @Cacheable
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String get(@RequestHeader("Request-Id") final int requestId) {
        logRequest(requestId, "get");
        return value;
    }

    @CachePut
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public String set(@RequestHeader("Request-Id") final int requestId, @RequestBody final String newValue) {
        logRequest(requestId, "set");
        this.value = newValue;
        return this.value;
    }
}
