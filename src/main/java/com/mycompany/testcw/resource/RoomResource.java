package com.mycompany.testcw.resource;

import com.mycompany.testcw.exception.RoomNotEmptyException;
import com.mycompany.testcw.service.RoomService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import com.mycompany.testcw.model.Room;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/rooms")
public class RoomResource {
    
    RoomService roomService = new RoomService();
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> getRooms(){
        return roomService.getAllRooms();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Room getARoom(@PathParam("id") String id){
        return roomService.getRoomById(id);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRoom(Room room){
        roomService.addRoom(room);
        return Response.status(201).entity(room).build();
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response deleteRoom(@PathParam("id") String id){
        Room deleted = roomService.deleteRoomId(id);
        
        if(deleted == null){
                return Response.status(404).entity("Room not found").build();        
        }
        return Response.ok(deleted).build();
    }
            
}
