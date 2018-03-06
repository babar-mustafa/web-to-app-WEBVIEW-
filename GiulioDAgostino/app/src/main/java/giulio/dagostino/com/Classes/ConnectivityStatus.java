package giulio.dagostino.com.Classes;

import android.content.Context;
import android.content.ContextWrapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by BabarMustafa on 7/13/2017.
 */


public class ConnectivityStatus extends ContextWrapper {

    public ConnectivityStatus(Context base) {
        super(base);
    }

    public static boolean isConnected(Context context){

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo connection = manager.getActiveNetworkInfo();
        if (connection != null && connection.isConnectedOrConnecting()){
            return true;
        }
        return false;
    }
}