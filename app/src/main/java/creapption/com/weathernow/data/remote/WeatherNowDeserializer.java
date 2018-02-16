package creapption.com.weathernow.data.remote;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import creapption.com.weathernow.data.remote.api.WeatherData;

import static creapption.com.weathernow.main.MainActivity.TAG;

/**
 * Captures the data consumed by the rest API and deserialize it and creates the respective object.
 */

public class WeatherNowDeserializer implements JsonDeserializer<WeatherData> {

    @Override
    public WeatherData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject jsonObject = json.getAsJsonObject().get("currently").getAsJsonObject();
        Log.d(TAG, "deserialize: "+jsonObject);

        WeatherData weatherData = new WeatherData();
        weatherData.setSummary(jsonObject.get("summary").toString());
        weatherData.setIcon(jsonObject.get("icon").toString());
        weatherData.setPrecipProbability(jsonObject.get("precipProbability").getAsDouble());
        weatherData.setTemperature(jsonObject.get("temperature").getAsDouble());
        weatherData.setHumidity(jsonObject.get("humidity").getAsDouble());
        return weatherData;
    }
}
