package com.mycompany.testcw;

import com.mycompany.testcw.filter.LoggingFilter;
import com.mycompany.testcw.mapper.GlobalExceptionMapper;
import com.mycompany.testcw.mapper.LinkedResourceNotFoundExceptionMapper;
import com.mycompany.testcw.mapper.RoomNotEmptyExceptionMapper;
import com.mycompany.testcw.mapper.SensorUnavailableExceptionMapper;
import com.mycompany.testcw.resource.DiscoveryResource;
import com.mycompany.testcw.resource.RoomResource;
import com.mycompany.testcw.resource.SensorResource;
import javax.ws.rs.core.Application;
import java.util.Set;
import java.util.HashSet;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api/v1")
public class ApplicationClass extends Application{
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        
        classes.add(org.glassfish.jersey.jackson.JacksonFeature.class);
        
        // Resources
        classes.add(DiscoveryResource.class);
        classes.add(RoomResource.class);
        classes.add(SensorResource.class);
        
        // Mappers
        classes.add(GlobalExceptionMapper.class);
        classes.add(LinkedResourceNotFoundExceptionMapper.class);
        classes.add(RoomNotEmptyExceptionMapper.class);
        classes.add(SensorUnavailableExceptionMapper.class);
        
        // Fileters
        classes.add(LoggingFilter.class);
        
        return classes;
    }
}