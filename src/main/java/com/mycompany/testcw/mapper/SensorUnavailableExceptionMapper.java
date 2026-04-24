package com.mycompany.testcw.mapper;

import com.mycompany.testcw.exception.SensorUnavailableException;
import com.mycompany.testcw.model.ErrorMessage;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

// Cannot add readings to the sensor when type is MAINTAINANCE
@Provider
public class SensorUnavailableExceptionMapper implements ExceptionMapper<SensorUnavailableException>{
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(SensorUnavailableException exception){
        ErrorMessage errorMessage = new ErrorMessage(
             exception.getMessage(),403,"Forbidden"
        );
        return Response.status(Response.Status.FORBIDDEN).entity(errorMessage).build();
    }
    
}
