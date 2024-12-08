package org.acme.rules.drools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import org.acme.rules.common.LocalDateTimeTypeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MessageToWoData {

    private static final Logger log = LoggerFactory.getLogger(MessageToWoData.class);

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();
    Type listType = new TypeToken<List<WorkOrderData>>(){}.getType();

    public List<WorkOrderData> readRabbitMessage(JsonArray msg) {
        try{
            List<WorkOrderData> woDataStrings = gson.fromJson(String.valueOf(msg), listType);
            return woDataStrings;
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

}

