package com.example.toollib.http.converter;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.exception.HttpError;
import com.example.toollib.util.LoginInterceptor;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Converter;

import static okhttp3.internal.Util.UTF_8;

/**
 * @author Administrator
 * @date 2019/5/17 0017
 */
public class ResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    ResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        HttpResult httpStatus = gson.fromJson(response, HttpResult.class);
        if (Integer.parseInt(httpStatus.getCode()) != HttpError.HTTP_SUCCESS.getCode()) {
            value.close();
            String data;
            if (httpStatus.getData() == null) {
                data = "";
            } else {
                data = httpStatus.getData().toString();
            }
            LoginInterceptor.tokenReLogin(new ApiException(Integer.parseInt(httpStatus.getCode()), httpStatus.getMsg(), data));
            throw new ApiException(Integer.parseInt(httpStatus.getCode()), httpStatus.getMsg(), data);
        }
        MediaType contentType = value.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        assert charset != null;
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);
        try {
            return adapter.read(jsonReader);
        } finally {
            value.close();
        }
    }

}
