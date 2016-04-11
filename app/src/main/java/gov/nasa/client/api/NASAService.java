package gov.nasa.client.api;

import gov.nasa.client.model.picture.PictureItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NASAService {

    @GET("/planetary/apod?api_key=" + NASAApi.API_KEY)
    Call<PictureItem> pictureDay(@Query("date") String date, @Query("hd") boolean heightQuality);

}
