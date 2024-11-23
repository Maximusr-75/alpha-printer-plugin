package com.alphaplugin.printer;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;

public class POSPrinterPlugin extends CordovaPlugin {
    private static final String TAG = "POSPrinterPlugin";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("printData")) {
            String ip = args.getString(0);
            int port = args.getInt(1);
            String data = args.getString(2);
            this.printData(ip, port, data, callbackContext);
            return true;
        }
        else if (action.equals("testConnection")) {
            String ip = args.getString(0);
            int port = args.getInt(1);
            this.testConnection(ip, port, callbackContext);
            return true;
        }
        return false;
    }

    private void printData(final String ip, final int port, final String data, final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Socket socket = new Socket(ip, port);
                    OutputStreamWriter outputStream = new OutputStreamWriter(socket.getOutputStream());
                    outputStream.write(data);
                    outputStream.flush();
                    socket.close();
                    callbackContext.success("Print successful");
                } catch (Exception e) {
                    Log.e(TAG, "Print error", e);
                    callbackContext.error("Print failed: " + e.getMessage());
                }
            }
        });
    }

    private void testConnection(final String ip, final int port, final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                try {
                    Socket socket = new Socket(ip, port);
                    socket.close();
                    callbackContext.success("Connection successful");
                } catch (Exception e) {
                    Log.e(TAG, "Connection test failed", e);
                    callbackContext.error("Connection failed: " + e.getMessage());
                }
            }
        });
    }
}
