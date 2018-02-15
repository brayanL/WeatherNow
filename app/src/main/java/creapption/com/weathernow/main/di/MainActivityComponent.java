package creapption.com.weathernow.main.di;

import javax.inject.Singleton;

import creapption.com.weathernow.domain.DomainModule;
import creapption.com.weathernow.main.MainActivity;
import dagger.Component;

/**
 * Created by boma24 on 2/10/18.
 */

@Singleton
@Component(modules = {DomainModule.class, MainActivityModule.class })
public interface MainActivityComponent {
    void inject(MainActivity mainActivity);
}
