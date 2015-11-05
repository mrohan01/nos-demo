package hazelcast;

import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.Validate;
import org.slf4j.LoggerFactory;

@Named
public class DemoServiceClientCommandExecutor {

    @Inject
    private DemoServiceClientCommand client;

    private final AtomicInteger      requestId = new AtomicInteger(1);

    public void invoke() {

        try {

            String value = client.get(requestId.getAndIncrement());
            Validate.isTrue("DEFAULT".equals(value));

            client.set(requestId.getAndIncrement(), "FIRST_UPDATE");

            for (int i = 0; i < 10; i++) {
                value = client.get(requestId.getAndIncrement());
                Validate.isTrue("FIRST_UPDATE".equals(value), value);
            }

            Validate.isTrue("FIRST_UPDATE".equals(value), value);

        } catch (final Exception e) {

            LoggerFactory.getLogger(getClass()).error("Unable to execute request " + requestId.get(), e);

        } finally {

            client.set(requestId.getAndIncrement(), "DEFAULT");
            client.evict(requestId.getAndIncrement());
        }
    }
}
