package nos;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
@SpringBootApplication
public class DemoService {

	@Value("${spring.application.instance_id}")
	private String nodeId;

	@RequestMapping("/")
	@ResponseBody
	String handleRequest(final int requestId) {
		LoggerFactory.getLogger(getClass()).info("[{}] Handling request...",
				StringUtils.leftPad(String.valueOf(requestId), 6));
		return nodeId;
	}

	public static void main(String[] args) throws Exception {
		new SpringApplicationBuilder(DemoService.class, EmbeddedServletContainerPortRangeConfig.class).run(args);
	}
}
