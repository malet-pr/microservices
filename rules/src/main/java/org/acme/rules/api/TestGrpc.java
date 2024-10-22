package org.acme.rules.api;

import org.acme.rules.grpc.TestBack;
import org.acme.rules.grpc.TestConnectionConsumer;
import org.acme.rules.grpc.TestGo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-grpc")
public class TestGrpc {

    @Autowired
    TestConnectionConsumer testConsumer;

    @GetMapping()
    public ResponseEntity<String> test(@RequestParam String message) {
        TestGo go = TestGo.newBuilder().setMsgOut(message).build();
        TestBack back = testConsumer.testConnection(go);
        return ResponseEntity.ok(back.getMsgIn());
    }

}