package edu.ncsu.se.utils;

import com.google.gson.Gson;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;

public class RestUtils {

    private static Client restClient = ClientBuilder.newClient();

    public static String getRequest(String url, Map<String, Object> headers) throws Exception {
        Invocation.Builder builder = restClient.target(url)
                .request();

        for(Map.Entry<String, Object> header : headers.entrySet()){
            builder = builder.header(header.getKey(), header.getValue());
        }

        Response response = builder.get();
        if (response.getStatus() != 200) {
            throw new Exception("Rest call failed! for url :" + url + "\n response : " + response.readEntity(String.class));
        }

        return response.readEntity(String.class);
    }

    public static String postRequest(String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        String json = new Gson().toJson(params);
        Invocation.Builder builder = restClient.target(url)
                .request("application/json");

        for(Map.Entry<String, Object> header : headers.entrySet()){
            builder = builder.header(header.getKey(), header.getValue());
        }
        Response response = builder
                .post(Entity.entity(json, MediaType.APPLICATION_JSON));
        if (response.getStatus() != 201 && response.getStatus() != 200) {
            throw new Exception("Rest call failed! for url :" + url + "\n response : " + response.readEntity(String.class));
        }
        return response.readEntity(String.class);
    }

    public static void patchRequest(String url, Map<String, Object> params, Map<String, Object> headers) throws Exception {
        String json = new Gson().toJson(params);
        Invocation.Builder builder =  ClientBuilder.newClient()
                .target(url)
                .request("application/json");

        for(Map.Entry<String, Object> header : headers.entrySet()){
            builder = builder.header(header.getKey(), header.getValue());
        }
        Response response = builder.build("PATCH", Entity.entity(json, MediaType.APPLICATION_JSON))
                .property(HttpUrlConnectorProvider.SET_METHOD_WORKAROUND, true)
                .invoke();
        if (response.getStatus() != 200) {
            throw new Exception("Rest call failed! for url :" + url + "\n response : " + response.readEntity(String.class));
        }
    }
}