package creapption.com.weathernow.main.impl;

import creapption.com.weathernow.data.DataManager;
import creapption.com.weathernow.main.MainActivityPresenter;
import creapption.com.weathernow.main.MainActivityView;

/**
 * Created by boma24 on 2/10/18.
 */

public class MainActivityPresenterImpl implements MainActivityPresenter {
    private MainActivityView mainActivityView;
    private DataManager dataManager;

    public MainActivityPresenterImpl(MainActivityView mainActivityView, DataManager dataManager) {
        this.mainActivityView = mainActivityView;
        this.dataManager = dataManager;
    }
}
