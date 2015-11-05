package hazelcast;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("hazelcast-demo-service")
public interface DemoServiceClient {

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    void evict(@RequestHeader("Request-Id") final int requestId);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String get(@RequestHeader("Request-Id") final int requestId);

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    void set(@RequestHeader("Request-Id") final int requestId, final String newValue);
}
