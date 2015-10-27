package nos;

import javax.inject.Inject;

import org.springframework.cloud.netflix.feign.ribbon.CachingLBClientFactory;
import org.springframework.cloud.netflix.feign.ribbon.SpringLBClientFactory;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.DefaultLoadBalancerRetryHandler;
import com.netflix.client.RetryHandler;
import com.netflix.client.config.IClientConfig;

import feign.Client;
import feign.ribbon.LBClient;
import feign.ribbon.RibbonClient;

@Configuration
public class FeignRibbonClientConfig {

	public static class DelegatingSpringLBClientFactory
		// extends CachingSpringLoadBalancerFactory
	extends SpringLBClientFactory
		{

		private SpringClientFactory factory;

		public DelegatingSpringLBClientFactory(SpringClientFactory factory) {
			super(factory);
			this.factory = factory;
		}

//		public FeignLoadBalancer create(String clientName) {
		public LBClient create(String clientName) {

			final LBClient client = super.create(clientName);
//			final FeignLoadBalancer client = super.create(clientName);

			// Override the default load balancer creation, which sets the retry
			// handler to a non-configured default retry handler.
			client.setRetryHandler(buildRetryHandler(clientName));

			return client;
		}

		private RetryHandler buildRetryHandler(final String clientName) {
			final IClientConfig clientConfig = factory.getClientConfig(clientName);
			return new DefaultLoadBalancerRetryHandler(clientConfig);
		}
	}

	@Inject
	private SpringClientFactory factory;

//	@Bean
//	@Primary
//	public CachingSpringLoadBalancerFactory cachingLBClientFactory(
//			SpringClientFactory factory) {
//		return new DelegatingSpringLBClientFactory(factory);
//	}

	@Bean
	public Client feignRibbonClient() {
		final DelegatingSpringLBClientFactory springLBClientFactory = new DelegatingSpringLBClientFactory(factory);
		final CachingLBClientFactory cachingLBClientFactory = new CachingLBClientFactory(springLBClientFactory);
		return RibbonClient.builder().lbClientFactory(cachingLBClientFactory).build();
	}
}
