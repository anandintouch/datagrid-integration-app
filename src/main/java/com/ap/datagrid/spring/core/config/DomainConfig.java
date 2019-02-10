package com.ap.datagrid.spring.core.config;


import java.lang.reflect.Method;

import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
//import org.infinispan.configuration.global.GlobalConfigurationBuilder;
//import org.infinispan.spring.provider.SpringEmbeddedCacheManagerFactoryBean;
import org.infinispan.spring.provider.SpringRemoteCacheManager;
import org.infinispan.spring.provider.SpringRemoteCacheManagerFactoryBean;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ap.datagrid.spring.core.client.CachedClientGetter;
import com.ap.datagrid.spring.core.client.ClientCache;
import com.ap.datagrid.spring.core.client.ClientGetter;

/**
 * Spring configuration for domain objects.
 * 
 * @author anand.prakash
 * 
 */
@Configuration
@EnableCaching
public class DomainConfig {


    @Bean
    public ClientGetter clientGetter() {
        return new ClientGetter();
    }

    @Bean
    public com.ap.datagrid.spring.core.client.CachedClientGetter cachedClientGetter(ClientGetter clientGetter) {
        return new CachedClientGetter(clientGetter);
    }

   @Bean
    public ClientCache cacheHandler(CacheManager cacheManager) {
	    
        return new ClientCache(cacheManager());
        
    }
    
    @Bean
    public SpringRemoteCacheManager cacheManager(){
    	System.out.println("Inside DomainConfig.cacheManager()---AP");

    	String host ="datagrid-service-app";
    	int port = 11333;
    	
    	//org.infinispan.configuration.cache.ConfigurationBuilder bc = new  org.infinispan.configuration.cache.ConfigurationBuilder();
    	/*GlobalConfigurationBuilder gc = new GlobalConfigurationBuilder();
    	gc.site().localSite("SFO");*/
    	
	    ConfigurationBuilder builder = new ConfigurationBuilder();
	    //builder = new ConfigurationBuilder();

	    builder.addServer().host(host).port(port);
	    builder.maxRetries(1).socketTimeout(20000).connectionTimeout(50000);
	    builder.tcpNoDelay(true);
	    
	    //Code to connect with Datagrid using TLS/SSL
	  /*  builder.addServer().host(host).port(port).security().ssl().enable()
	    .trustStoreFileName(System.getProperty("javax.net.ssl.trustStore")).trustStorePassword(System.getProperty("javax.net.ssl.trustStorePassword").toCharArray());
	    */
	    
	   /* builder.addServer().host("datagrid-app-hotrod").port(11333).security().ssl().enable()
	    .trustStoreFileName("/etc/datagrid-secret-volume").trustStorePassword("changeit".toCharArray());*/
	    
	    System.out.println("======> Connecting to HOST->'"+host +"' and PORT->"+port );
	    //return new RemoteCacheManager(builder.build(),true);
	   return new SpringRemoteCacheManager(new RemoteCacheManager(builder.build(),true));
    }

    
    @Bean
    public KeyGenerator simpleKeyGenerator() {
      return new KeyGenerator() {
		@Override
		public Object generate(Object target, Method method, Object... params) {
		    StringBuilder sb = new StringBuilder();
		    sb.append(target.getClass().getName());
		    sb.append(method.getName());
		    for (Object obj : params) {
		      sb.append(obj.toString());
		    }
		    return sb.toString();
		  }
	};
    }
}
