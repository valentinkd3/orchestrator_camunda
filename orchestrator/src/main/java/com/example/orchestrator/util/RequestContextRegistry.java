package com.example.orchestrator.util;

import com.example.orchestrator.dto.RequestContext;

public class RequestContextRegistry {

    private static final ThreadLocal<? super RequestContext> requestContext = new ThreadLocal<>();

    private RequestContextRegistry(){}

    public static RequestContext getRequestContext(){
        return (RequestContext) requestContext.get();
    }

    public static <T extends RequestContext> void setRequestContext(T context){
        requestContext.set(context);
    }

}
