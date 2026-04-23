package com.mycompany.testcw.resource;

import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Map;

@Path("/")

public class DiscoveryResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public  Map<String, Object> getMetaData(){
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("version", "1.0.0");
        metadata.put("admin", "Dineli Ekanayake");
        
        Map<String, String> endponits = new HashMap<>();
        endponits.put("rooms","/api/v1/rooms");
        endponits.put("sensors", "/api/v1/sensors");
        metadata.put("resources", endponits);
        
        return metadata;
    }
}
    
