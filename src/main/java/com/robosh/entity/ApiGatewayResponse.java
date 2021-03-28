package com.robosh.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiGatewayResponse {
    private int statusCode;
    private Map<String, String> headers;
    private Object body;
}
