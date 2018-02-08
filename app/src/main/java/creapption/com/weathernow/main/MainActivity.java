package creapption.com.weathernow.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import creapption.com.weathernow.R;
import creapption.com.weathernow.util.CommonUtils;
import creapption.com.weathernow.util.PermissionsDexter;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private PermissionsDexter mPermissionsDexter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLocationPermissions();
    }

    @Override
    public void deniedPermanentlyPermissionMessage() {
        CommonUtils.showSimpleToastMessages(this,
                getString(R.string.denied_permission_permanently));
    }

    @Override
    public void checkLocationPermissions() {
        mPermissionsDexter = new PermissionsDexter(this);
        mPermissionsDexter.permissionAccessLocation();
    }
}
