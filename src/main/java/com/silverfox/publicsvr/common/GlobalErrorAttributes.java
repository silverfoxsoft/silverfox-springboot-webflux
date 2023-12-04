package com.silverfox.publicsvr.common;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.MissingRequestValueException;

import java.util.HashMap;
import java.util.Map;

@Component
public class GlobalErrorAttributes extends DefaultErrorAttributes {
    @Override
    public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions errorAttributeOptions) {
        Map<String, Object> map = new HashMap<>();
        if (getError(request) instanceof MissingRequestValueException) {
            MissingRequestValueException ex = (MissingRequestValueException) getError(request);
            map.put("msg", ex.getReason());
            map.put("code", 500);
            map.put("data", null);
            return map;
        }
        Throwable error = getError(request);
        map.put("msg", error.getMessage());
        map.put("code", 500);
        map.put("data", null);
        return map;
    }
}