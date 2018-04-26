package singkorea.singkorea.com.singkorea;

import android.app.Application;

import es.dmoral.toasty.Toasty;

public class SingKoreaApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Toasty.Config.getInstance().tintIcon(true).apply();
    }
}
