package com.ll.jwt.utill;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;
import java.util.Map;

public class Ut {
    public static class json{

        public static Object toStr(Map<String, Object> map){
            try{
                return new ObjectMapper().writeValueAsString(map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        public static Map<String, Object> toMap(String body) {
            try {
                return new ObjectMapper().readValue(body, LinkedHashMap.class);
            } catch (JsonProcessingException e){
                return null;
            }
        }
    }
}
