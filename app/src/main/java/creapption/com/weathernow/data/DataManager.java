package creapption.com.weathernow.data;

import creapption.com.weathernow.data.remote.WeatherNowService;

/**
 * Created by boma24 on 2/10/18.
 */

public class DataManager {
    private WeatherNowService weatherNowService;

    public DataManager(WeatherNowService weatherNowService) {
        this.weatherNowService = weatherNowService;
    }
}
