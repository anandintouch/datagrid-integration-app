package com.ap.datagrid.spring.rest;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ap.datagrid.spring.core.client.CachedClientGetter;
import com.ap.datagrid.spring.core.client.ClientGetter;

/**
 * REST Services configuration
 * 
 */
@Configuration
public class RestConfiguration {

    @Bean
    public ClientsRest cachedClientRest(@Qualifier("cachedClientGetter") CachedClientGetter cachedClientGetter, @Qualifier("clientGetter") ClientGetter clientGetter) {
        return new ClientsRest(cachedClientGetter, clientGetter);
    }

    @Bean
    public CacheRest cacheRest(com.ap.datagrid.spring.core.client.ClientCache clientCache) {
        return new CacheRest(clientCache);
    }

}
