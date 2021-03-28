package com.robosh.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.robosh.entity.ApiGatewayResponse;
import com.robosh.entity.Product;
import com.robosh.entity.SaveProductRequest;
import com.robosh.service.ProductService;
import com.robosh.util.UtilService;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class CreateProductHandler implements RequestHandler<SaveProductRequest, ApiGatewayResponse> {

    private final ProductService productService = new ProductService();
    private final Logger logger = Logger.getLogger(this.getClass());

    @Override
    public ApiGatewayResponse handleRequest(SaveProductRequest productRequest, Context context) {
        try {
            logger.debug(productRequest);

            Product product = Product.builder()
                    .name(productRequest.getName())
                    .price(productRequest.getPrice())
                    .imageUrl(productRequest.getImageUrl())
                    .build();

            logger.debug(product);
            product = productService.save(product);
            Map<String, Object> result = new HashMap<>();
            result.put("product", product);
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
