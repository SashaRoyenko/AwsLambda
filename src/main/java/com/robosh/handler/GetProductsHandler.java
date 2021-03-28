package com.robosh.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.robosh.entity.ApiGatewayResponse;
import com.robosh.entity.Product;
import com.robosh.service.ProductService;
import com.robosh.util.UtilService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetProductsHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

    private final ProductService productService = new ProductService();
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
        try {

            List<Product> products = productService.findAll();
            Map<String, Object> result = new HashMap<>();
            result.put("products", products);
            return ApiGatewayResponse.builder()
                    .statusCode(200)
                    .headers(UtilService.getCorsHeaders())
                    .body(result)
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
