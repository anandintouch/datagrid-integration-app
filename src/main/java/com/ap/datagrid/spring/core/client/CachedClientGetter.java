package com.ap.datagrid.spring.core.client;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;


/**
 * Clients repository with APIs using Spring cache.
 * 
 * @author anand.prakash
 */
@CacheConfig(cacheNames = "session_cache",keyGenerator = "simpleKeyGenerator")
public class CachedClientGetter extends ClientGetter {

    private final ClientGetter clientGetter;

    public CachedClientGetter(ClientGetter clientGetter) {
    	
        this.clientGetter = clientGetter;
    }

    @Cacheable
    public List<Client> getBestClients() {
        return clientGetter.getBestClients();
    /*	byte[] data = Utils.serializeDTO(clientGetter.getBestClients());
    	return Utils.deserializeDTO(data);*/
    }
    
	@CachePut(key="#id")
	public Client updateBestClient(int id){
		String info = String.format("---> PUT client with id = %d to Cache", id);
		System.out.println(info);
		
		return clientGetter.updateBestClient(id);
	}
	
	@CacheEvict(value="d1_fortune_session",allEntries = true)
	public void evictAllEntries(){
		System.out.println("---> Evict All Entries.");
	}

	@CacheEvict(key="#id")
	public void evictEntry(int id){
		System.out.println("---> Evict CLient with id = " + id);
	}
}
