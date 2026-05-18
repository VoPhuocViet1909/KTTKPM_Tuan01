package com.javabuilder.searchservice.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest5_client.Rest5ClientTransport;
import co.elastic.clients.transport.rest5_client.low_level.Rest5Client;
import org.apache.hc.core5.http.Header;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.message.BasicHeader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.net.URISyntaxException;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class ElasticSearchConfiguration {

    @Value("${elasticsearch.server-url}")
    private String serverUrl;

    @Value("${elasticsearch.api-key}")
    private String apiKey;

    private ObjectMapper buildObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public ElasticsearchClient elasticsearchClient() throws URISyntaxException {
        Rest5Client rest5Client = Rest5Client.builder(HttpHost.create(serverUrl))
                .setDefaultHeaders(new Header[] {
                    new BasicHeader("Authorization", "ApiKey " + apiKey),
                })
                .build();

        Rest5ClientTransport transport = new Rest5ClientTransport(
                rest5Client,
                new JacksonJsonpMapper(buildObjectMapper())
        );

        return new ElasticsearchClient(transport);
    }

}
