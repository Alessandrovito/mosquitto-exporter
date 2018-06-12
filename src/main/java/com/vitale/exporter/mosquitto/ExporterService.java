package com.vitale.exporter.mosquitto;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ExporterService {

    private MqttConnection client;
    private static final Logger logger = LoggerFactory.getLogger(ExporterService.class);

    @Value("${mosquitto.exporter.account.username:mosquitto}") private String accountUsername;
    @Value("${mosquitto.exporter.account.password:mosquitto}") private String accountPassword;
    @Value("${mosquitto.exporter.broker.name:mosquitto}") private String brokerName;

    @PostConstruct
    void createClient() {

        client = new MqttConnection(accountUsername, accountPassword, brokerName);

        ExporterPrometheus.MAP_TOPIC_PROMETHEUS.entrySet().stream().forEach(p -> logger.info(" Map Topic Prometheus: entry: [{}]", p));

    }

    @Scheduled(initialDelayString = "10000", fixedDelayString = "${mosquitto.exporter.refresh-reconnect-ms:60000}")
    void refresh() {
        logger.debug("Run refresh mqtt client connection");
        /*
         * This implementation aim to disconnect and reconnect MQTT client to broker in order to get some metrics ( connectio, uptime)
         * sent by Broker only at first connection of client.
         */
        if (client.isValidClient()) {
            client.disconnect();
            client.connect();
            client.subcribe();
        } else {

            client.connect();
            client.subcribe();
        }

    }

}
