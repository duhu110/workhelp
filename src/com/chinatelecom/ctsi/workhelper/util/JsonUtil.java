package com.chinatelecom.ctsi.workhelper.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Created by apple on 14/10/31.
 */
public class JsonUtil {
    private JsonUtil() {}


    public static <T> T readJsonFromByte(byte[] jsonByte, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonByte, type);
        } catch (JsonParseException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        } catch (IOException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        }
    }

    public static <T> T readJsonFromByte(byte[] jsonByte, TypeReference<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        // mapper.set
        try {
            return mapper.readValue(jsonByte, type);
        } catch (JsonParseException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        } catch (IOException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        }
    }

    public static String toJsonStr(Object object){
    	 ObjectMapper mapper = new ObjectMapper();  
         
         // Convert object to JSON string  
         String Json = null;
		try {
			Json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
         System.out.println("Change Object to JSON String: " + Json);  
        return Json;
    }


    public static <T> T readJsonFromString(String jsonString, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, type);
        } catch (JsonParseException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        } catch (IOException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        }
    }

    public static <T> T readJsonFromString(String jsonString, TypeReference<T> type) {
        ObjectMapper mapper = new ObjectMapper();
       // mapper.set
        try {
            return mapper.readValue(jsonString, type);
        } catch (JsonParseException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        } catch (JsonMappingException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        } catch (IOException e) {
            throw new RuntimeException("Deserialize from JSON failed.", e);
        }
    }
}
