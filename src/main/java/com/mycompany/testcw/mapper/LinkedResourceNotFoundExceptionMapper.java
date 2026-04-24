package com.mycompany.testcw.mapper;

import com.mycompany.testcw.exception.LinkedResourceNotFoundException;
import com.mycompany.testcw.model.ErrorMessage;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

// Room does not exist for the sensor
@Provider
public class LinkedResourceNotFoundExceptionMapper implements ExceptionMapper<LinkedResourceNotFoundException>{
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(LinkedResourceNotFoundException exception){
        ErrorMessage errorMessage = new ErrorMessage(
             exception.getMessage(),422," Unprocessable Entity"
        );
        return Response.status(422).entity(errorMessage).build();
    }
}
