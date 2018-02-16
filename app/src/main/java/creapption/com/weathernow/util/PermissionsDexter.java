package creapption.com.weathernow.util;

import android.Manifest;
import android.view.View;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import creapption.com.weathernow.R;
import creapption.com.weathernow.main.MainActivity;

/**
 * Controls the permissions granted by users to the application, through the Dexter library.
 */

public class PermissionsDexter {

    private MainActivity mainActivity;

    public PermissionsDexter(MainActivity activity) {
        this.mainActivity = activity;
    }

    public void permissionAccessLocation() {
        Dexter.withActivity(mainActivity)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(listenerLocation)
                .check();
    }

    private PermissionListener listenerLocation = new PermissionListener() {
        @Override
        public void onPermissionGranted(PermissionGrantedResponse response) {
            mainActivity.updateMessages(View.INVISIBLE, R.string.success_messages);
        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse response) {
            if(response.isPermanentlyDenied()){
                mainActivity.toastMessages(R.string.denied_permission_permanently);
            }else{
                mainActivity.updateMessages(View.VISIBLE, R.string.permission_location_error);
            }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            token.continuePermissionRequest();
        }
    };
}
