package nos;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.SocketUtils;

@Configuration
@ConfigurationProperties("serverPortRange")
public class EmbeddedServletContainerPortRangeConfig {

    private static final String SERVER_PORT_KEY = "server.port";

    private static final int    MIN_PORT        = 1100;

    private final int           MAX_PORT        = 65535;

    /**
     * Sourced from application.properties / application.yml:
     * serverPortRange.max
     * <p/>
     * Defaults to {@link #MAX_PORT}
     */
    private int                 max             = MAX_PORT;

    /**
     * Sourced from application.properties / application.yml:
     * serverPortRange.min
     * <p/>
     * Defaults to {@link #MIN_PORT}
     */
    private int                 min             = MIN_PORT;

    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer(final Environment environment) {

        final int port = environment.containsProperty(SERVER_PORT_KEY)
                ? Integer.valueOf(environment.getProperty(SERVER_PORT_KEY)) : 0;

        return new EmbeddedServletContainerCustomizer() {

            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                if (port == 0) {
                    validatePorts();
                    final int port = SocketUtils.findAvailableTcpPort(min, max);
                    container.setPort(port);
                }
            }
        };
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        return new TomcatEmbeddedServletContainerFactory();
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setMin(int min) {
        this.min = min;
    }

    private void validatePorts() {

        if (min < MIN_PORT || min > MAX_PORT - 1) {
            throw new IllegalStateException(
                    "Minimum port range value must be between " + MIN_PORT + " and " + (MAX_PORT - 1) + ".");
        }

        if (max < MIN_PORT || max > MAX_PORT) {
            throw new IllegalStateException(
                    "Maximum port range value must be between " + (MIN_PORT + 1) + " and " + MAX_PORT + ".");
        }

        if (min > max) {
            throw new IllegalStateException("Minimum port range value must be less than maximum port range value.");
        }
    }
}
