package com.example.demo;

import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grpc")
public class EndPointController {

    @GrpcClient(value="grpc-model")
    private SimpleGrpc.SimpleBlockingStub simpleBlockingStub;

    @GetMapping("/{name}")
    public String helloWorld(@PathVariable String name) {
        try {
            final HelloReply response = this.simpleBlockingStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            e.printStackTrace();
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }

    @GetMapping
    public String ping() {
        return "pong";
    }

}
