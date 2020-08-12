package bboe.prometheusproxy.cfprometheusproxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class ProxyWebController {
    private Logger logger = LoggerFactory.getLogger(ProxyWebController.class);

    private String remoteMetricURI;
    private MetricCleaner metricCleaner;

    @Autowired
    public ProxyWebController(MetricCleaner metricCleaner, @Value( "${remotemetricuri}" ) String remoteMetricURI) {
        this.metricCleaner = metricCleaner;
        this.remoteMetricURI = remoteMetricURI;

        logger.info("ProxyWebController starting using remote metric source: " + this.remoteMetricURI);
    }

    @RequestMapping("/metrics")
    public String getMetrics() {
        RestTemplate t = new RestTemplate();
        String s = t.getForObject(remoteMetricURI, String.class);
        return metricCleaner.cleanMessage(s);
    }
}
