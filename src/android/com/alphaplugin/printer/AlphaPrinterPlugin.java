package com.alphaplugin.printer;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import java.net.Socket;
import java.io.OutputStreamWriter;

public class AlphaPrinterPlugin extends CordovaPlugin {
    private static final String TAG = "AlphaPrinter";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("print")) {
            String ip = args.getString(0);
            int port = args.getInt(1);
            String data = args.getString(2);
            this.print(ip, port, data, callbackContext);
            return true;
        }
        return false;
    }

    private void print(final String ip, final int port, final String data, final CallbackContext callbackContext) {
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
}