package com.example.toollib.http.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @author Administrator
 * @date 2019/5/17 0017
 */
public class MyConverterFactory extends Converter.Factory{

    private final Gson gson;

    private MyConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    public static MyConverterFactory create() {
        return create(new Gson());
    }

    public static MyConverterFactory create(Gson gson) {
        return new MyConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new ResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new RequestBodyConverter<>(gson, adapter);
    }

}
