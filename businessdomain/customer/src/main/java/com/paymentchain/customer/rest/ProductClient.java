package com.paymentchain.customer.rest;

import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Component
public class ProductClient {

    private final WebClient webClient;
    private final HttpClient httpClient;

    public ProductClient(WebClient.Builder wcb) {

        // webClient requires HttpClient library to work propertly
        this.httpClient = HttpClient.create()
                //Connection Timeout: is a period within which a connection between a client and a server must be established
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(EpollChannelOption.TCP_KEEPIDLE, 300)
                .option(EpollChannelOption.TCP_KEEPINTVL, 60)
                //Response Timeout: The maximun time we wait to receive a response after sending a request
                .responseTimeout(Duration.ofSeconds(1))
                // Read and Write Timeout: A read timeout occurs when no data was read within a certain
                //period of time, while the write timeout when a write operation cannot finish at a specific time
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                });

        this.webClient = wcb.clone()
                .clientConnector(new ReactorClientHttpConnector(this.httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl("http://BUSINESSDOMAIN-PRODUCT/api/v1/product")
                .build();
    }

    public String getProductName(long id) {
        JsonNode block =  webClient.get()
                .uri("/" + id)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        assert block != null;
        return block.get("name").asText();

    }

}
