package nos;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

@Named
public class DemoServiceClientCommandExecutor {

	@Inject
	private DemoServiceClientCommand client;

	private AtomicInteger requestId = new AtomicInteger(1);

	@Scheduled(initialDelay = 10000, fixedRate = 3000)
	public void invokeHello() {
		final int nextRequestId = requestId.getAndIncrement();
		final String response = client.hello(nextRequestId);
		LoggerFactory.getLogger(getClass()).info("[{}] {}", StringUtils.leftPad(String.valueOf(nextRequestId), 6),
				response);
	}
}
