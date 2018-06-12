package com.vitale.exporter.mosquitto;

import com.google.common.base.MoreObjects;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;

public class PrometheusMetric {
    private String name;
    private String help;
    private Gauge gauge;
    private Counter counter;

    public PrometheusMetric(String name, String help, Gauge gauge) {
        this.name = name;
        this.help = help;
        this.gauge = gauge;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public Gauge getGauge() {
        return gauge;
    }

    public void setGauge(Gauge gauge) {
        this.gauge = gauge;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues().add("name", name).add("help", help).add("gauge", gauge).toString();
    }

}
