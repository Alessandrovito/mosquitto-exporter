package com.vitale.exporter.mosquitto;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MqttConnection {

    private static final Logger logger = LoggerFactory.getLogger(MqttConnection.class);

    static final String HOSTNAME_BROKER = "mosquitto";

    static final String MQTT_BROKER_URL = "tcp://" + HOSTNAME_BROKER + ":1883";

    static final String SYS_TOPIC_ALL = "$SYS/#";

    static final String CLIENT_ID = "PromExporterClient";

    private MqttClient clientPrometheus;

    private String username;
    private String password;

    public MqttConnection(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean isValidClient() {
        return (clientPrometheus != null) ? true : false;
    }

    public void connect() {

        MemoryPersistence persistence = new MemoryPersistence();

        try {
            logger.debug("Parameter:  MQTT Broker  " + MQTT_BROKER_URL + "Account : " + username + " Password +" + password);
            clientPrometheus = new MqttClient(MQTT_BROKER_URL, CLIENT_ID, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();

            connOpts.setAutomaticReconnect(false);
            connOpts.setCleanSession(true);
            connOpts.setKeepAliveInterval(30);
            connOpts.setUserName(username);
            connOpts.setPassword(password.toCharArray());

            logger.debug("Connecting to broker: " + MQTT_BROKER_URL);
            clientPrometheus.connect(connOpts);
            logger.debug("Connected to Broker");

            SimpleCallback callback = new SimpleCallback(CLIENT_ID);
            clientPrometheus.setCallback(callback);

        } catch (MqttException me) {

            logger.error("Error to connect to Broker: reason {} - message {} - locatizet {} - cause {}", me.getReasonCode(),
                    me.getMessage(), me.getLocalizedMessage(), me.getCause(), me);
        } catch (Exception e) {
            System.out.println("Genric Error " + e);
            e.printStackTrace();
        }
    }

    public void subcribe() {
        if (clientPrometheus != null) {
            try {
                logger.debug("Subscribing to {} ", SYS_TOPIC_ALL);
                clientPrometheus.subscribe(SYS_TOPIC_ALL);

            } catch (MqttException me) {
                System.out.println("Genric Error subscribing  " + me);

                me.printStackTrace();
            }
        }

    }

    public void disconnect() {
        if (clientPrometheus != null) {
            try {
                logger.debug("Disconnnect {} client", CLIENT_ID);
                clientPrometheus.disconnect();
            } catch (MqttException me) {
                logger.error("Genric Error disconneting  " + me);
                me.printStackTrace();
            }

            try {

                logger.debug("Close sample client");
                clientPrometheus.close(true);
            } catch (MqttException e3) {
                logger.error("An error occurred closing client", e3);
                return;
            }
            System.out.println("End terminate client");
            clientPrometheus = null;
        }
    }

}

class SimpleCallback implements MqttCallback {

    private static final Logger logger = LoggerFactory.getLogger(SimpleCallback.class);

    private String clientId = "";

    SimpleCallback(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void connectionLost(Throwable cause) { // Called when the client lost the connection to the broker

        logger.info("Connection lost on ClientId \"" + clientId + "\" with cause \"" + cause.getMessage() + "\" Reason code "
                + ((MqttException) cause).getReasonCode() + "\" Cause \"" + ((MqttException) cause).getCause() + "\"");
        cause.printStackTrace();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

        logger.trace("| ClientId: " + clientId + " Topic:" + topic + " message arrived " + new String(message.getPayload()));

        try {
            String text = new String(message.getPayload());
            text = text.replaceAll("[^\\d.]", "");

            double value = Double.parseDouble(text);

            ExporterPrometheus.MAP_TOPIC_PROMETHEUS.entrySet().forEach(s -> {
                if (s.getKey().equals(topic)) {
                    s.getValue().getGauge().set(value);
                    logger.debug("Set Gauge {} to value : {}", s.getValue().getName(), value);
                }
            });
        } catch (Exception e) {
            logger.error("Cannot convert and send message : {}", topic);
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {// Called when a outgoing publish is complete
        logger.debug("Publish completed >>" + token);
    }

}
