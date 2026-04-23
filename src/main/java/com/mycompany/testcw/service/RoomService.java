package com.mycompany.testcw.service;

import com.mycompany.testcw.exception.RoomNotEmptyException;
import java.util.List;
import com.mycompany.testcw.model.Room;
import com.mycompany.testcw.storage.DataStore;
import java.util.ArrayList;

public class RoomService {
    public List<Room> getAllRooms(){
        return new ArrayList<>(DataStore.rooms.values());
    }
    
    public Room getRoomById(String id){
        return DataStore.rooms.get(id);
    }
    
    public void addRoom(Room room){
        DataStore.rooms.put(room.getId(), room);
    }
    
    public Room deleteRoomId(String id){
        Room room = DataStore.rooms.get(id);
        
        // if room doesn't exist
        if (room == null){
            return null;
        }
        
        // If room has sensors room cannot be deleted
        if (!room.getSensorIds().isEmpty()){
            throw new RoomNotEmptyException("Room " + id + " still has sensors assigned");
        }
        return DataStore.rooms.remove(id);
    }
}
