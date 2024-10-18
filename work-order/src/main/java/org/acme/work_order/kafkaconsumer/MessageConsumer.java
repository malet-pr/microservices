package org.acme.work_order.kafkaconsumer;

import org.acme.work_order.api.JobController;
import org.acme.work_order.common.LocalDateTimeTypeAdapter;
import org.acme.work_order.workorder.WorkOrderDTO;
import org.acme.work_order.workorder.WorkOrderService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Transactional
public class MessageConsumer {

    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private WorkOrderService woService;

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
            .create();

    private static AtomicInteger savedCount ;
    public AtomicInteger getSavedCount() { return savedCount;}

    private static AtomicInteger errorCount ;
    public AtomicInteger getErrorCount() { return errorCount;}

    static String errorMessage;

    @KafkaListener(topics = "new-wo", groupId = "wo-group-2")
    public void listen(@Payload String message) {
        savedCount = new AtomicInteger(0);
        errorCount = new AtomicInteger(0);
        try {
            Type listType = new TypeToken<List<WorkOrderDTO>>() {}.getType();
            List<WorkOrderDTO> dtos = gson.fromJson(message, listType);
            dtos.forEach(dto -> {
                boolean saved = woService.save(dto);
                if (saved) {
                    savedCount.getAndIncrement();
                } else {
                    errorCount.getAndIncrement();
                }
            });
            log.info("Work Orders:  Saved {} - Not saved {}", savedCount, errorCount);
        } catch (JsonSyntaxException e) {
            log.error("Failed to deserialize Kafka message: {}", e.getMessage());
            errorMessage = "Failed to deserialize Kafka message";
        } catch (Exception e) {
            log.error(e.getMessage());
            errorMessage = e.getMessage();
        }
    }

}