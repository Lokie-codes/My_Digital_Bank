package com.bank.account.config;

import net.devh.boot.grpc.server.serverfactory.GrpcServerConfigurer;
import net.devh.boot.grpc.server.serverfactory.GrpcServerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {
  // Using spring-boot-starter-grpc: defaults to port from application.yml
}
