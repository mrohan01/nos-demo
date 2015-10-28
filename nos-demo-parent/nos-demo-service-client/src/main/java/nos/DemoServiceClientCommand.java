package nos;
import javax.inject.Inject;
import javax.inject.Named;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Named
public class DemoServiceClientCommand {

	@Inject
	private DemoServiceClient client;

	@HystrixCommand(fallbackMethod = "fallback")
	public String hello(int requestId) {
		return "Success! (" + client.handleRequest(requestId) + ")";
	}

	public String fallback(int requestId) {
		return "Failure! Fallback method invoked.";
	}
}
