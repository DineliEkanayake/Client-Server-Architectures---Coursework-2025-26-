package com.mycompany.testcw.service;

import com.mycompany.testcw.exception.SensorUnavailableException;
import com.mycompany.testcw.model.SensorReading;
import com.mycompany.testcw.model.Sensor;
import com.mycompany.testcw.storage.DataStore;
import java.util.ArrayList;
import java.util.List;

public class ReadingService {
    // GET history for specific sensorId
    public List<SensorReading> getReadings(String sensorId){
        return DataStore.sensorReadings.getOrDefault(sensorId, new ArrayList<>());
    }
    
    public SensorReading addReading(String sensorId, SensorReading reading){
        Sensor sensor = DataStore.sensors.get(sensorId);
        
        if (sensor == null){
            return null;
        }
        
        // Check if the sensor is ”MAINTENANCE”
        if(sensor.getStatus().equals("MAINTENANCE")){
            throw new SensorUnavailableException("Sensor with ID " + sensorId + "is under maintenance");
    }
        
        // Create a new List for the specific sensor if it doesn't exist
        DataStore.sensorReadings.putIfAbsent(sensorId, new ArrayList<>());
        
        // Add reading to the specific sensor 
        DataStore.sensorReadings.get(sensorId).add(reading);
        
        // Update the currentValue in Sensor
        sensor.setCurrentValue(reading.getValue());
        return reading;
    }
}
