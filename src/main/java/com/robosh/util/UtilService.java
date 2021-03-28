package com.robosh.util;

import java.util.HashMap;
import java.util.Map;

public class UtilService {

    private UtilService() {
    }

    public static Map<String, String> getCorsHeaders() {
        Map<String, String> corsHeaders = new HashMap<>();
        corsHeaders.put("Access-Control-Allow-Origin", "*");
        corsHeaders.put("Access-Control-Allow-Headers", "*");
        corsHeaders.put("Access-Control-Allow-Method", "POST,GET,PUT,DELETE");
        return corsHeaders;
    }

}
