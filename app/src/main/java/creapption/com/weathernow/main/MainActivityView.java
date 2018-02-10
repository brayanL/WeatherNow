package creapption.com.weathernow.main;

import android.support.annotation.StringRes;

/**
 * Created by boma24 on 2/7/18.
 */

public interface MainActivityView {
    void deniedPermanentlyPermissionMessage();
    void updateMessages(int visible, @StringRes int message);
}
