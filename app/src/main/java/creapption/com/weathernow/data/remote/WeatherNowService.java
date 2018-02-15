package creapption.com.weathernow.data.remote;

import java.util.Observable;

import creapption.com.weathernow.data.remote.api.WeatherData;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by boma24 on 2/10/18.
 */

public interface WeatherNowService {
    @GET("{latitude},{longitude}")
    rx.Observable<WeatherData> getWeather(@Path("latitude") Double latitude,
                                          @Path("longitude") Double longitude);
}
