package creapption.com.weathernow.data.remote.api;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by boma24 on 2/15/18.
 */

public class WeatherData implements Serializable {
    @Expose()
    private String summary;
    @Expose()
    private String icon;
    @Expose()
    private Double precipProbability;
    @Expose()
    private Double temperature;
    @Expose()
    private Double humidity;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Double getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(Double precipProbability) {
        this.precipProbability = precipProbability;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}
