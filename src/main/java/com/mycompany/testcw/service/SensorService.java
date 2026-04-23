package com.mycompany.testcw.service;

import com.mycompany.testcw.model.Sensor;
import com.mycompany.testcw.model.Room;
import com.mycompany.testcw.storage.DataStore;
import java.util.ArrayList;
import java.util.List;


public class SensorService {
    public List<Sensor> getAllSensors(){
        return new ArrayList<>(DataStore.sensors.values());
    }
    
    public Sensor addSensor(Sensor sensor){
        if(!DataStore.rooms.containsKey(sensor.getRoomId())){
            return null;
        }
        
        // Add the new sensor to the room
        Room room = DataStore.rooms.get(sensor.getRoomId());
        room.getSensorIds().add(sensor.getId());
        DataStore.sensors.put(sensor.getId(), sensor);
        return sensor;
    }
    
    public List<Sensor> filterLogic(String type){
        List<Sensor> filtered = new ArrayList<>();
        
        for (Sensor sensor: DataStore.sensors.values()){
            if(sensor.getType().equals(type)){
                filtered.add(sensor);
            }
        }
        return filtered;
    }
}
