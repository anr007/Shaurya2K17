package red.shaurya2k17.NetworkTools;

import android.app.Application;


public class ConnectivityCheck extends Application {

    private static ConnectivityCheck mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized ConnectivityCheck getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}

