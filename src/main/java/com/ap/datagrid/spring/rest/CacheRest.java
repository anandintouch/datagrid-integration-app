package com.ap.datagrid.spring.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ap.datagrid.spring.core.client.Client;
import com.ap.datagrid.spring.core.client.ClientCache;
import com.ap.datagrid.spring.rest.model.Clients;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * REST client based on Cached Client Getter.
 * 
 * @author anand.prakash
 */
@RequestMapping("cache")
public class CacheRest {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    
    private final ClientCache clientCache;
    
   // @Autowired
    //protected  ClientCache clientCache;

    public CacheRest(ClientCache clientCache) {
        this.clientCache = clientCache;
    }


    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Clients getValuesFromCache() {
    	Collection<Client> result = clientCache.getCachedClients();
    	/*for (Client c : result) {
    		System.out.println("Clients are ->"+c.getId()+","+c.getFirstName()+","+c.getLastName());
    	}*/
        return new Clients(result);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/clear")
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody String clearCache() {
        clientCache.clearCache();
        return "Cache cleared !!";
    }
    
	@RequestMapping(method = RequestMethod.GET, value ="/evictEntry")
	public @ResponseBody String evictEntry(@RequestParam("id") int id){
		clientCache.evictEntry(id);
		return "Evicting object id '"+id+"' directly from Cache is Done";
	}
	
	 @RequestMapping(method = RequestMethod.POST, value ="/addData")
	 public @ResponseBody Client addData(@RequestParam("id") int id) {
		 logger.info("Adding Client data");
		 
		 Client result = clientCache.addData(id);
		// logger.info("Reading data from cache-> ",clientCache.getData(id));
	     return result;
	     
	 }
	 
    @RequestMapping(method = RequestMethod.PUT, value="/updateTTL")
    public @ResponseBody String updateLifeSpan( @RequestParam("id") int id) {

    	Client client = null ;
	    client = new Client(id, "ANAND", "PRAKASH", "Coffee", 2);
	    Long ttl =  TimeUnit.MINUTES.toMillis(1);
	    
	    logger.info("Updating Client TTL " );
	    clientCache.updateLifeSpan(String.valueOf(id),client,ttl);
        
        return "TTL updated";
    }
	 
	
}
