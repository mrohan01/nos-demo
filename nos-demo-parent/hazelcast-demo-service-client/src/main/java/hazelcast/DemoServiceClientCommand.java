package hazelcast;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Named
public class DemoServiceClientCommand {

    @Inject
    private DemoServiceClient client;

    @HystrixCommand
    public String get(int requestId) {
        String result = client.get(requestId);
        log(requestId, "GET SUCCESS (" + result + ")");
        return result;
    }

    private void log(int requestId, String msg) {
        LoggerFactory.getLogger(getClass()).info("[{}] {}", StringUtils.leftPad(String.valueOf(requestId), 6), msg);
    }

    @HystrixCommand
    public void set(int requestId, final String value) {
        client.set(requestId, value);
        log(requestId, "SET SUCCESS");
    }

    @HystrixCommand
    public void evict(int requestId) {
        client.evict(requestId);
        log(requestId, "EVICT SUCCESS");
    }
}
