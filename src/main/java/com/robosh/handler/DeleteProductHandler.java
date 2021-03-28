package com.robosh.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.robosh.entity.ApiGatewayResponse;
import com.robosh.service.ProductService;
import com.robosh.util.UtilService;
import org.apache.log4j.Logger;

import java.util.Map;

public class DeleteProductHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final ProductService productService = new ProductService();
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {

            Map<String, String> pathParameters = (Map<String, String>) input.get("pathParameters");
            String id = pathParameters.get("id");
            logger.debug("Id: " + id);
            String deleteMessage = productService.delete(id);
            if (deleteMessage.equalsIgnoreCase("Not found!")) {
                return ApiGatewayResponse.builder()
                        .statusCode(404)
                        .headers(UtilService.getCorsHeaders())
                        .body(deleteMessage)
                        .build();
            }
            return ApiGatewayResponse.builder()
                    .statusCode(200)
                    .headers(UtilService.getCorsHeaders())
                    .body(deleteMessage)
                    .build();
        } catch (Exception ex) {
            return ApiGatewayResponse.builder()
                    .statusCode(500)
                    .headers(UtilService.getCorsHeaders())
                    .body("Something went wrong " + ex)
                    .build();
        }
    }
}
