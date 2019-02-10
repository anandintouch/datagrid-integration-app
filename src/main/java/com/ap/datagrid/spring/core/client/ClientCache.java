package com.ap.datagrid.spring.core.client;

//import org.infinispan.AdvancedCache;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.spring.provider.SpringRemoteCache;
import org.infinispan.spring.provider.SpringRemoteCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.SimpleKey;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Handles direct cache access.
 * @author anand.prakash
 */
public class ClientCache {

	@Autowired
	protected CacheManager cacheManager;
	
    private Cache cache;
    //private SpringRemoteCache cache;
    // private AdvancedCache<String, Collection<Client>> nativeCache;
    private RemoteCache<String, Collection<Client>> nativeCache;
    private RemoteCache rc;

    public ClientCache(SpringRemoteCacheManager springRemoteCacheManager) {
          this.cache = (SpringRemoteCache) springRemoteCacheManager.getCache("session_cache");
          //this.cache = (SpringRemoteCache) springRemoteCacheManager.getCacheNames();
          //this.rc =  (RemoteCache) springRemoteCacheManager.getCache("default");
     //   cache.put("Name", "Anand");
       // this.nativeCache = (AdvancedCache<String, Collection<Client>>) cache.getNativeCache();
          System.out.println("!!!! Successfully connected to Datagrid server !!!!!" );
          System.out.println("CACHE name-"+cache.getName()+ " and native cache is-"+cache.getNativeCache());
          this.nativeCache = (RemoteCache<String, Collection<Client>>) cache.getNativeCache();
    }
    
/*    public ClientCache(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("clients");
        this.nativeCache = (AdvancedCache<String, Collection<Client>>) cache.getNativeCache();
    }*/

    public void clearCache() {
        cache.clear();
    }

    public Collection<Client> getCachedClients() {
        //since we stored a list of client - in reality it maps for a single key in infinispan and list of clients
        //as values corresponding to that key.
    	//Cache.ValueWrapper wrapper = cache..get();
    	 System.out.println("key sets are-"+nativeCache.keySet().size());
    	 
        Set<String> keys = nativeCache.keySet();
        if(keys.size() > 0) {
        	System.out.println("Key value is->"+keys.iterator().next());
            return nativeCache.get(keys.iterator().next());
        }
        return Collections.emptyList();
    }
    
	public void evictEntry(int id){
		//nativeCache.removeAsync(id);
		cache.evict(id);
		System.out.println("---> Evict Client with id = " + id);
		
	}
    
    public Client getUpdatedCachedClients() {
        //since we stored a list of client - in reality it maps for a single key in infinispan and list of clients
        //as values corresponding to that key.
    	//Cache.ValueWrapper wrapper = cache..get();
    	 System.out.println("key sets are-"+nativeCache.keySet().size());
    	 
        Set<String> keys = nativeCache.keySet();
        if(keys.size() > 0) {
        	System.out.println("Key value in getUpdatedCachedClients() is->"+String.valueOf(keys.iterator().next()));
            return (Client) nativeCache.get(keys.iterator().next());
        }
        return null;
    }
    
	public Client addData(int id){
		//nativeCache.removeAsync(id);
		Client client = null ;
	    client = new Client(id, "ANAND", "PRAKASH", "Coffee", 2);
		cache.put(id, client);
		System.out.println("---> Data added with id = " + id);
		return client;
		
	}
	
	public String getData(int id){
		//nativeCache.removeAsync(id);

		ValueWrapper obj = cache.get(id);
		System.out.println("---> Reading data for id = " + id);
		return obj.toString();
		
	}
	
    public void updateLifeSpan(String id,Client cl,Long lifespan){
    	//cache.put(key, value);
    	System.out.println("CACHE :- "+cache.getName()+ " and native cache :- "+cache.getNativeCache());
    	RemoteCache<String, Client> nativeCache1 = (RemoteCache<String, Client>) cache.getNativeCache();
    	nativeCache1.put(id, cl, lifespan, TimeUnit.MILLISECONDS);

    }
    


}
