package com.wayzor.wayzor_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Configuration
public class GoogleConfig {

    @Value("${google.api.key}")
    public String googleApiKey;

}
