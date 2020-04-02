package com.simoncorp.spoonacular_browser.repositories.remotedatasource;

import com.simoncorp.spoonacular_browser.BuildConfig;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.annotations.EverythingIsNonNull;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Simple singleton to store the retrofit client instance
 * (Yup, sorry M. Lanoix, this part is commented in english :P)
 */
public class RetrofitClientInstance {

    private static Retrofit retrofit;

    /**
     * Retrofit base url
     **/
    private static final String BASE_URL = "https://api.spoonacular.com";

    /**
     * Retrofit client singleton
     * @return An instantiated retrofit client, ready to contact spoonacular with the API KEY
     */
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder client = new OkHttpClient.Builder();
            // Create a new OkHttpClient to append at each request the query parameter apiKey
            client.interceptors().add(new Interceptor() {
                @Override
                @EverythingIsNonNull
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder()
                            // Get the corresponding API KEY from gradle.properties
                            .addQueryParameter("apiKey", BuildConfig.SPOONACULAR_APIKEY)
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            });
            //we create the new retrofit instance
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}