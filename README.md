Mosquitto-exporter
========

Mosquitto Exporter to collect metric of $SYS topic for Prometheus system 

Project based on Spring boot framework and gradle build automation system .

## Install 

gradle build

## Setup Mosquitto-exporter


`environment:
      - "JAVA_OPTS=-Dlogging.level.com.vitale.exporter.mosquitto=DEBUG -Dmosquitto.exporter.account.username=namemosquitto -Dmosquitto.exporter.account.password=pwmosquitto -Dmosquitto.exporter.broker.name=mosquitto"
`

You can set following JAVA_OPTS running jar.
* logging.level.com.vitale.exporter.mosquitto: Logging level for mosquitto-exporter
* mosquitto.exporter.account.username: Account username dedicated to Mosquitto Exporter user
* mosquitto.exporter.account.password: Account password dedicated to Mosquitto Exporter user
* mosquitto.exporter.broker.name: hostname to reach URL Mqtt Broker
