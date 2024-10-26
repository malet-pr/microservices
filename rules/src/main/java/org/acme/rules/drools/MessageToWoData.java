package org.acme.rules.drools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.acme.rules.common.LocalDateTimeTypeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class MessageToWoData {

    private static final Logger log = LoggerFactory.getLogger(MessageToWoData.class);

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    public WorkOrderData readRabbitMessage(String msg){
        try{
            return gson.fromJson(msg, WorkOrderData.class);
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

}

