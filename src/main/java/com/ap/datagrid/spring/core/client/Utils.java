package com.ap.datagrid.spring.core.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ConfigurableObjectInputStream;

/**
 * 
 * @author anand.prakash
 *
 */


public class Utils {
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	public static Client deserializeDTO(byte[] data) {
		Client dto = null;
	    try {
	        ByteArrayInputStream fileIn = new ByteArrayInputStream(data);
	        ObjectInputStream in = new ConfigurableObjectInputStream(fileIn,
	                Thread.currentThread().getContextClassLoader());
	        dto =  (Client) in.readObject();
	        in.close();
	        fileIn.close();
	    } catch (Exception e) {
	        String msg = "Deserialization of marshalled List failed!";
	       // LOG.error(msg, e);
	        throw new RuntimeException(msg, e);
	    }
	    return dto;
	}

	public static byte[] serializeDTO(Client dtoObject) {
	    byte[] result = null;
	    try {
	        ByteArrayOutputStream data = new ByteArrayOutputStream();
	        ObjectOutputStream out = new ObjectOutputStream(data);
	        out.writeObject(dtoObject);
	        out.close();
	        result = data.toByteArray();
	        data.close();
	    } catch (IOException e) {
	        String msg = "Serialization of marshalled List failed!";
	        //LOG.error(msg, e);
	        throw new RuntimeException(msg, e);
	    }

	    return result;
	}
	
    public static long sizeOf(List<Client> object){
   	 if (object == null) {
   	  return 0;
   	 }

   	 try{
	    	  final ByteArrayOutputStream out = new ByteArrayOutputStream();
	    	  ObjectOutputStream oOut = new ObjectOutputStream(out);
	    	  oOut.writeObject(object);
	    	  oOut.close();
	    	 // System.out.println("Inside CLientGetter, Object size-"+out.size());
	    	  
	    	  return out.toByteArray().length;
   	 }catch (IOException e){
   	  if (logger.isWarnEnabled()){
   		  logger.info("Unable to determine object size:"+ object.toString(), e);
   	   
   	  }
   	  return -1;
   	 }
   }

}
