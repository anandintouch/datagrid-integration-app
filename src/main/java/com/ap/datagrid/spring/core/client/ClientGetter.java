package com.ap.datagrid.spring.core.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  Clients repository with dummy data to store.
 *  
 *  @author anand.prakash
 */
public class ClientGetter {

    private static final String[] FIRST_NAMES = {"Anand", "Charlotte", "John", "Andy", "Concepcion", "Hee", "Anand"};

    private static final String[] LAST_NAMES = {"Prakash", "Dicesare", "Hap", "Sriv", "Norberg", "Aldrich", "P"};

    private static final String[] COFFEES = {"Latte", "Cappuccino", "Espresso", "Chai"};

    private Random randomSeed = new Random();
    
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


    public List<Client> getBestClients() {
    	
        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            String firstName = FIRST_NAMES[randomSeed.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[randomSeed.nextInt(LAST_NAMES.length)];
            String coffee = COFFEES[randomSeed.nextInt(COFFEES.length)];
            int numberOfOrders = randomSeed.nextInt(100);

            clients.add(new Client(i, firstName, lastName, coffee, numberOfOrders));
            randomDelay();
        }
        logger.info("Object list size while Get call is:"+ Utils.sizeOf(clients) +" bytes when serialized");
        return clients;
    }
    
    public Client updateBestClient(int id) {
    	 Client client = null ;
       /* List<Client> clients = getBestClients();
       
        for (Client c : clients) {

            if(c.getId()==id){
            	c.setFirstName("ANAND");
            	c.setLastName("PRAKASH");
            	
            	client = c;
            	break;
            }
            randomDelay();
        }*/
    	int numberOfOrders = randomSeed.nextInt(100);
    	String firstName = FIRST_NAMES[randomSeed.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[randomSeed.nextInt(LAST_NAMES.length)];
    	String coffee = COFFEES[randomSeed.nextInt(COFFEES.length)];
        client = new Client(id, firstName, lastName, coffee, numberOfOrders);
        ArrayList<Client> list = new ArrayList<Client>();
        list.add(client);
        logger.info("Object size while update call is:"+ Utils.sizeOf(list)+" bytes when serialized");
        return client;
    }
    
    protected void randomDelay() {
        try {
        	//System.out.println("Sleeping for-" +randomSeed.nextInt(200) +" ms");
            TimeUnit.MILLISECONDS.sleep(randomSeed.nextInt(200));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    


}
