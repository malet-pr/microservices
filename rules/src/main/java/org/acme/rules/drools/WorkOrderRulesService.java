package org.acme.rules.drools;

import org.acme.rules.grpc.WorkOrderRequest;
import org.acme.rules.grpc.WorkOrderResponse;
import org.springframework.stereotype.Service;


@Service
public class WorkOrderRulesService {

    public WorkOrderResponse processWorkOrder(WorkOrderRequest request) {
        WorkOrderResponse resp = WorkOrderResponse.newBuilder()
                .setWoNumber(request.getWoNumber())
                .addAllWoJobDTOs(request.getWoJobDTOsList())
                .setJobTypeCode(request.getJobTypeCode())
                .setAddress(request.getAddress())
                .setCity(request.getCity())
                .setWoCreationDate(request.getWoCreationDate())
                .setWoCompletionDate(request.getWoCompletionDate())
                .setClientId(request.getClientId())
                .setAppliedRule("response from rules service")
                .build();
        return resp;
    }

}
