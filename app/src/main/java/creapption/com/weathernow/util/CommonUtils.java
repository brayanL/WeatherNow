package creapption.com.weathernow.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by boma24 on 2/7/18.
 */

public class CommonUtils {

    public static void showSimpleToastMessages(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
