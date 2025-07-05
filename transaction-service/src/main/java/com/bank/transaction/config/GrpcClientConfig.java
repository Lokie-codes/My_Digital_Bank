package com.bank.transaction.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import com.bank.account.grpc.AccountServiceGrpc;

@Configuration
public class GrpcClientConfig {
    @Value("${ACCOUNT_SERVICE_HOST:account-service}")
    private String host;

    @Value("${ACCOUNT_SERVICE_PORT:9090}")
    private int port;

    @Bean
    public ManagedChannel accountServiceChannel() {
        return ManagedChannelBuilder
            .forAddress(host, port)
            .usePlaintext()
            .build();
    }

    @Bean
    public AccountServiceGrpc.AccountServiceBlockingStub accountServiceStub(ManagedChannel channel) {
        return AccountServiceGrpc.newBlockingStub(channel);
    }
}
