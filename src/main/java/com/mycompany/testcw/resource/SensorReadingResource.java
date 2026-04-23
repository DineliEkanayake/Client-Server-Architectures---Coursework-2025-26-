package com.mycompany.testcw.resource;

import com.mycompany.testcw.exception.SensorUnavailableException;
import com.mycompany.testcw.model.SensorReading;
import com.mycompany.testcw.service.ReadingService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;

public class SensorReadingResource {
    private String sensorId; 
    ReadingService readService = new ReadingService();
    
    public SensorReadingResource(String id){
        this.sensorId = id;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory(){
        List<SensorReading> history = readService.getReadings(sensorId);
        return Response.ok(history).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addReading(SensorReading reading){
        SensorReading readingResult = readService.addReading(sensorId, reading);
        if (readingResult == null){
            throw new SensorUnavailableException("Sensor not found");
        }
        return Response.status(201).entity(readingResult).build();
    }
}
