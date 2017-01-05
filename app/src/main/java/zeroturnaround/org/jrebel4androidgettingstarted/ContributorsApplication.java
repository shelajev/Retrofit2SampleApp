package zeroturnaround.org.jrebel4androidgettingstarted;

import android.app.Application;
import android.os.StrictMode;

import com.facebook.drawee.backends.pipeline.Fresco;

import timber.log.Timber;
import zeroturnaround.org.jrebel4androidgettingstarted.service.ContributorsService;

/**
 * Created by Sten on 17/02/16.
 */
public class ContributorsApplication extends Application {

    private ContributorsService contributorsService;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectAll()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        Timber.plant(new Timber.DebugTree());

        contributorsService = new ContributorsService();

    }

    public ContributorsService getContributorService() {
        return contributorsService;
    }
}
