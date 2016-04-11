package gov.nasa.client.api;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class NASAApiAdapter {

    public static NASAService getRESTAdapter() {
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(NASAApi.NASA_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return restAdapter.create(NASAService.class);
    }

}
