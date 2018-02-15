package creapption.com.weathernow.main;

import android.location.Location;

/**
 * Created by boma24 on 2/10/18.
 */

public interface MainActivityPresenter {
    void getWeather(Location location);
    void onDestroy();
}
