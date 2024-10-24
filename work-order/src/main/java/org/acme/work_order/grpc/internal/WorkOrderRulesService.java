package org.acme.work_order.grpc.internal;

import org.acme.work_order.grpc.WorkOrderRequest;
import org.acme.work_order.grpc.WorkOrderResponse;
import org.springframework.stereotype.Service;


@Service
public class WorkOrderRulesService {

    public WorkOrderResponse processWorkOrder(WorkOrderRequest request) {
        return WorkOrderResponse.newBuilder()
                .setWoNumber(request.getWoNumber())
                .addAllWoJobs(request.getWoJobsList())
                .setJobTypeCode(request.getJobTypeCode())
                .setAddress(request.getAddress())
                .setCity(request.getCity())
                .setWoCreationDate(request.getWoCreationDate())
                .setWoCompletionDate(request.getWoCompletionDate())
                .setClientId(request.getClientId())
                .setHasRules(Boolean.TRUE)
                .build();
    }

}
