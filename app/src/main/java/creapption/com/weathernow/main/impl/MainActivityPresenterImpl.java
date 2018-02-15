package creapption.com.weathernow.main.impl;

import android.location.Location;
import android.util.Log;

import creapption.com.weathernow.data.DataManager;
import creapption.com.weathernow.data.remote.api.WeatherData;
import creapption.com.weathernow.main.MainActivityPresenter;
import creapption.com.weathernow.main.MainActivityView;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static creapption.com.weathernow.main.MainActivity.TAG;

/**
 * Created by boma24 on 2/10/18.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {
    private MainActivityView mainActivityView;
    private DataManager dataManager;
    private Subscription mSubscription;

    public MainActivityPresenterImpl(MainActivityView mainActivityView, DataManager dataManager) {
        this.mainActivityView = mainActivityView;
        this.dataManager = dataManager;
    }

    @Override
    public void getWeather(Location location) {
        Log.d(TAG, "getWeather: Presenter Here");
        mSubscription = dataManager.getWeather(location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<WeatherData>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        Log.d(TAG, "onNext: "+weatherData.getSummary());
                        mainActivityView.updateUI(weatherData);
                    }
                });
    }

    @Override
    public void onDestroy() {
        if(mSubscription != null) mSubscription.unsubscribe();
    }
}
