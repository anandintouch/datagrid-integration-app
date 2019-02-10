package com.ap.datagrid.spring.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.ap.datagrid.spring.core.client.CachedClientGetter;
import com.ap.datagrid.spring.core.client.Client;
import com.ap.datagrid.spring.core.client.ClientGetter;
import com.ap.datagrid.spring.rest.model.Clients;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * REST Cache Endpoints.
 * 
 * @author anand.prakash
 *
 */
@RequestMapping("clients")
public class ClientsRest {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CachedClientGetter cachedClientGetter;

    private final ClientGetter clientGetter;

    public ClientsRest(CachedClientGetter cachedClientGetter, ClientGetter clientGetter) {
        this.cachedClientGetter = cachedClientGetter;
        this.clientGetter = clientGetter;
    }

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    Clients getClients(@RequestParam(value = "caching", defaultValue = "false") boolean caching) {
        long startTime = System.currentTimeMillis();

        List<Client> clients;
        if(caching) {
            clients = cachedClientGetter.getBestClients();
        } else {
            clients = clientGetter.getBestClients();
        }

        logger.info("Loading Clients took {} ms", System.currentTimeMillis() - startTime);
        return new Clients(clients);
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public @ResponseBody
    Clients updateClient(@RequestParam(value = "caching", defaultValue = "false") boolean caching,
    		@RequestParam("id") int id) {
        long startTime = System.currentTimeMillis();

        Client clientResult;
        if(caching) {
        	clientResult = cachedClientGetter.updateBestClient(id);
        } else {
        	clientResult = clientGetter.updateBestClient(id);
        }

        logger.info("Updating Clients took {} ms", System.currentTimeMillis() - startTime);
        return new Clients(clientResult);
    }
    
	@RequestMapping(method = RequestMethod.GET, value = "/evictAll")
	//@ResponseStatus(value = HttpStatus.OK)
	public @ResponseBody String evictAll(){
		cachedClientGetter.evictAllEntries();
		return "Evicting all objects are Done !";
	}
	
	@RequestMapping(method = RequestMethod.GET, value ="/evictEntry")
	public @ResponseBody String evictEntry(@RequestParam("id") int id){
		cachedClientGetter.evictEntry(id);
		return "Evicting object id '"+id+"' is Done";
	}
}
