package org.acme.rules.drools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import org.acme.rules.common.LocalDateTimeTypeAdapter;
import org.acme.rules.grpc.OrderData;
import org.acme.rules.grpc.woserviceconnect.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class MessageToWoData {

    private static final Logger log = LoggerFactory.getLogger(MessageToWoData.class);

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();
    Type listType = new TypeToken<List<OrderData>>(){}.getType();

    public Order readRabbitMessage(JsonElement msg) {
        try{
            return gson.fromJson(String.valueOf(msg), Order.class);
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

}

