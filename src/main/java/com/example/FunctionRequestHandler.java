package com.example;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.BeanProvider;
import io.micronaut.context.annotation.Any;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private final Logger logger = LoggerFactory.getLogger(FunctionRequestHandler.class);
    @Inject
    ObjectMapper objectMapper;

    @Any
    BeanProvider<com.amazonaws.services.lambda.runtime.Context> context;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        logger.info("Fname:{}-{}", context.get().getFunctionName(), context.get().getFunctionVersion());
        try {
            String json = objectMapper.writeValueAsString(Collections.singletonMap("message", "Hello World"));
            response.setStatusCode(200);
            response.setBody(json);
        } catch (JsonProcessingException e) {
            response.setStatusCode(500);
        }
        return response;
    }
}
