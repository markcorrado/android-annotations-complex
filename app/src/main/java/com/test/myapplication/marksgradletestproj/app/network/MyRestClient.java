package com.test.myapplication.marksgradletestproj.app.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.myapplication.marksgradletestproj.app.model.TestObject;
import org.androidannotations.annotations.rest.Accept;
import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.MediaType;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import java.util.List;

/**
 * Created by mark on 6/4/14.
 */
@Rest(rootUrl = "http://10.40.112.198:3000/", converters = {MappingJacksonHttpMessageConverter.class })
public interface MyRestClient {

    @Get("/users/{id}")
    @Accept(MediaType.APPLICATION_JSON)
    @JsonIgnoreProperties(ignoreUnknown = true)
    TestObject getTestObject(long id);
}
