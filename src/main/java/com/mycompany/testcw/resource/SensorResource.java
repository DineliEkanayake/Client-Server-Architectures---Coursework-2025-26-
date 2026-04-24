package com.mycompany.testcw.resource;

import com.mycompany.testcw.exception.LinkedResourceNotFoundException;
import com.mycompany.testcw.service.SensorService;
import com.mycompany.testcw.model.Sensor;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/sensors")
public class SensorResource {
    SensorService sensorService = new SensorService();
    
    // Create new sensor
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newSensor(Sensor sensor){
        Sensor sensorNew = sensorService.addSensor(sensor);
        
        if (sensorNew == null){
            throw new LinkedResourceNotFoundException("Sensor with ID " + sensor.getId() + "does not have a room");
        }
        return Response.status(201).entity(sensorNew).build();
    }
    
    // Filter using the sensor type
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response fileterSensor(@QueryParam("type") String type){
        if(type != null){
            List<Sensor> filtered = sensorService.filterLogic(type);
            return Response.ok(filtered).build();
        }
        return Response.ok(sensorService.getAllSensors()).build();
    }
    
    // Nested path for readings of the sensor
    @Path("{sensorId}/readings")
    public SensorReadingResource getReading(@PathParam("sensorId") String id){
        return new SensorReadingResource(id);
    }
}
