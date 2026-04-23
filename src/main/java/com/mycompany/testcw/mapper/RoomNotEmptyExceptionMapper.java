package com.mycompany.testcw.mapper;

import com.mycompany.testcw.exception.RoomNotEmptyException;
import com.mycompany.testcw.model.ErrorMessage;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class RoomNotEmptyExceptionMapper implements ExceptionMapper<RoomNotEmptyException>{
    @Override
    @Produces(MediaType.APPLICATION_JSON)
    public Response toResponse(RoomNotEmptyException exception){
        ErrorMessage errorMessage = new ErrorMessage(
             exception.getMessage(),409,"Conflict occured with the current state of the server-side resouce"
        );
        return Response.status(Response.Status.CONFLICT).entity(errorMessage).build();
    }
}
