package creapption.com.weathernow.domain;

import android.os.Build;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import creapption.com.weathernow.BuildConfig;
import creapption.com.weathernow.data.DataManager;
import creapption.com.weathernow.data.remote.WeatherNowDeserializer;
import creapption.com.weathernow.data.remote.WeatherNowService;
import creapption.com.weathernow.data.remote.api.WeatherData;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by boma24 on 2/10/18.
 */

@Module
public class DomainModule {

    @Singleton
    @Provides
    DataManager providesDataManager(WeatherNowService weatherNowService){
        return new DataManager(weatherNowService);
    }

    @Singleton
    @Provides
    WeatherNowService provideWeatherNowService(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

        //Logging interceptor
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        //Logs will only appear in Debug Mode
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                : HttpLoggingInterceptor.Level.NONE);
        httpClientBuilder.addNetworkInterceptor(httpLoggingInterceptor);
        httpClientBuilder.connectTimeout(120, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(120, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(120, TimeUnit.SECONDS);

        OkHttpClient customOkHttpClient = httpClientBuilder.build();

        //Todo: add deserializer
        final GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(WeatherData.class, new WeatherNowDeserializer());
        gsonBuilder.excludeFieldsWithoutExposeAnnotation();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.WEATHERNOW_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .client(customOkHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
                .create(WeatherNowService.class);
    }

}
