package hazelcast;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Named
public class DemoServiceClientCommand {

    private static void log(final int requestId, final String msg) {
        LoggerFactory.getLogger(DemoServiceClientCommand.class).info("[{}] {}",
                StringUtils.leftPad(String.valueOf(requestId), 6), msg);
    }

    @Inject
    private DemoServiceClient client;

    protected String cacheKey(final int requestId) {
        return "demo";
    }

    protected String cacheKey(final int requestId, final String value) {
        return "demo";
    }

    @HystrixCommand
    // @CacheRemove(commandKey = "demo", cacheKeyMethod = "cacheKey")
    public void evict(final int requestId) {
        client.evict(requestId);
        log(requestId, "EVICT SUCCESS");
    }

    @HystrixCommand
    // @CacheResult(cacheKeyMethod = "cacheKey")
    public String get(final int requestId) {
        final String result = client.get(requestId);
        log(requestId, "GET SUCCESS (" + result + ")");
        return result;
    }

    @HystrixCommand
    // @CacheRemove(commandKey = "demo", cacheKeyMethod = "cacheKey")
    public void set(final int requestId, final String value) {
        client.set(requestId, value);
        log(requestId, "SET SUCCESS");
    }
}
