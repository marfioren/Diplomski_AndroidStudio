package hr.crofi.rasporedterapija.Kosarica;

import android.content.Context;
import android.net.ConnectivityManager;


import java.net.InetAddress;

public class ProvjeraSignala {

    public boolean ProvjeriSignal(Context context){

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
        }
}
