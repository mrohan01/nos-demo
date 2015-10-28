package hazelcast;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("hazelcast-demo-service")
public interface DemoServiceClient {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String get(@RequestParam("requestId") final int requestId);

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    void set(@RequestParam("requestId") final int requestId, final String newValue);

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    void evict(@RequestParam("requestId") final int requestId);
}
