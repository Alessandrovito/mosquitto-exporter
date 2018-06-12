package com.vitale.exporter.mosquitto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;

@Controller
@SpringBootApplication
@EnablePrometheusEndpoint
public class ExporterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExporterApplication.class, args);
    }

}