package com.example.formation4.superquizz;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkChangeReceiver extends BroadcastReceiver {


    public static final String NETWORK_CHANGE_ACTION = "com.androiderstack.broadcastreceiverdemo.NetworkChangeReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (isOnline(context)){
            sendInternalBroadcast(context, "Internet Connected");
        }
        else{
            sendInternalBroadcast(context, "Internet not Connected");
        }

    }

    private void sendInternalBroadcast(Context context, String status){

            Log.e("BROADCAST_RECEIVED", ""+isOnline(context));
            try{
                Intent intent = new Intent();
                intent.putExtra("status", status);
                intent.setAction(NETWORK_CHANGE_ACTION);
                context.sendBroadcast(intent);
            } catch(Exception ex){
                ex.printStackTrace();
            }


    }

    public boolean isOnline(Context context){
        boolean isOnline = false;
        try{
            ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            isOnline = (netInfo != null && netInfo.isConnected());
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return isOnline;
    }
}
