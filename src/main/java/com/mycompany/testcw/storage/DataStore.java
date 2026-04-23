package com.mycompany.testcw.storage;

import com.mycompany.testcw.model.SensorReading;
import com.mycompany.testcw.model.Room;
import com.mycompany.testcw.model.Sensor;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class DataStore {
    public static Map<String, Room> rooms = new HashMap<>();
    public static Map<String, Sensor> sensors = new HashMap<>();
    public static Map<String, List<SensorReading>> sensorReadings = new HashMap<>();
}
