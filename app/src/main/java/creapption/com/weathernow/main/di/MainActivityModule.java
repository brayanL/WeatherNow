package creapption.com.weathernow.main.di;

import javax.inject.Singleton;

import creapption.com.weathernow.data.DataManager;
import creapption.com.weathernow.main.MainActivityPresenter;
import creapption.com.weathernow.main.MainActivityView;
import creapption.com.weathernow.main.impl.MainActivityPresenterImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Created by boma24 on 2/10/18.
 */

@Module
public class MainActivityModule {
    private MainActivityView mMainActivityView;

    public MainActivityModule(MainActivityView mMainActivityView) {
        this.mMainActivityView = mMainActivityView;
    }

    //This method will provide MainActivityView object instance, but only one
    //for @Singleton annotation
    @Provides
    @Singleton
    MainActivityView provideMainActivityView(){
        return this.mMainActivityView;
    }


    @Provides
    @Singleton
    MainActivityPresenter provideMainActivityPresenter(MainActivityView view, DataManager dataManager){
        return new MainActivityPresenterImpl(view, dataManager);
    }

    //more providers...

}
