package nos;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("demo-service")
public interface DemoServiceClient {

	@RequestMapping(method = RequestMethod.GET, value = "/")
	String handleRequest(@RequestParam("requestId") int requestId);
}
