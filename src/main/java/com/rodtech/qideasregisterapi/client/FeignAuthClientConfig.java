package com.rodtech.qideasregisterapi.client;

import feign.Logger;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.security.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@Configuration
public class FeignAuthClientConfig {

    @Value("${security.oauth2.qideas.access-token-uri}")
    private String accessTokenUri;

    @Value("${security.oauth2.qideas.client-id}")
    private String clientId;

    @Value("${security.oauth2.qideas.client-secret}")
    private String clientSecret;

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
    }

    private OAuth2ProtectedResourceDetails resource() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setAccessTokenUri(accessTokenUri);
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        return details;
    }


    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }


}
