package creapption.com.weathernow.util;

import android.Manifest;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import creapption.com.weathernow.main.MainActivity;

/**
 * Created by boma24 on 2/7/18.
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

        }

        @Override
        public void onPermissionDenied(PermissionDeniedResponse response) {
            if(response.isPermanentlyDenied()){
                mainActivity.deniedPermanentlyPermissionMessage();
            }
        }

        @Override
        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
            token.continuePermissionRequest();
        }
    };
}
