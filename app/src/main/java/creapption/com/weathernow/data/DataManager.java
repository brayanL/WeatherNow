package creapption.com.weathernow.data;

import android.location.Location;

import creapption.com.weathernow.data.remote.WeatherNowService;
import creapption.com.weathernow.data.remote.api.WeatherData;
import rx.Observable;

/**
 * Bridge between the presenter and the data.
 */

public class DataManager {
    private WeatherNowService weatherNowService;

    public DataManager(WeatherNowService weatherNowService) {
        this.weatherNowService = weatherNowService;
    }

    public Observable<WeatherData> getWeather(Location location) {
        return weatherNowService.getWeather(location.getLatitude(), location.getLongitude());
    }
}
