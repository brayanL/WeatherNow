package creapption.com.weathernow;

import android.app.Application;

import creapption.com.weathernow.domain.DomainModule;
import creapption.com.weathernow.main.MainActivityView;
import creapption.com.weathernow.main.di.DaggerMainActivityComponent;
import creapption.com.weathernow.main.di.MainActivityComponent;
import creapption.com.weathernow.main.di.MainActivityModule;

/**
 * Created by boma24 on 2/15/18.
 */

public class WeathernowApplication extends Application {
    private DomainModule domainModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initModules();
    }

    private void initModules() {
        domainModule = new DomainModule();
    }

    public MainActivityComponent getMainActivityComponent(MainActivityView view){
        return DaggerMainActivityComponent
                .builder()
                .domainModule(domainModule)
                .mainActivityModule(new MainActivityModule(view))
                .build();
    }

}
