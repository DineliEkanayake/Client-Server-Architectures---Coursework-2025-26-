package com.mycompany.testcw.mapper;

import com.mycompany.testcw.model.ErrorMessage;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable>{
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(Throwable exception){
        ErrorMessage errorMessage = new ErrorMessage(
             exception.getMessage(),500,"Internal Server Error"
        );
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();
    
    }
}
