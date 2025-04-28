package org.example.javangersspringrecap.model.openai;


//                  "location": {
    //                  "type": "string",
    //                  "description": "City or location name"
//                  },
//                  "temperature": {
    //                  "type": "number",
    //                  "description": "Temperature in Celsius"
//                  },
//                  "conditions": {
    //                  "type": "string",
    //                  "description": "Weather conditions description"
//                  }

import java.util.Map;

public record Property(String type, String description, Map<String, String> items) {
    public Property(String type, String description) {
        this(type, description, null);
    }
}
