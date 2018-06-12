package com.vitale.exporter.mosquitto;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;

import io.prometheus.client.Gauge;

public final class ExporterPrometheus {

    private static final Map<String, String> MAP_SYS_TOPIC_PROM_NAME;

    private static final String SYS_TOPIC_PREFIX = "$SYS/broker/";

    private static final String PREMETHEUS_NAME_PREFIX = "mosquitto_";

    static {

        /* defines the list of available function allowed in spel Expression */
        Map<String, String> mapTopicSysPromName = new HashMap<>();
        /* Java Math functions */

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "uptime", PREMETHEUS_NAME_PREFIX + "uptime");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "subscriptions/count", PREMETHEUS_NAME_PREFIX + "subscriptions_count");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "clients/disconnected", PREMETHEUS_NAME_PREFIX + "clients_disconnected");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "clients/expired", PREMETHEUS_NAME_PREFIX + "clients_expired");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "clients/maximum", PREMETHEUS_NAME_PREFIX + "clients_maximum");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "clients/inactive", PREMETHEUS_NAME_PREFIX + "clients_inactive");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "clients/connected", PREMETHEUS_NAME_PREFIX + "clients_connected");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "clients/stored", PREMETHEUS_NAME_PREFIX + "clients_stored");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "clients/maximum", PREMETHEUS_NAME_PREFIX + "clients_maximum");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "clients/total", PREMETHEUS_NAME_PREFIX + "clients_total");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "clients/expired", PREMETHEUS_NAME_PREFIX + "clients_expired");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "messages/received", PREMETHEUS_NAME_PREFIX + "messages_received");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "messages/sent", PREMETHEUS_NAME_PREFIX + "messages_sent");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "bytes/received", PREMETHEUS_NAME_PREFIX + "bytes_received");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "bytes/sent", PREMETHEUS_NAME_PREFIX + "bytes_sent");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "publish/bytes/sent", PREMETHEUS_NAME_PREFIX + "publish_bytes_sent");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "publish/bytes/received", PREMETHEUS_NAME_PREFIX + "publish_bytes_received");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "publish/messages/dropped", PREMETHEUS_NAME_PREFIX + "publish_messages_dropped");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "publish/messages/received", PREMETHEUS_NAME_PREFIX + "publish_messages_received");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "publish/messages/sent", PREMETHEUS_NAME_PREFIX + "publish_messages_sent");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/messages/received/1min",
                PREMETHEUS_NAME_PREFIX + "load_messages_received_1min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/messages/received/5min",
                PREMETHEUS_NAME_PREFIX + "load_messages_received_5min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/messages/received/15min",
                PREMETHEUS_NAME_PREFIX + "load_messages_received_15min");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/messages/sent/1min", PREMETHEUS_NAME_PREFIX + "load_messages_sent_1min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/messages/sent/5min", PREMETHEUS_NAME_PREFIX + "load_messages_sent_5min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/messages/sent/15min", PREMETHEUS_NAME_PREFIX + "load_messages_sent_15min");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/bytes/received/1min", PREMETHEUS_NAME_PREFIX + "load_bytes_received_1min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/bytes/received/5min", PREMETHEUS_NAME_PREFIX + "load_bytes_received_5min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/bytes/received/15min", PREMETHEUS_NAME_PREFIX + "load_bytes_received_15min");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/sockets/1min", PREMETHEUS_NAME_PREFIX + "load_sockets_1min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/sockets/5min", PREMETHEUS_NAME_PREFIX + "load_sockets_5min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/sockets/15min", PREMETHEUS_NAME_PREFIX + "load_sockets_15min");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/bytes/sent/1min", PREMETHEUS_NAME_PREFIX + "load_bytes_sent_1min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/bytes/sent/5min", PREMETHEUS_NAME_PREFIX + "load_bytes_sent_5min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/bytes/sent/15min", PREMETHEUS_NAME_PREFIX + "load_bytes_sent_15min");

        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/connections/1min", PREMETHEUS_NAME_PREFIX + "load_connections_1min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/connections/5min", PREMETHEUS_NAME_PREFIX + "load_connections_5min");
        mapTopicSysPromName.put(SYS_TOPIC_PREFIX + "load/connections/15min", PREMETHEUS_NAME_PREFIX + "load_connections_15min");

        MAP_SYS_TOPIC_PROM_NAME = ImmutableMap.copyOf(mapTopicSysPromName);
    }

    public final static Map<String, PrometheusMetric> MAP_TOPIC_PROMETHEUS = MAP_SYS_TOPIC_PROM_NAME.entrySet().stream()
            .collect(Collectors.toMap(e -> e.getKey(), s -> new PrometheusMetric(s.getValue(), s.getValue(),
                    Gauge.build().name(s.getValue()).help("Broker info topic" + s.getValue()).register())));

}
